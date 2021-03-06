package zombies.server.game;

import org.codehaus.jackson.JsonParseException;
import zombies.dto.actions.UserAction;
import zombies.dto.actions.ActionTypeEnum;
import com.google.inject.Inject;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.channel.Channel;
import org.slf4j.LoggerFactory;
import zombies.dto.reply.UserReply;
import zombies.server.actionworker.ActionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 24.12.12
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
public class RequestManager implements Runnable {
    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());
    ObjectMapper requestMapper = new ObjectMapper();
    ObjectMapper replyMapper = new ObjectMapper();
    LinkedBlockingQueue<Request> requestQueue;
    @Inject
    ActionManager actionManager;

    Executor longActions = Executors.newFixedThreadPool(10);
    Executor fastActions = Executors.newSingleThreadExecutor();

    public RequestManager(){
        try {
            requestMapper.generateJsonSchema(UserAction.class);
            replyMapper.generateJsonSchema(UserReply.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public LinkedBlockingQueue<Request> getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(LinkedBlockingQueue<Request> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void sendReply(UserInfo ui,UserReply userReply) throws IOException {
        if(ui.getChannel().isOpen()){
            String replyString=replyMapper.writeValueAsString(userReply);
            ui.getChannel().write(replyString+'\n');
            logger.info("sended:"+replyString);
        }
    }
    private void send(Channel c,UserReply userReply) throws IOException{
        if(c.isOpen()){
            String replyString=replyMapper.writeValueAsString(userReply);
            c.write(replyString+'\n');
            logger.info("sended responce:"+replyString);
        }
    }

    private void proccessAction(UserAction act, Channel channel){
        try {
        //   Date d=new Date();
            UserReply userReply = actionManager.processAction(act,channel);
            if(userReply !=null){
                send(channel, userReply);
            }
         //   Date d1=new Date();
         //   logger.info("request {} time {}",act,Long.toString(d1.getTime()-d.getTime()));
        } catch (Exception e) {
            logger.error("Error while process action",e);
            try {
                send(channel,actionManager.processError("Unknown error"));
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private void process(){
        if(requestQueue==null || requestQueue.size()==0)
            return;
        List<Request> tempList=new ArrayList<>();
        requestQueue.drainTo(tempList) ;
        for(Request r:tempList){
            try {
//                if(r.getRequest().contains("turnAction")) {
//                     logger.info(String.format("parse %s, %s", r.getRequest(), new Date()).toString());
//                }
                UserAction act = requestMapper.readValue(r.getRequest(),UserAction.class);
                ActionTypeEnum type= ActionTypeEnum.getValue(act.getAction());
                if(type.isLong()){
                    longActions.execute(new OperationProcess(act, r.getChannel()));
                }else{
                    fastActions.execute(new OperationProcess(act, r.getChannel()));
                }
            } catch (JsonParseException json){
                try {
                    send(r.getChannel(), actionManager.processError("Incorrect Request Error"));
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } catch (Exception e) {
                logger.error("error while parsing",e);
            }
        }
    }


    @Override
    public void run() {
        process();
    }

    private class OperationProcess implements Runnable{
        UserAction act;
        Channel channel;

        private OperationProcess(UserAction act, Channel channel) {
            this.act = act;
            this.channel = channel;
        }

        @Override
        public void run() {
            proccessAction(act,channel);
        }
    }
}

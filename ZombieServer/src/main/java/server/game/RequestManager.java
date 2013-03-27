package server.game;

import actions.Action;
import actions.ActionTypeEnum;
import com.google.inject.Inject;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.channel.Channel;
import org.slf4j.LoggerFactory;
import reply.Reply;
import server.actionworker.ActionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
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
            requestMapper.generateJsonSchema(Action.class);
            replyMapper.generateJsonSchema(Reply.class);
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

    public void sendReply(UserInfo ui,Reply reply) throws IOException {
        String replyString=replyMapper.writeValueAsString(reply);
        ui.getChannel().write(replyString+'\n');
        System.out.println("sended"+replyString);
    }
    private void send(Channel c,Reply reply) throws IOException{
        String replyString=replyMapper.writeValueAsString(reply);
        c.write(replyString+'\n');
    }

    private void proccessAction(Action act, Channel channel){
        try {
        //   Date d=new Date();
            Reply reply= actionManager.processAction(act,channel);
            if(reply!=null){
                send(channel,reply);
            }
         //   Date d1=new Date();
         //   logger.info("request {} time {}",act,Long.toString(d1.getTime()-d.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
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
                Action act = requestMapper.readValue(r.getRequest(),Action.class);
                ActionTypeEnum type= ActionTypeEnum.getValue(act.getAction());
                if(type.isLong()){
                    longActions.execute(new OperationProcess(act, r.getChannel()));
                }else{
                    fastActions.execute(new OperationProcess(act, r.getChannel()));
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    @Override
    public void run() {
        process();
    }

    private class OperationProcess implements Runnable{
        Action act;
        Channel channel;

        private OperationProcess(Action act, Channel channel) {
            this.act = act;
            this.channel = channel;
        }

        @Override
        public void run() {
            proccessAction(act,channel);
        }
    }
}

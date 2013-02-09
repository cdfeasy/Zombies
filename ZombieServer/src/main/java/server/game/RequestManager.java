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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    ConcurrentLinkedQueue<Request> requestQueue;
    @Inject
    ActionManager actionManager;

    Executor longActions = Executors.newSingleThreadExecutor();
    Executor fastActions = Executors.newSingleThreadExecutor();

    public RequestManager(){
        try {
            requestMapper.generateJsonSchema(Action.class);
            replyMapper.generateJsonSchema(Reply.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ConcurrentLinkedQueue<Request> getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(ConcurrentLinkedQueue<Request> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void sendReply(Channel c,Reply reply) throws IOException {
        String replyString=replyMapper.writeValueAsString(reply);
        c.write(replyString);
    }

    private void proccessAction(Action act, Channel channel){
        try {
            Reply reply= actionManager.processAction(act,channel);
            sendReply(channel,reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void process(){
        if(requestQueue==null)
            return;
        Request r=null;
        while((r=requestQueue.poll())!=null){
            try {
                logger.info("parse "+r.getRequest());
                Action act = requestMapper.readValue(r.getRequest(),Action.class);
                ActionTypeEnum type= ActionTypeEnum.getValue(act.getAction());
                if(type.isLong()){
                    longActions.execute(new longOperationProcess(act, r.getChannel()));
                }else{
                    fastActions.execute(new longOperationProcess(act, r.getChannel()));
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

    private class longOperationProcess implements Runnable{
        Action act;
        Channel channel;

        private longOperationProcess(Action act, Channel channel) {
            this.act = act;
            this.channel = channel;
        }

        @Override
        public void run() {
            proccessAction(act,channel);
        }
    }
}

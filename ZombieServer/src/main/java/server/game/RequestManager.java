package server.game;

import actions.Action;
import actions.ActionTypeEnum;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.netty.channel.Channel;
import reply.Reply;
import reply.ReplyBuilder;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 24.12.12
 * Time: 22:19
 * To change this template use File | Settings | File Templates.
 */
public class RequestManager implements Runnable {
    ObjectMapper requestMapper = new ObjectMapper();
    ObjectMapper replyMapper = new ObjectMapper();
    public RequestManager(){
        try {
            requestMapper.generateJsonSchema(Action.class);
            replyMapper.generateJsonSchema(Reply.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    ConcurrentLinkedQueue<Request> requestQueue;
    public RequestManager(ConcurrentLinkedQueue<Request> requestQueue)  {
        this.requestQueue=requestQueue;
    }
    public Reply processRequest(Action action) throws Exception {
        ActionTypeEnum type= ActionTypeEnum.getValue(action.getAction());
        Reply rep=null;
        if(type==null)
            throw new Exception("неизвестный тип");
        switch (type){
            case CONNECT:
                rep= ReplyBuilder.getConnectionReplyBuilder().setToken("12345").build();

                break;
            case GETUSERINFO:break;
            case SEARCH:break;
            case TURN:break;
        }
        return rep;
    }

    public void sendReply(Channel c,Reply reply) throws IOException {
        String replyString=replyMapper.writeValueAsString(reply);
        c.write(replyString);
    }


    private void process(){
        Request r=null;
        while((r=requestQueue.poll())!=null){
            try {

                Action act= requestMapper.readValue(r.getRequest(),Action.class);
                Reply repl=processRequest(act);
                sendReply(r.getChannel(),repl);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    @Override
    public void run() {

    }
}

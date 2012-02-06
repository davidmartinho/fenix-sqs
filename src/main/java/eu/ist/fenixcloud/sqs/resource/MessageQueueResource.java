package eu.ist.fenixcloud.sqs.resource;

import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jvstm.Atomic;
import pt.ist.fenixframework.FenixFramework;
import eu.ist.fenixcloud.sqs.Bootstrap;
import eu.ist.fenixcloud.sqs.domain.Message;
import eu.ist.fenixcloud.sqs.domain.MessageQueue;
import eu.ist.fenixcloud.sqs.domain.MessageQueueService;
import eu.ist.fenixcloud.sqs.mapper.JsonMapper;
import eu.ist.fenixcloud.sqs.mapper.Mapper;

@Path("queue")
public class MessageQueueResource {

  private static final int TIMEOUT = 10000;
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public static String getAllTheQueues() {
    Bootstrap.init();
    return fetchAllExistingQueues();
  }
  
  @GET
  @Produces
  @Path("/{queueOid}/message")
  public static String getNextAvailableMessage(@PathParam("queueOid") String queueOid) {
    Bootstrap.init();
    return fetchNextAvailableMessage(queueOid);
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public static String postMessageQueue(@FormParam("name") String queueName) {
    Bootstrap.init();
    return createMessageQueue(queueName);
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{queueOid}/message")
  public static String postMessageOnMessageQueue(@PathParam("queueOid") String queueOid, @FormParam("payload") String payload) {
    Bootstrap.init();
    return createMessageOnMessageQueue(queueOid, payload);
  }

  @Atomic
  public static String createMessageOnMessageQueue(String queueOid, String payload) {
    try {
      MessageQueue mq = (MessageQueue)MessageQueueService.fromOID(Long.parseLong(queueOid));
      Message message = mq.createNewMessage(payload);
      Mapper jsonMapper = new JsonMapper();
      return jsonMapper.externalizeMessage(message);  
    } catch(Exception e) {
      return "{ \"error\": \"MessageQueue not found\" }";
    }
  }
  
  
  @Atomic
  public static String fetchAllExistingQueues() {
    try {
      MessageQueueService mqs = (MessageQueueService)FenixFramework.getRoot();
      Mapper jsonMapper = new JsonMapper();
      return jsonMapper.externalizeMessageQueueSet(mqs.getMessageQueueSet());
    } catch(Exception e) {
      return "{ \"error\": \"There are no Message Queues\" }";
    }
  }
  
  @Atomic
  public static String fetchNextAvailableMessage(String queueOid) {
    try {
      MessageQueue mq = (MessageQueue)MessageQueueService.fromOID(Long.parseLong(queueOid));
      final Message availableMessage = mq.getNextAvailableMessage();
      Mapper jsonMapper = new JsonMapper();
      Timer t = new Timer();
      t.schedule(new TimerTask() {

        @Override
        public void run() {
          releaseMessage(availableMessage.getOid());
        }
        
      }, TIMEOUT);
      return jsonMapper.externalizeMessage(availableMessage);
    } catch(Exception e) {
      return "{ \"error\": \"No messages were found\" }";
    }
  }
  
  @Atomic
  public static String createMessageQueue(String queueName) {
    MessageQueueService mqs = (MessageQueueService)FenixFramework.getRoot();
    MessageQueue mq = mqs.createMessageQueue(queueName);
    Mapper jsonMapper = new JsonMapper();
    return jsonMapper.externalizeMessageQueue(mq);
  }
  
  @Atomic
  public static void releaseMessage(Long messageOid) {
    Message m = (Message)MessageQueueService.fromOID(messageOid);
    if(m.isClaimed()) {
      m.setClaimed(false);
    }
  }
  
}

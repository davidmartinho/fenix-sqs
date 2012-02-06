package eu.ist.fenixcloud.sqs.mapper;

import java.util.Set;
import eu.ist.fenixcloud.sqs.domain.Message;
import eu.ist.fenixcloud.sqs.domain.MessageQueue;

public class JsonMapper implements Mapper {

  public String externalizeMessage(Message message) {
    return "{ \"oid\": "+message.getOid()+", \"payload\": \""+message.getPayload()+"\" }";
  }
  
  public String externalizeMessageQueue(MessageQueue messageQueue) {
    return "{ \"oid\": "+messageQueue.getOid()+", \"name\": \""+messageQueue.getName()+"\" }";
  }
  
  public String externalizeMessageQueueSet(Set<MessageQueue> messageQueueSet) {
    String result = "[";
    for(MessageQueue messageQueue : messageQueueSet) {
      result += externalizeMessageQueue(messageQueue)+", ";
    }
    return result.substring(0, result.length()-2)+"]";
  }

}

package eu.ist.fenix.mqs.mapper;

import java.util.Set;
import eu.ist.fenix.mqs.domain.Message;
import eu.ist.fenix.mqs.domain.MessageQueue;

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

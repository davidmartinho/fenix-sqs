package eu.ist.fenixcloud.sqs.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageQueue extends MessageQueue_Base {

  public MessageQueue(String name) {
    setName(name);
  }
  
  public Message getNextAvailableMessage() {
    List<Message> messageList = new ArrayList<Message>();
    for(Message message : getMessageSet()) {
      if(!message.isClaimed()) {
        messageList.add(message);  
      }
    }
    if(messageList.size() > 0) {
      Collections.sort(messageList, new Message.CreationTimestampComparator());
      Message nextMessage = messageList.get(0);
      nextMessage.setClaimed(true);
      return nextMessage;
    } else {
      return null;
    }
  }
  
  public Message createNewMessage(String payload) {
    Message message = new Message(payload);
    this.addMessage(message);
    return message;
  }
  
}

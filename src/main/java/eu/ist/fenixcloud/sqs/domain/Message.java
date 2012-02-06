package eu.ist.fenixcloud.sqs.domain;

import java.util.Comparator;

import org.joda.time.DateTime;

public class Message extends Message_Base {

  public Message(String payload) {
    setCreationTimestamp(new DateTime());
    setPayload(payload);
    setClaimed(false);
  }
  
  public boolean isClaimed() {
    return getClaimed();
  }
    
  static class CreationTimestampComparator implements Comparator<Message> {

    @Override
    public int compare(Message messageA, Message messageB) {
      return messageA.getCreationTimestamp().compareTo(messageB.getCreationTimestamp());
    }
  }
  
  public void delete() {
    removeMessageQueue();
    deleteDomainObject();
  }
  
}

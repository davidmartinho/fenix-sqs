package eu.ist.fenix.mqs.domain;

public class MessageQueueService extends MessageQueueService_Base {

  public MessageQueueService() {
    
  }
  
  public MessageQueue createMessageQueue(String name) {
    MessageQueue mq = new MessageQueue(name);
    addMessageQueue(mq);
    return mq;
  }
}

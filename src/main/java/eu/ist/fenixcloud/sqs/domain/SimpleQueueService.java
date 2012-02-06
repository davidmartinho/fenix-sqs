package eu.ist.fenixcloud.sqs.domain;

public class SimpleQueueService extends SimpleQueueService_Base {

  public SimpleQueueService() {
    
  }
  
  public MessageQueue createMessageQueue(String name) {
    MessageQueue mq = new MessageQueue(name);
    addMessageQueue(mq);
    return mq;
  }
}

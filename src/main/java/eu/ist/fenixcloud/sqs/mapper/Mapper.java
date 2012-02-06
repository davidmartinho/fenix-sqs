package eu.ist.fenixcloud.sqs.mapper;

import java.util.Set;

import eu.ist.fenixcloud.sqs.domain.Message;
import eu.ist.fenixcloud.sqs.domain.MessageQueue;

public interface Mapper {

  String externalizeMessage(Message message);
  String externalizeMessageQueue(MessageQueue messageQueue);
  String externalizeMessageQueueSet(Set<MessageQueue> messageQueueSet);

}

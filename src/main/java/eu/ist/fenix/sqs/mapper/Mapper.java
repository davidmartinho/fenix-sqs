package eu.ist.fenix.sqs.mapper;

import java.util.Set;

import eu.ist.fenix.mqs.domain.Message;
import eu.ist.fenix.mqs.domain.MessageQueue;

public interface Mapper {

  String externalizeMessage(Message message);
  String externalizeMessageQueue(MessageQueue messageQueue);
  String externalizeMessageQueueSet(Set<MessageQueue> messageQueueSet);

}

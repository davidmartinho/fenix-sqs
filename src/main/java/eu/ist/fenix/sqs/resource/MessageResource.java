package eu.ist.fenix.sqs.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jvstm.Atomic;
import eu.ist.fenix.mqs.Bootstrap;
import eu.ist.fenix.mqs.domain.Message;
import eu.ist.fenix.mqs.domain.MessageQueueService;
import eu.ist.fenix.mqs.mapper.JsonMapper;
import eu.ist.fenix.mqs.mapper.Mapper;

@Path("message")
public class MessageResource {

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{messageOid}")
  public static String deleteMessageOnMessageQueue(@PathParam("messageOid") String messageOid) {
    Bootstrap.init();
    return deleteMessage(messageOid);
  }
  
  @Atomic
  public static String deleteMessage(String messageOid) {
    try {
      Message m = (Message)MessageQueueService.fromOID(Long.parseLong(messageOid));
      Mapper jsonMapper = new JsonMapper();
      if(!m.getClaimed()) {
        throw new Exception("Message was not claimed");
      }
      String output = jsonMapper.externalizeMessage(m);
      m.delete();
      return output;
    } catch(Exception e) {
      return "{ \"error\": \"Message not found\" }";
    }
  }
}

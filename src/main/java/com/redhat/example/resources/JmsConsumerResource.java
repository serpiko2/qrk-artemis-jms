package com.redhat.example.resources;

import com.redhat.example.beans.JmsConsumer;
import com.redhat.example.models.JmsConsumeModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/consume")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JmsConsumerResource {

  @Inject
  JmsConsumer jmsConsumer;

  @POST
  @Path("/queue")
  public List<String> consumeMessageOnQueue(JmsConsumeModel.Queue getMessageBody) {
    return jmsConsumer.consumeOnQueue(getMessageBody);
  }

  @POST
  @Path("/topic")
  public List<String> postMessageOnTopic(JmsConsumeModel.Topic getMessageBody) {
    if(getMessageBody.durable) {
      if(getMessageBody.shared) {
        return jmsConsumer.consumeOnSharedDurableTopic(getMessageBody);
      } else {
        return jmsConsumer.consumeOnDurableTopic(getMessageBody);
      }
    } else {
      return jmsConsumer.consumeOnNonDurableTopic(getMessageBody);
    }
  }

}

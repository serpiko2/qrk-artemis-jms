package com.redhat.example.resources;

import com.redhat.example.models.JmsProduceModel;
import com.redhat.example.beans.JmsProducer;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.function.Consumer;

@Path("/produce")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JmsProducerResource {

    @Inject
    JmsProducer jmsProducer;

    @POST
    @Path("/queue")
    public void postMessageOnQueue(JmsProduceModel postMessageBody) {
        postOnJms(postMessageBody, jmsProducer::produceOnQueue);
    }

    @POST
    @Path("/topic")
    public void postMessageOnTopic(JmsProduceModel postMessageBody) {
        postOnJms(postMessageBody, jmsProducer::produceOnTopic);
    }

    void postOnJms(JmsProduceModel model, Consumer<JmsProduceModel> produceOn){
        for(int i = 0; i < model.number; i++)
            produceOn.accept(model);
    }

}

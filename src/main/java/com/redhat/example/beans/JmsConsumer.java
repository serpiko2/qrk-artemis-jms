package com.redhat.example.beans;

import com.redhat.example.models.JmsConsumeModel;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.util.LinkedList;
import java.util.List;

@RequestScoped
public class JmsConsumer {

  @ConfigProperty(name = "quarkus.artemis.clientId")
  String clientId;

  @Inject
  ConnectionFactory connectionFactory;

  public List<String> consumeOnQueue(JmsConsumeModel.Queue model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      JMSConsumer consumer = context.createConsumer(context.createQueue(model.destination));
      return consume(model.number, consumer, context);
    }
  }

  public List<String> consumeOnDurableTopic(JmsConsumeModel.Topic model) {
    List<String> result;
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      context.setClientID(clientId);
      JMSConsumer consumer =
          context.createDurableConsumer(context.createTopic(model.destination), model.clientId);
      result = consume(model.number, consumer, context);
    }
    return result;
  }

  public List<String> consumeOnSharedDurableTopic(JmsConsumeModel.Topic model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      JMSConsumer consumer =
          context.createSharedDurableConsumer(context.createTopic(model.destination), model.clientId);
      return consume(model.number, consumer, context);
    }
  }

  public List<String> consumeOnNonDurableTopic(JmsConsumeModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      JMSConsumer consumer = context.createConsumer(context.createTopic(model.destination));
      return consume(model.number, consumer, context);
    }
  }

  List<String> consume(int number, JMSConsumer consumer, JMSContext context) {
    List<String> messageList = new LinkedList<>();
    try {
      for (int i = 0; i < number; i++) {
        Message message = consumer.receive();
        if (message == null) return messageList;
        messageList.add(message.getBody(String.class));
        //context.acknowledge();
      }
    } catch (JMSException e) {
      throw new RuntimeException(e);
    } finally {
      //context.commit();
    }
    return messageList;
  }

}

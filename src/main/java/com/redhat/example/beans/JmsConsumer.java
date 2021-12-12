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

  public List<String> consumeOnQueue(JmsConsumeModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      JMSConsumer consumer = context.createConsumer(context.createQueue(model.destination));
      return consume(model.number, consumer);
    }
  }

  public List<String> consumeOnDurableTopic(JmsConsumeModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      context.setClientID(clientId);
      JMSConsumer consumer =
          context.createDurableConsumer(context.createTopic(model.destination), model.clientId);
      return consume(model.number, consumer);
    }
  }

  public List<String> consumeOnSharedDurableTopic(JmsConsumeModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      JMSConsumer consumer =
          context.createSharedDurableConsumer(context.createTopic(model.destination), model.clientId);
      return consume(model.number, consumer);
    }
  }

  public List<String> consumeOnNonDurableTopic(JmsConsumeModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      JMSConsumer consumer = context.createConsumer(context.createTopic(model.destination));
      return consume(model.number, consumer);
    }
  }

  List<String> consume(int number, JMSConsumer consumer) {
    List<String> messageList = new LinkedList<>();
    try {
      for (int i = 0; i < number; i++) {
        Message message = consumer.receive();
        if (message == null) return messageList;
        messageList.add(message.getBody(String.class));
      }
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
    return messageList;
  }

}

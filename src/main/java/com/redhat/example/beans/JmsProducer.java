package com.redhat.example.beans;

import com.redhat.example.models.JmsProduceModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
public class JmsProducer {

  @Inject
  ConnectionFactory connectionFactory;

  public void produceOnQueue(JmsProduceModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      System.out.println("producing message");
      context.createProducer()
          .send(context.createQueue(model.destination), model.message);
    }
  }

  public void produceOnTopic(JmsProduceModel model) {
    try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
      System.out.println("producing message");
      context.createProducer()
          .send(context.createTopic(model.destination), model.message);
    }
  }

}
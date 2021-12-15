package com.redhat.example.models;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

@RegisterForReflection
public class JmsConsumeModel {
  @JsonbProperty
  public int number;
  @JsonbProperty
  public String destination;

  public JmsConsumeModel(){}

  public static class Topic extends JmsConsumeModel {
    @JsonbProperty
    public String clientId;
    @JsonbProperty
    public boolean durable;
    @JsonbProperty
    public boolean shared;

    public Topic(){}

    @JsonbCreator
    public Topic(int number, String destination, String clientId, boolean durable, boolean shared) {
      super();
      this.number = number;
      this.destination = destination;
      this.clientId = clientId;
      this.durable = durable;
      this.shared = shared;
    }

  }

  public static class Queue extends JmsConsumeModel {
    public Queue(){}

    @JsonbCreator
    public Queue(int number, String destination) {
      this.number = number;
      this.destination = destination;
    }

  }

}

package com.redhat.example.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class JmsConsumeModel {
  @JsonbProperty
  public int number;
  @JsonbProperty
  public String destination;
  @JsonbProperty
  public String clientId;
  @JsonbProperty
  public boolean durable;
  @JsonbProperty
  public boolean shared;

  public JmsConsumeModel(){}

  @JsonbCreator
  public JmsConsumeModel(int number, String destination) {
    this.number = number;
    this.destination = destination;
  }
}

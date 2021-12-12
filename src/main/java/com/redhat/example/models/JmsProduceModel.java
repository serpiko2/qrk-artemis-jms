package com.redhat.example.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class JmsProduceModel {
  @JsonbProperty
  public int number;
  @JsonbProperty
  public String destination;
  @JsonbProperty
  public String message;

  public JmsProduceModel(){}

  @JsonbCreator
  public JmsProduceModel(int number, String destination, String message) {
    this.number = number;
    this.destination = destination;
    this.message = message;
  }

}

package com.redhat.example;

import javax.jms.Destination;
import javax.jms.JMSException;

public interface Address extends Destination {
  String getAddressName() throws JMSException;
  String toString();
}
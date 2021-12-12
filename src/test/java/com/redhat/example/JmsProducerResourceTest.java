package com.redhat.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class JmsProducerResourceTest {

    @Test
    public void testHelloEndpoint() {
    }

}
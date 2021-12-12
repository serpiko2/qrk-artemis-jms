package com.redhat.example;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeJmsProducerResourceIT extends JmsProducerResourceTest {

    // Execute the same tests but in native mode.
}
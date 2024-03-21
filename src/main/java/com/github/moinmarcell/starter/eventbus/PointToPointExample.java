package com.github.moinmarcell.starter.eventbus;

import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;

public class PointToPointExample {
  private static final Logger LOGGER = io.vertx.core.impl.logging.LoggerFactory.getLogger(PointToPointExample.class);

  public static void main(String[] args) {
    var vertx = io.vertx.core.Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }

  static class Sender extends io.vertx.core.AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(1000, id -> {
        LOGGER.info("Sending message...");
        vertx.eventBus().send(Receiver.class.getName(), "Sending message...");
      });
    }
  }

  static class Receiver extends io.vertx.core.AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Sender.class.getName(), message -> {
        LOGGER.info("Received: " + message.body());
      });
    }
  }
}

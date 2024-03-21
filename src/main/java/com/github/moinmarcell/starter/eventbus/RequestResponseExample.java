package com.github.moinmarcell.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class RequestResponseExample {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseExample.class);

  public static void main(String[] args) {
    var vertx = io.vertx.core.Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle {

    public static final String MY_REQUEST_ADDRESS = "my.request.address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      String message = "Hello World!";
      LOGGER.info("Sending: " + message);
      eventBus.<String>request(MY_REQUEST_ADDRESS, message, reply -> {
        LOGGER.info("Received reply: " + reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(RequestVerticle.MY_REQUEST_ADDRESS, message -> {
        LOGGER.info("Received: " + message.body());
        message.reply("Roger, Roger!");
      });
    }
  }
}

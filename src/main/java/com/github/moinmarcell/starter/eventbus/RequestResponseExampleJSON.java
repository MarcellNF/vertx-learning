package com.github.moinmarcell.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class RequestResponseExampleJSON {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseExampleJSON.class);

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
      JsonObject message = new JsonObject()
        .put("message", "Hello World!")
        .put("version", 1.0);
      LOGGER.info("Sending: " + message);
      eventBus.<JsonArray>request(MY_REQUEST_ADDRESS, message, reply -> {
        LOGGER.info("Received reply: " + reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<JsonObject>consumer(RequestVerticle.MY_REQUEST_ADDRESS, message -> {
        LOGGER.info("Received: " + message.body());
        message.reply(new JsonArray().add("Roger, Roger!").add(1).add(2.0).add(true));
      });
    }
  }
}

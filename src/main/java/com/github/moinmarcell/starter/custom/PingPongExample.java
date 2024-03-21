package com.github.moinmarcell.starter.custom;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class PingPongExample {
  private static final Logger LOGGER = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    var vertx = io.vertx.core.Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), logOnError());
    vertx.deployVerticle(new PongVerticle(), logOnError());
  }

  private static Handler<AsyncResult<String>> logOnError() {
    return ar -> {
      if (ar.failed()) {
        LOGGER.error("Failed to deploy verticle: " + ar.cause());
      }
    };
  }

  static class PingVerticle extends AbstractVerticle {

    public static final String MY_REQUEST_ADDRESS = PingVerticle.class.getName();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      var eventBus = vertx.eventBus();
      Ping message = new Ping("Hello", true);
      vertx.eventBus().registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));

      LOGGER.info("Sending: " + message);
      eventBus.<Pong>request(MY_REQUEST_ADDRESS, message, reply -> {
        if (reply.failed()) {
          LOGGER.error("Failed to receive reply: " + reply.cause());
          return;
        }
        LOGGER.info("Received reply: " + reply.result().body());
      });

      startPromise.complete();
    }
  }

  static class PongVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      vertx.eventBus().<Ping>consumer(PingVerticle.MY_REQUEST_ADDRESS, message -> {
        LOGGER.info("Received: " + message.body());
        message.reply(new Pong(0));
      }).exceptionHandler(error -> {
        LOGGER.error("Failed to receive message: " + error);
      });
      startPromise.complete();
    }
  }
}

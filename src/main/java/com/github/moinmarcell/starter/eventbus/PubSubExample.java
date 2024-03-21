package com.github.moinmarcell.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;

public class PubSubExample {
  private static final Logger LOGGER = io.vertx.core.impl.logging.LoggerFactory.getLogger(PubSubExample.class);

  public static void main(String[] args) {
    var vertx = io.vertx.core.Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Sub1());
    vertx.deployVerticle(Sub2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publish extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(1000, id -> {
        LOGGER.info("Publishing message...");
        vertx.eventBus().publish(Publish.class.getName(), "A message for all subscribers");
      });
    }
  }

  public static class Sub1 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
        LOGGER.info("Sub1 received: " + message.body());
      });
    }
  }

  public static class Sub2 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(), message -> {
        LOGGER.info("Sub2 received: " + message.body());
      });
    }
  }
}

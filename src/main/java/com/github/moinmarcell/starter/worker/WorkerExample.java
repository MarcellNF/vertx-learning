package com.github.moinmarcell.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class WorkerExample extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    vertx.executeBlocking(event -> {
      LOGGER.info("Exedcute blocking code");
      try {
        Thread.sleep(5000);
        event.fail("Error");
      } catch (InterruptedException e) {
        LOGGER.error("Error", e);
        event.fail(e);
      }
    }, res -> {
      if(res.succeeded()) {
        LOGGER.info("Blocking call done.");
      } else {
        LOGGER.error("Error", res.cause());
      }
    });
  }
}

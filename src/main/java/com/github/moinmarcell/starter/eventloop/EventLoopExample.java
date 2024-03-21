package com.github.moinmarcell.starter.eventloop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.logging.Logger;

public class EventLoopExample extends AbstractVerticle {
  private static final Logger LOGGER = io.vertx.core.impl.logging.LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    var vertx = io.vertx.core.Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(java.util.concurrent.TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(java.util.concurrent.TimeUnit.SECONDS)
        .setEventLoopPoolSize(2)
    );
    vertx.deployVerticle(EventLoopExample.class.getName(), new DeploymentOptions().setInstances(4));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOGGER.info("Start {}" + getClass().getName());
    startPromise.complete();
    //Thread.sleep(5000);
  }
}

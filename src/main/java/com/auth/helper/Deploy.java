package com.auth.helper;

import com.auth.builder.RouteBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class Deploy extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(Deploy.class);

  @Override
  public void start(Future<Void> fut) throws IllegalAccessException {
    // Create a router object
    Router router = Router.router(vertx);
    RouteBuilder.addExceptionHandlers(router);
    RouteBuilder.addRequestRouteHandlers(router);

    // Create the HTTP server and pass the "accept" method to the request handler.
    vertx
        .exceptionHandler(throwable -> {
          if (null != throwable.getCause()) {
            logger.error("Uncaught Failure", throwable.getCause());
          } else {
            logger.error("Uncaught Failure", throwable);
          }
        })
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(
            // Retrieve the port from the configuration,
            this.config().getInteger("http.port"),

            result -> {
              if (result.succeeded()) {
                fut.complete();
              } else {
                fut.fail(result.cause());
              }
            }
        );
  }
}

package com.auth.builder;

import com.auth.handler.EmployeeHandler;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class RouteBuilder {
	private static final Logger logger = LoggerFactory.getLogger(RouteBuilder.class);

	  public static void addExceptionHandlers(Router router) {
	    // catches the route exception and returns ISE
	    router.route().failureHandler(ctx -> {
	      if (null != ctx.failure().getCause()) {
	        logger.error("Route Failure", ctx.failure().getCause());
	      } else {
	        logger.error("Route Failure", ctx.failure());
	      }

	      ResponseBuilder.writeInternalServerError(ctx.response());
	    });

	    // catches any exception in failure handler when the response fails
	    router.exceptionHandler(throwable -> {
	      if (null != throwable.getCause()) {
	        logger.error("Uncaught Failure", throwable.getCause());
	      } else {
	        logger.error("Uncaught Failure", throwable);
	      }
	    });
	  }

	  public static void addRequestRouteHandlers(Router router) {
		  router.route("/employee*").consumes("application/x-www-form-urlencoded")
	        .produces("application/json")
	        .handler(BodyHandler.create().setDeleteUploadedFilesOnEnd(true));
		  router.post("/employee/create").consumes("application/x-www-form-urlencoded")
	        .produces("application/json")
	        .handler(EmployeeHandler::addEmployee);
	  }
}

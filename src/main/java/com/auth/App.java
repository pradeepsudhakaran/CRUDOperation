package com.auth;

import java.io.FileNotFoundException;

import com.auth.helper.ConfigJSON;
import com.auth.helper.Deploy;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        Vertx vertx = Vertx.vertx();
        int threadInstances = 1;
        try {
          vertx.deployVerticle(Deploy.class.getName(), new DeploymentOptions().setInstances(threadInstances)
              .setConfig(ConfigJSON.getConf("config.json", "db.json")), atsAsyncResult -> {
            if (!atsAsyncResult.succeeded()) {
              logger.error("Deployment Failure");
            } else {
              logger.info("Deployed Successfully");
            }
          });
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } finally {

        }
        
        
    }
}

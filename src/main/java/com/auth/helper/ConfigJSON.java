package com.auth.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import io.vertx.core.json.JsonObject;

public class ConfigJSON {
	private static final String appResourcePath = "resources" + File.separator +
		      File.separator;
    private static final String appResourcePathJava = "src/main/resources" + File.separator;

	/**
     * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static JsonObject getConf(String path) throws FileNotFoundException {
	   Scanner scanner = (new Scanner(new File(appResourcePath + path), "UTF-8"))
		  .useDelimiter("\\A");
		    return new JsonObject(scanner.next());
	}

	 /**
	 * @param paths
	 * @return
	 * @throws FileNotFoundException
     */
	public static JsonObject getConf(String... paths) throws FileNotFoundException {
	  JsonObject configs = new JsonObject();
	   for (String path : paths) {
		      configs.mergeIn(getConf(path));
		}
		return configs;
	}
}

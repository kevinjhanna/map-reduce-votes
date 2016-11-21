package ar.edu.itba.pod.hz.client.IO;

import org.slf4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.exit;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Parser {

  private Logger _logger;

  public Configuration getConfiguration(Logger logger) {
    _logger = logger;
    String name = System.getProperty("name");
    String pass = System.getProperty("pass");

    String addresses = System.getProperty("addresses");
    String[] arrayAddresses = addresses.split(";");

    String query = System.getProperty("query");

    String inputPath = System.getProperty("inPath");
    String outputPath = System.getProperty("outPath");

    validateNotNull("name", name);
    validateNotNull("pass", pass);
    validateNotNull("addresses", addresses);

    if (arrayAddresses.length < 1) {
      logger.error("Set at least one address. Addresses format: xx.xx.xx.xx:yy.yy.yy.yy");
      exit(1);
    }

//    checkIpAddress("from", arrayAddresses[1], "xx.xx.xx.xx");
//    checkIpAddress("to", arrayAddresses[1], "yy.yy.yy.yy");

    validateNotNull("query", query);

    Integer queryID = null;
    try {
      queryID = Integer.valueOf(query);
    } catch (NumberFormatException e) {
      logger.error("Query must be an integer!");
      exit(1);
    }

    if (queryID == null || queryID < 1 || queryID > 5) {
      logger.error("Argument query must be between 1 and 5");
      exit(1);
    }

    validateNotNull("inPath", inputPath);
    validateNotNull("outPath", outputPath);

    if (queryID == 3) {
      String n = System.getProperty("n");
      validateNotNull("n", n);
    }

    if (queryID == 4) {
      String prov = System.getProperty("prov");
      String tope = System.getProperty("tope");

      validateNotNull("prov", prov);
      validateNotNull("tope", tope);
    }

    return new Configuration(name, pass, arrayAddresses, queryID, inputPath, outputPath);
  }

  private void validateNotNull(String key, String value) {
    if (value == null) {
      _logger.error("Argument " + key + " is required");
      exit(1);
    }
  }

  private void checkIpAddress(String key, String address, String correct) {
    try {
      InetAddress inetAddress = InetAddress.getByName(address);
      if (inetAddress == null) {
        _logger.error("Incorrect ip format: " + key + " it should be " + correct);
        exit(1);
      }
    } catch (UnknownHostException e) {
      _logger.error("Incorrect ip format: " + key + " it should be " + correct);
      exit(1);
    }
  }


}

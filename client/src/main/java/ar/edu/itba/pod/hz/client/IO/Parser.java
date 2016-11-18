package ar.edu.itba.pod.hz.client.IO;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.exit;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Parser {

  public Configuration getConfiguration() {
    String name = System.getProperty("name");
    String pass = System.getProperty("pass");

    String addresses = System.getProperty("addresses");
    String[] arrayAddresses = addresses.split(":");

    String query = System.getProperty("query");

    String inputPath = System.getProperty("inPath");
    String outputPath = System.getProperty("outPath");

    validateNotNull("name", name);
    validateNotNull("pass", pass);
    validateNotNull("addresses", addresses);

    if (arrayAddresses.length != 2) {
      System.out.println("Error!: Addresses format: xx.xx.xx.xx:yy.yy.yy.yy");
      exit(1);
    }

    checkIpAddress("from", arrayAddresses[1], "xx.xx.xx.xx");
    checkIpAddress("to", arrayAddresses[1], "yy.yy.yy.yy");

    validateNotNull("query", query);
    Integer queryID = Integer.valueOf(query);

    if (queryID == null || queryID < 1 || queryID > 5) {
      System.out.println("Error!: Argument query must be between 1 and 5");
      exit(1);
    }

    validateNotNull("inPath", inputPath);
    validateNotNull("outPath", outputPath);

    return new Configuration(name, pass, arrayAddresses[0],arrayAddresses[1], queryID, inputPath, outputPath);
  }

  private void validateNotNull(String key, String value) {
    if (value == null) {
      System.out.println("Error!: Argument " + key + " is required");
      exit(1);
    }
  }

  private void checkIpAddress(String key, String address, String correct) {
    try {
      InetAddress inetAddress = InetAddress.getByName(address);
      if (inetAddress == null) {
        System.out.println("Incorrect ip format: " + key + " it should be " + correct);
      }
    } catch (UnknownHostException e) {
      System.out.println("Incorrect ip format: " + key + " it should be " + correct);
    }
  }


}

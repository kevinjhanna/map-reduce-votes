package ar.edu.itba.pod.hz.client.IO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Configuration {

  private String name;

  private String pass;

  private String addressFrom;

  private String addressTo;

  private Integer queryID;

  private String inputPath;

  private String outputPath;

  public Configuration(String name, String pass, String addressFrom, String addressTo, Integer queryID, String inputPath, String outputPath) {
    this.name = name;
    this.pass = pass;
    this.addressFrom = addressFrom;
    this.addressTo = addressTo;
    this.queryID = queryID;
    this.inputPath = inputPath;
    this.outputPath = outputPath;
  }

  public String getName() {
    return name;
  }

  public String getPass() {
    return pass;
  }

  public String getAddressFrom() {
    return addressFrom;
  }

  public String getAddressTo() {
    return addressTo;
  }

  public Integer getQueryID() {
    return queryID;
  }

  public String getInputPath() {
    return inputPath;
  }

  public String getOutputPath() {
    return outputPath;
  }

  public List<String> getAllAddresses() {
    List<String> addresses = new ArrayList<String>();

    String[] startParts = addressFrom.split("(?<=\\.)(?!.*\\.)");
    String[] endParts = addressTo.split("(?<=\\.)(?!.*\\.)");

    int first = Integer.parseInt(startParts[1]);
    int last = Integer.parseInt(endParts[1]);

    for (int i = first; i <= last; i++) {
      addresses.add(startParts[0] + i);
    }

    return addresses;
  }

  @Override
  public String toString() {
    String string = "";
    string += "name: " + name + "\n";
    string += "pass: " + pass + "\n";
    string += "address from: " + addressFrom + "\n";
    string += "address to: " + addressTo + "\n";
    string += "query: " + queryID + "\n";
    string += "input path: " + inputPath + "\n";
    string += "output path: " + outputPath + "\n";

    return string;
  }
}

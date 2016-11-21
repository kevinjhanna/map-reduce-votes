package ar.edu.itba.pod.hz.client.IO;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Configuration {

  private String name;

  private String pass;

  private String[] addresses;

  private Integer queryID;

  private String inputPath;

  private String outputPath;

  // For query 3
  private Integer numberOfDepartments;

  // For query 4
  private String province;

  // For query 4
  private Integer tope;

  private boolean loadMap;


  public Configuration(String name, String pass, String[] addresses,  Integer queryID, String inputPath, String outputPath, Integer numberOfDepartments, String province, Integer tope, boolean loadMap) {
    this.name = name;
    this.pass = pass;
    this.addresses = addresses;
    this.queryID = queryID;
    this.inputPath = inputPath;
    this.outputPath = outputPath;
    this.numberOfDepartments = numberOfDepartments;
    this.province = province;
    this.tope = tope;
    this.loadMap = loadMap;
  }

  public String getName() {
    return name;
  }

  public String getPass() {
    return pass;
  }

  public String[] addresses() {
    return addresses;
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

  public Integer getNumberOfDepartments() {
    return numberOfDepartments;
  }

  public String getProvince() {
    return province;
  }

  public Integer getTope() {
    return tope;
  }

  public boolean loadMap() {
    return loadMap;
  }

  @Override
  public String toString() {
    String string = "";
    string += "name: " + name + "\n";
    string += "pass: " + pass + "\n";
    string += "address:" + addresses + "\n";
    string += "query: " + queryID + "\n";
    string += "input path: " + inputPath + "\n";
    string += "output path: " + outputPath + "\n";

    return string;
  }
}

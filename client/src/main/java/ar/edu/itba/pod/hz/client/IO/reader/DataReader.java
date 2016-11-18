package ar.edu.itba.pod.hz.client.IO.reader;

import ar.edu.itba.pod.hz.model.Citizen;
import com.hazelcast.core.IMap;

import static java.lang.System.exit;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class DataReader {

  private String _inputFile;

  public DataReader(String inputFile) {
    _inputFile = inputFile;
  }

  public void loadData(final IMap<String, Citizen> iMap) {
    try {
      VotacionReader.readVotacion(_inputFile, iMap);
    } catch (Exception e) {
      System.out.println("File or directory doesn't exist");
      exit(1);
    }
  }
}

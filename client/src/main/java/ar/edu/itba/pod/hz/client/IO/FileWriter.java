package ar.edu.itba.pod.hz.client.IO;

import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by FranDepascuali on 11/20/16.
 */
public class FileWriter {

  private String _fileName;
  private Logger _logger;

  public FileWriter(String fileName, Logger logger) {
    _fileName = fileName;
    _logger = logger;
  }

  public void write(List<String> lines) {
    Path path = Paths.get(_fileName);
    try {
      Files.write(path, lines, Charset.forName("UTF-8"));
    } catch (IOException e) {
      _logger.error("couldn't create output file " + _fileName);
    }
  }

}

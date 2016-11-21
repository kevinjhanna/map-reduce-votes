package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Chronometer;
import ar.edu.itba.pod.hz.client.IO.Configuration;
import ar.edu.itba.pod.hz.client.IO.FileWriter;
import ar.edu.itba.pod.hz.client.IO.reader.DataReader;
import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.model.*;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class QueryExecutor {

  private JobProvider _jobProvider;
  private DistributedMapProvider _mapProvider;
  private DataReader _dataReader;
  private Logger _logger;
  private FileWriter _fileWriter;

  public QueryExecutor(JobProvider jobProvider, DistributedMapProvider mapProvider, DataReader dataReader, Logger logger, FileWriter fileWriter) {
    _jobProvider = jobProvider;
    _mapProvider = mapProvider;
    _dataReader = dataReader;
    _logger = logger;
    _fileWriter = fileWriter;
  }

  public void execute(Configuration configuration) throws ExecutionException, InterruptedException {
    IMap<String, Citizen> map = _mapProvider.getMap(String.valueOf(configuration.getQueryID()));
    if (configuration.loadMap()) {
      _logger.info("Inicio de lectura del archivo: " + _dataReader.getInputFile());
      _dataReader.loadData(map);
      _logger.info("Fin de lectura del archivo: " + _dataReader.getInputFile());
    }
    Chronometer chronometer = new Chronometer();
    _logger.info("Inicio del trabajo map/reduce");
    chronometer.start();
    List<String> answer = executeQuery(map, configuration);
    _fileWriter.write(answer);
    chronometer.stop();
    _logger.info("Fin del trabajo map/reduce");
    _logger.info("Duracion: " + chronometer.getTime() + " ms");
  }

  private List<String> executeQuery(IMap<String, Citizen> map, Configuration configuration) throws ExecutionException, InterruptedException {
    Job<String, Citizen> job = _jobProvider.newJob(map);

    switch (configuration.getQueryID()) {
      case 1:
        return executeQuery1(job);
      case 2:
        return executeQuery2(job);
      case 3:
        return executeQuery3(job, configuration.getNumberOfDepartments());
      case 4:
        return executeQuery4(job, configuration.getProvince(), configuration.getTope());
      case 5:
        return executeQuery5(job);
      default:
        // Should not reach here, as it is already validated in configuration
        System.out.println("Incorrect query selected");
        exit(1);
        return null;
    }
  }

  private IMap<String, Citizen> readInputFile() {
    IMap<String, Citizen> map = _mapProvider.getMap("0");
    _dataReader.loadData(map);
    return map;
  }

  private List<String> executeQuery1(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<String, Integer> answer = new Query1().execute(job);

    List<String> lines = new ArrayList();
    lines.add(String.format("%s = %d", "0-14", answer.get("0-14")));
    lines.add(String.format("%s = %d", "15-64", answer.get("15-64")));
    lines.add(String.format("%s = %d", "65-?", answer.get("65-?")));

    return lines;
  }

  private List<String> executeQuery2(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<TipoVivienda, Double> answer = new Query2().execute(job, _jobProvider, _mapProvider);

    List<Map.Entry<TipoVivienda, Double>> list = new LinkedList(answer.entrySet());

    return answer
            .entrySet()
            .stream()
            .sorted((e1, e2) -> Integer.compare(e1.getKey().getCode(), e2.getKey().getCode()))
            .map(e -> String.format("%s = %.2f", e.getKey().getCode(), e.getValue()))
            .collect(Collectors.toList());
  }

  private List<String> executeQuery3(Job<String, Citizen> job, Integer numberOfDepartments) throws ExecutionException, InterruptedException {
    List<DepartmentWithIndex> answer = new Query3(numberOfDepartments).execute(job);

    return answer
            .stream()
            .map(departmentWithIndex -> String.format("%s = %.2f", departmentWithIndex.getDepartment(), departmentWithIndex.getIndex()))
            .collect(Collectors.toList());
  }

  private List<String> executeQuery4(Job<String, Citizen> job, String province, int tope) throws ExecutionException, InterruptedException {
    List<DepartmentWithPopulation> answer = new Query4(province,tope).execute(job);

    return answer
            .stream()
            .sorted((e1, e2) -> -e1.compareTo(e2))
            .map(e -> String.format("%s = %d", e.getDepartment(), e.getPopulation()))
            .collect(Collectors.toList());
  }

  private List<String> executeQuery5(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    List<Pair<Long, List<String>>> answer = new Query5().execute(job, _jobProvider, _mapProvider);

    List<String> lines = new ArrayList<>();

    answer
            .stream()
            .sorted((e1, e2) -> Long.compare(e1.fst, e2.fst))
            .forEach(e -> {
              lines.add(String.valueOf(e.fst));
              e.snd.stream().forEach(s ->  {
                lines.add(String.valueOf(s));
              });
              lines.add("");
            });

    return lines;
  }
}

package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.IO.reader.DataReader;
import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.model.*;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class QueryExecutor {

  private JobProvider _jobProvider;
  private DistributedMapProvider _mapProvider;
  private DataReader _dataReader;

  private static final Logger logger = LoggerFactory.getLogger(QueryExecutor.class);

  public QueryExecutor(JobProvider jobProvider, DistributedMapProvider mapProvider, DataReader dataReader) {
    _jobProvider = jobProvider;
    _mapProvider = mapProvider;
    _dataReader = dataReader;

  }

  public void execute(int queryID) throws ExecutionException, InterruptedException {
    logger.info("Inicio de lectura del archivo: " + _dataReader.getInputFile());
    IMap<String, Citizen> map = readInputFile();
    logger.info("Fin de lectura del archivo: " + _dataReader.getInputFile());

    logger.info("Inicio del trabajo map/reduce");
    executeQuery(queryID, map);
    logger.info("Fin del trabajo map/reduce");
  }

  private void executeQuery(int queryID, IMap<String, Citizen> map) throws ExecutionException, InterruptedException  {
    Job<String, Citizen> job = _jobProvider.newJob(map);
    switch (queryID) {
      case 1:
        executeQuery1(job);
        break;
      case 2:
        executeQuery2(job);
        break;
      case 3:
        executeQuery3(job);
        break;
      case 4:
        executeQuery4(job);
        break;
      case 5:
        executeQuery5(job);
        break;
      default:
        // Should not reach here, as it is already validated in configuration
        System.out.println("Incorrect query selected");
        exit(1);
    }
  }

  private IMap<String, Citizen> readInputFile() {
    IMap<String, Citizen> map = _mapProvider.getMap();
    _dataReader.loadData(map);
    return map;
  }

  private void executeQuery1(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<String, Integer> answer = new Query1().execute(job);

    System.out.println(String.format("%s = %d", "0-14", answer.get("0-14")));
    System.out.println(String.format("%s = %d", "15-64", answer.get("15-64")));
    System.out.println(String.format("%s = %d", "65-?", answer.get("65-?")));
  }

  private void executeQuery2(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<TipoVivienda, Double> answer = new Query2().execute(job, _jobProvider, _mapProvider);

    List<Map.Entry<TipoVivienda, Double>> list = new LinkedList(answer.entrySet());

    list
        .stream()
        .sorted((e1, e2) -> Integer.compare(e1.getKey().getCode(), e2.getKey().getCode()))
        .forEach (e -> {
          System.out.println(String.format("%s = %.2f", e.getKey().getCode(), e.getValue()));
        });
  }

  private void executeQuery3(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    List<DepartmentWithIndex> answer = new Query3().execute(job);

    for (DepartmentWithIndex departmentWithIndex : answer) {
      System.out.println(String.format("%s = %.2f", departmentWithIndex.getDepartment(), departmentWithIndex.getIndex()));
    }
  }

  private void executeQuery4(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    List<DepartmentWithPopulation> answer = new Query4().execute(job);

    answer
      .stream()
      .sorted((e1, e2) -> -e1.compareTo(e2))
      .forEach (e -> {
          System.out.println(String.format("%s = %d", e.getDepartment(), e.getPopulation()));
      });
  }

  private void executeQuery5(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
      List<Pair<Long, List<String>>> answer = new Query5().execute(job, _jobProvider, _mapProvider);

      answer
          .stream()
          .sorted((e1, e2) -> Long.compare(e1.fst, e2.fst))
          .forEach (e -> {
            System.out.println(e.fst);
              e.snd.stream().forEach(s -> {
                System.out.println(s);
              });
          });
  }
}

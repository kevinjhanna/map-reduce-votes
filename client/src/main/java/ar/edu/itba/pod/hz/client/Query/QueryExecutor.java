package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.IO.reader.DataReader;
import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.DepartmentWithIndex;
import ar.edu.itba.pod.hz.model.DepartmentWithPopulation;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class QueryExecutor {

  private JobProvider _jobProvider;
  private DistributedMapProvider _mapProvider;
  private DataReader _dataReader;

  public QueryExecutor(JobProvider jobProvider, DistributedMapProvider mapProvider, DataReader dataReader) {
    _jobProvider = jobProvider;
    _mapProvider = mapProvider;
    _dataReader = dataReader;
  }

  public void execute(int queryID) throws ExecutionException, InterruptedException {
    IMap<String, Citizen> myMap = _mapProvider.getMap();
    _dataReader.loadData(myMap);
    Job<String, Citizen> job = _jobProvider.newJob(myMap);

    System.out.println("Executing Query " + queryID);
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

  private void executeQuery1(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<String, Integer> answer = new Query1().execute(job);

    for (Map.Entry<String, Integer> e : answer.entrySet()) {
      System.out.println(String.format("%s => %d", e.getKey(), e.getValue()));
    }
  }

  private void executeQuery2(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<TipoVivienda, Double> answer = new Query2().execute(job, _jobProvider, _mapProvider);

    for (Map.Entry<TipoVivienda, Double> e : answer.entrySet()) {
      System.out.println(String.format("%s => %f", e.getKey(), e.getValue()));
    }
  }

  private void executeQuery3(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    List<DepartmentWithIndex> answer = new Query3().execute(job);

    for (DepartmentWithIndex departmentWithIndex : answer) {
      System.out.println("Department: " + departmentWithIndex.getIndex() + " index: " + departmentWithIndex.getIndex());
    }
  }

  private void executeQuery4(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    List<DepartmentWithPopulation> answer = new Query4().execute(job);

    for (DepartmentWithPopulation departmentWithPopulation : answer) {
      System.out.println(String.format("%s = %d", departmentWithPopulation.getDepartment(), departmentWithPopulation.getPopulation()));
    }
  }

  private void executeQuery5(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    Map<Long, List<String>> answer = new Query5().execute(job, _jobProvider, _mapProvider);

    for (Map.Entry<Long, List<String>> e : answer.entrySet()) {
      System.out.println(String.format("%s => %s", e.getKey(), e.getValue()));
    }
  }
}

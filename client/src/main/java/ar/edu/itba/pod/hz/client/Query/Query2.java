package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.Query.Subquery.AveragePerHomeTypeQuery;
import ar.edu.itba.pod.hz.client.Query.Subquery.CitizensPerHomeQuery;
import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Query2 implements QueryType {

  @Override
  public Map<TipoVivienda, Double> execute(JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException {
    IMap<String, Citizen> myMap = mapProvider.getMap();
    loadData(myMap);
    Job<String, Citizen> job = jobProvider.newJob(myMap);
    Map<Integer, NumberOfCitizensPerHomeType> map = new CitizensPerHomeQuery().execute(job);

    IMap<Integer, NumberOfCitizensPerHomeType> other = mapProvider.getMap();
    other.putAll(map);
    Job<Integer, NumberOfCitizensPerHomeType> newJob = jobProvider.newJob(other);
    Map<TipoVivienda, Double> answer = new AveragePerHomeTypeQuery().execute(newJob);

    return answer;
  }

  private static void loadData(final IMap<String, Citizen> iMap) {
    try {
      VotacionReader.readVotacion(System.getProperty("inPath"), iMap);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}

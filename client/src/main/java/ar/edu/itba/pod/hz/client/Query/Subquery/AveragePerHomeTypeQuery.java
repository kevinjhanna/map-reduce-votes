package ar.edu.itba.pod.hz.client.Query.Subquery;

import ar.edu.itba.pod.hz.client.Query.SimpleQueryType;
import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import ar.edu.itba.pod.hz.mr.Query2PeoplePerHomeTypeMapperFactory;
import ar.edu.itba.pod.hz.mr.Query2PeoplePerHomeTypeReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class AveragePerHomeTypeQuery implements SimpleQueryType<Integer, NumberOfCitizensPerHomeType, Map<TipoVivienda, Double>> {

  @Override
  public Map<TipoVivienda, Double> execute(Job<Integer, NumberOfCitizensPerHomeType> job) throws ExecutionException, InterruptedException {
    ICompletableFuture<Map<TipoVivienda, Double>> future = job
            .mapper(new Query2PeoplePerHomeTypeMapperFactory())
            .reducer(new Query2PeoplePerHomeTypeReducerFactory())
            .submit();

    return future.get();
  }

}

package ar.edu.itba.pod.hz.client.Query.Subquery;

import ar.edu.itba.pod.hz.client.Query.SimpleQueryType;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.mr.Query2HomesPerHomeTypeMapperFactory;
import ar.edu.itba.pod.hz.mr.Query2HomesPerHomeTypeReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class CitizensPerHomeQuery implements SimpleQueryType<String, Citizen, Map<Integer, NumberOfCitizensPerHomeType>> {

  @Override
  public Map<Integer, NumberOfCitizensPerHomeType> execute(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    ICompletableFuture<Map<Integer, NumberOfCitizensPerHomeType>> future = job
            .mapper(new Query2HomesPerHomeTypeMapperFactory())
            .reducer(new Query2HomesPerHomeTypeReducerFactory())
            .submit();

    return future.get();

  }
}

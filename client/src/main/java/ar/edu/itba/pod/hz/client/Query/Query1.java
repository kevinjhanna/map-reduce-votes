package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.mr.Query1MapperFactory;
import ar.edu.itba.pod.hz.mr.Query1ReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Query1 implements SimpleQueryType<String, Citizen, Map<String, Integer>> {

  @Override
  public Map<String, Integer> execute(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    ICompletableFuture<Map<String, Integer>> future = job
            .mapper(new Query1MapperFactory())
            .reducer(new Query1ReducerFactory())
            .submit();

    return future.get();
  }

}

package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.Pair;
import ar.edu.itba.pod.hz.mr.*;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Query5 implements ComplexQueryType<String, Citizen, List<Pair<Long, List<String>>>> {

  @Override
  public List<Pair<Long, List<String>>> execute(Job<String, Citizen> job, JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException {

    ICompletableFuture<Map<String, Long>> future = job
            .mapper(new Query5MapperFactoryPart1())
            .reducer(new Query5ReducerFactoryPart1())
            .submit();

    Map<String, Long> rta = future.get();

    IMap<String, Long> otherMap = mapProvider.getMap();
    otherMap.putAll(rta);
    Job<String, Long> job2 = jobProvider.newJob(otherMap);

    ICompletableFuture<List<Pair<Long, List<String>>>> future2 = job2
            .mapper(new Query5MapperFactoryPart2())
            .reducer(new Query5ReducerFactoryPart2())
            .submit(new Query5Collator());

    return future2.get();
  }

}

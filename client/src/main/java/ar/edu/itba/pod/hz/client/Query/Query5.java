package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.mr.Query5MapperFactoryPart1;
import ar.edu.itba.pod.hz.mr.Query5MapperFactoryPart2;
import ar.edu.itba.pod.hz.mr.Query5ReducerFactoryPart1;
import ar.edu.itba.pod.hz.mr.Query5ReducerFactoryPart2;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Query5 implements QueryType {

  @Override
  public Map<Long, List<String>> execute(JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException {
    IMap<String, Citizen> map = mapProvider.getMap();
    loadData(map);
    Job<String, Citizen> job = jobProvider.newJob(map);

    ICompletableFuture<Map<String, Long>> future = job
            .mapper(new Query5MapperFactoryPart1())
            .reducer(new Query5ReducerFactoryPart1())
            .submit();

    Map<String, Long> rta = future.get();

    IMap<String, Long> otherMap = mapProvider.getMap();
    otherMap.putAll(rta);
    Job<String, Long> job2 = jobProvider.newJob(otherMap);

    ICompletableFuture<Map<Long, List<String>>> future2 = job2
            .mapper(new Query5MapperFactoryPart2())
            .reducer(new Query5ReducerFactoryPart2())
            .submit();

    return future2.get();
  }

  private static void loadData(final IMap<String, Citizen> iMap) {
    try {
      VotacionReader.readVotacion(System.getProperty("inPath"), iMap);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}

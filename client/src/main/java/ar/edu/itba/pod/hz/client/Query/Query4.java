package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.DepartmentWithPopulation;
import ar.edu.itba.pod.hz.mr.Query4Collator;
import ar.edu.itba.pod.hz.mr.Query4MapperFactory;
import ar.edu.itba.pod.hz.mr.Query4ReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Query4 implements QueryType {

  @Override
  public List<DepartmentWithPopulation> execute(JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException {
    IMap<String, Citizen> map = mapProvider.getMap();
    loadData(map);
    Job<String, Citizen> job = jobProvider.newJob(map);
    ICompletableFuture<List<DepartmentWithPopulation>> future = job
                                                              .mapper(new Query4MapperFactory("Santa Fe"))
                                                              .reducer(new Query4ReducerFactory())
                                                              .submit(new Query4Collator(7));

    return future.get();
  }

  private static void loadData(final IMap<String, Citizen> iMap) {
    try {
      VotacionReader.readVotacion(System.getProperty("inPath"), iMap);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

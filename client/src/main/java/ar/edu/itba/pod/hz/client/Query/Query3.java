package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import ar.edu.itba.pod.hz.client.reader.VotacionReader;
import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.DepartmentWithIndex;
import ar.edu.itba.pod.hz.mr.Query3MapperFactory;
import ar.edu.itba.pod.hz.mr.Query3ReducerFactory;
import ar.edu.itba.pod.hz.mr.TopCollator;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class Query3 implements QueryType {


  @Override
  public List<DepartmentWithIndex> execute(JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException {
    IMap<String, Citizen> map = mapProvider.getMap();
    loadData(map);
    Job<String, Citizen> job = jobProvider.newJob(map);
    ICompletableFuture<List<DepartmentWithIndex>> future = job
            .mapper(new Query3MapperFactory())
            .reducer(new Query3ReducerFactory())
            .submit(new TopCollator(10));

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

package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.DepartmentWithPopulation;
import ar.edu.itba.pod.hz.mr.Query4Collator;
import ar.edu.itba.pod.hz.mr.Query4MapperFactory;
import ar.edu.itba.pod.hz.mr.Query4ReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query4 implements SimpleQueryType<String, Citizen, List<DepartmentWithPopulation>> {

  private String _province;
  private int _tope;

  public Query4(String province, int tope) {
    _province = province;
    _tope = tope;
  }

  @Override
  public List<DepartmentWithPopulation> execute(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    ICompletableFuture<List<DepartmentWithPopulation>> future = job
            .mapper(new Query4MapperFactory(_province))
            .reducer(new Query4ReducerFactory())
            .submit(new Query4Collator(_tope));

    return future.get();
  }
}

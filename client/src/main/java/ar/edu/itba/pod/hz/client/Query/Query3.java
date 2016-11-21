package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.DepartmentWithIndex;
import ar.edu.itba.pod.hz.mr.Query3MapperFactory;
import ar.edu.itba.pod.hz.mr.Query3ReducerFactory;
import ar.edu.itba.pod.hz.mr.TopCollator;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.mapreduce.Job;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query3 implements SimpleQueryType<String, Citizen, List<DepartmentWithIndex>> {

  private int _numberOfDepartments;

  public Query3(int numberOfDepartments) {
    _numberOfDepartments = numberOfDepartments;
  }

  @Override
  public List<DepartmentWithIndex> execute(Job<String, Citizen> job) throws ExecutionException, InterruptedException {
    ICompletableFuture<List<DepartmentWithIndex>> future = job
            .mapper(new Query3MapperFactory())
            .reducer(new Query3ReducerFactory())
            .submit(new TopCollator(_numberOfDepartments));

    return future.get();
  }

}

package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;
import com.hazelcast.mapreduce.Job;

import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public interface ComplexQueryType<KeyIn, ValueIn, ValueOut> {

  ValueOut execute(Job<KeyIn, ValueIn> job, JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException;

}

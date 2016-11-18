package ar.edu.itba.pod.hz.client.Query;

import com.hazelcast.mapreduce.Job;

import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public interface SimpleQueryType<KeyIn, ValueIn, ValueOut> {

  ValueOut execute(Job<KeyIn, ValueIn> job) throws ExecutionException, InterruptedException;
}

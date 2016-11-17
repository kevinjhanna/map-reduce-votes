package ar.edu.itba.pod.hz.client.Query;

import com.hazelcast.mapreduce.Job;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public interface MapQuery<KeyIn, ValueIn, KeyOutType, ValueOutType> {

  public Map<KeyOutType, ValueOutType> execute(Job<KeyIn, ValueIn> job) throws ExecutionException, InterruptedException;
}

package ar.edu.itba.pod.hz.client.Query;

import ar.edu.itba.pod.hz.client.Provider.DistributedMapProvider;
import ar.edu.itba.pod.hz.client.Provider.JobProvider;

import java.util.concurrent.ExecutionException;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public interface QueryType {

  <ValueOut> ValueOut execute(JobProvider jobProvider, DistributedMapProvider mapProvider) throws ExecutionException, InterruptedException;

}
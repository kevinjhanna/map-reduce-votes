package ar.edu.itba.pod.hz;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class JobProvider {

  private static String JOB_TRACKER_TYPE = "default";

  private HazelcastInstance _client;

  public JobProvider(HazelcastInstance client) {
    _client = client;
  }

  public <JobKeyType, JobValueType> Job<JobKeyType, JobValueType> newJob(IMap<JobKeyType, JobValueType> map) {
    JobTracker tracker = _client.getJobTracker(JOB_TRACKER_TYPE);
    KeyValueSource<JobKeyType, JobValueType> source = KeyValueSource.fromMap(map);

    return tracker.newJob(source);
  }

}

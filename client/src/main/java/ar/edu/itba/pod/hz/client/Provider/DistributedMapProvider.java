package ar.edu.itba.pod.hz.client.Provider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.concurrent.atomic.AtomicInteger;

public class DistributedMapProvider {

  private static String AVAILABLE_MAP = "paso2015";

  private AtomicInteger currentMapIndex;

  private HazelcastInstance _client;

  public DistributedMapProvider(HazelcastInstance client) {
    _client = client;
  }

  public <KeyType, ValueType> IMap<KeyType, ValueType> getMap(String namespace) {
    IMap<KeyType, ValueType> map = _client.getMap(AVAILABLE_MAP + "_" + namespace);

    return map;
  }
}

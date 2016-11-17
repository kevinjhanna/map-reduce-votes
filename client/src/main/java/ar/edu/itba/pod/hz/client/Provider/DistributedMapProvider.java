package ar.edu.itba.pod.hz.client.Provider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by FranDepascuali on 11/17/16.
 */
public class DistributedMapProvider {

  private static String AVAILABLE_MAP = "paso2015";

  private AtomicInteger currentMapIndex;

  private HazelcastInstance _client;

  public DistributedMapProvider(HazelcastInstance client) {
    _client = client;
    currentMapIndex = new AtomicInteger(0);
  }

  public synchronized <KeyType, ValueType> IMap<KeyType, ValueType> getMap() {
    int current = currentMapIndex.get();
    IMap<KeyType, ValueType> map = _client.getMap(AVAILABLE_MAP + "_" + current);
    currentMapIndex.set(current + 1);

    return map;
  }
}

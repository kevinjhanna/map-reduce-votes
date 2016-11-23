package ar.edu.itba.pod.hz.client.Provider;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.concurrent.atomic.AtomicInteger;

public class DistributedMapProvider {

  private static String AVAILABLE_MAP = "52034-53080";

  private AtomicInteger currentMapIndex;

  private HazelcastInstance _client;

  private boolean loadMap;


  public DistributedMapProvider(HazelcastInstance client, boolean loadmap) {
    _client = client;
    this.loadMap = loadmap;
  }

  public <KeyType, ValueType> IMap<KeyType, ValueType> getMap(String namespace) {
    IMap<KeyType, ValueType> map = _client.getMap(AVAILABLE_MAP + "_" + namespace);
    if (loadMap) {
      map.clear();
    }

    return map;
  }
}

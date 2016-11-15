package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Collator;

import java.util.HashMap;
import java.util.Map;

public class AverageCollator implements Collator<Map.Entry<TipoVivienda, Integer>, Map<TipoVivienda, Double>> {

    @Override
    public Map<TipoVivienda, Double> collate(Iterable <Map.Entry<TipoVivienda, Integer>> iterator) {

        Integer total = 0;

        for (Map.Entry<TipoVivienda, Integer> entry: iterator) {
            total += entry.getValue();
        }

        Map<TipoVivienda, Double> averages = new HashMap();

        for (Map.Entry<TipoVivienda, Integer> entry: iterator) {
            averages.put(entry.getKey(), new Double(entry.getValue()) / total * 100);
        }

        return averages;
    }
}

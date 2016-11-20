package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.DepartmentWithIndex;
import com.hazelcast.mapreduce.Collator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopCollator implements Collator<Map.Entry<String, Double>, List<DepartmentWithIndex> > {
    private int n;

    public TopCollator(int n) {
        this.n = n;
    }

    @Override
    public List<DepartmentWithIndex> collate(Iterable <Map.Entry<String, Double>> iterable) {
        List<DepartmentWithIndex> list = new LinkedList<>();

        for (Map.Entry<String, Double> entry : iterable) {
            list.add(new DepartmentWithIndex(entry.getKey().split(":")[0], entry.getValue()));
        }

        return list
            .stream()
            .sorted(Collections.reverseOrder())
            .limit(n)
            .collect(Collectors.toList());
    }
}

package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.DepartmentWithIndex;
import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class TopCollator implements Collator<Map.Entry<String, Double>, List<DepartmentWithIndex> > {
    private long n;

    public TopCollator(long n) {
        this.n = n;
    }

    @Override
    public List<DepartmentWithIndex> collate(Iterable <Map.Entry<String, Double>> iterator) {

        SortedSet<DepartmentWithIndex> set = new TreeSet<DepartmentWithIndex>();

        for (Map.Entry<String, Double> entry : iterator) {
            if (set.size() < n) {
                set.add(new DepartmentWithIndex(entry.getKey(), entry.getValue()));
            } else if (set.first().getIndex().compareTo(entry.getValue()) == -1) {
                set.remove(set.first());
                set.add(new DepartmentWithIndex(entry.getKey(), entry.getValue()));
            }
        }

        return new LinkedList(set);
    }
}

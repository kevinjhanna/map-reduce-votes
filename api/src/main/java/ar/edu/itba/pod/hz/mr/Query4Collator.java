package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.DepartmentWithPopulation;
import com.hazelcast.mapreduce.Collator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Query4Collator implements Collator<Map.Entry<String, Long>, List<DepartmentWithPopulation> > {
    private long top;

    public Query4Collator(long top) {
        this.top = top;
    }

    @Override
    public List<DepartmentWithPopulation> collate(Iterable <Map.Entry<String, Long>> iterator) {
        List list = new LinkedList<DepartmentWithPopulation>();

        for (Map.Entry<String, Long> entry : iterator) {
            if (entry.getValue() < top) {
                list.add(new DepartmentWithPopulation(entry.getKey(), entry.getValue()));
            }
        }

        return list;
    }
}

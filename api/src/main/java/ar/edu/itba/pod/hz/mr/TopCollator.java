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
        Set set = new HashSet();
        DepartmentWithIndex min = null;

        for (Map.Entry<String, Double> entry : iterator) {
            DepartmentWithIndex department = new DepartmentWithIndex(entry.getKey(), entry.getValue());

            if (set.size() < n) {
                set.add(department);
                if (min == null || department.compareTo(min) == -1) {
                    min = department;
                }
            } else if (department.compareTo(min) == - 1) {
                set.remove(min);
                set.add(department);
            }
        }

        List<DepartmentWithIndex> list = new LinkedList(set);
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
}

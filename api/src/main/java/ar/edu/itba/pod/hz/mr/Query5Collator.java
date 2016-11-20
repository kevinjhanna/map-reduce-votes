package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Query5Collator implements Collator<Map.Entry<Long, List<String>>, List<Pair<Long, List<String>>>> {

    @Override
    public List<Pair<Long, List<String>>> collate(Iterable<Map.Entry<Long, List<String>>> iterable) {
        List<Pair<Long, List<String>>> list = new LinkedList<Pair<Long, List<String>>>();

        for (Map.Entry<Long, List<String>> entry : iterable) {
           // Discard elements without other pairs
           if (entry.getValue().size() == 1) {
               continue;
           }

            list.add(new Pair<Long, List<String>>(entry.getKey(), convertToPairs(entry.getValue())));
        }

        return list;
    }

    private List<String> convertToPairs(List<String> list) {
        String[] departments = list.toArray(new String[list.size()]);

        List<String> pairs = new LinkedList<String>();
        for (int i = 0; i < departments.length; i++) {
            for (int j = i+1; j < departments.length; j++) {
                pairs.add(String.format("%s + %s", departments[i], departments[j]));
            }

        }

        return pairs;
    }
}

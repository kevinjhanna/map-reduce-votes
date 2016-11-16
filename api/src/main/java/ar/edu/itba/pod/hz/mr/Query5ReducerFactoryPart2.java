package ar.edu.itba.pod.hz.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.LinkedList;
import java.util.List;

public class Query5ReducerFactoryPart2 implements ReducerFactory<Long, String, List<String>> {
    private static final long serialVersionUID = 7760070699178320492L;

    @Override
    public Reducer<String, List<String>> newReducer(Long hundreds) {
        return new Reducer<String, List<String>>() {
            List<String> departments;


            @Override
            public void beginReduce()
            {
             departments = new LinkedList<String>();
            }

            @Override
            public void reduce(final String string) {
                departments.add(string);
            }

            @Override
            public List<String> finalizeReduce() {
                return departments;
            }
        };
    }
}

package ar.edu.itba.pod.hz.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query1ReducerFactory implements ReducerFactory<String, Integer, Integer> {
    private static final long serialVersionUID = 7760070699178320492L;

    @Override
    public Reducer<Integer, Integer> newReducer(final String range) {
        return new Reducer<Integer, Integer>() {
            int sum;

            @Override
            public void beginReduce() // una sola vez en cada instancia
            {
                sum = 0;
            }

            @Override
            public void reduce(final Integer value) {
                sum += value;
            }

            @Override
            public Integer finalizeReduce() {
                return sum;
            }
        };
    }
}

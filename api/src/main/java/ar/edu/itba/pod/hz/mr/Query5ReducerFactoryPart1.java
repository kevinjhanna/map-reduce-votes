package ar.edu.itba.pod.hz.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query5ReducerFactoryPart1 implements ReducerFactory<String, Integer, Long> {
    private static final long serialVersionUID = 7760070699178320492L;

    @Override
    public Reducer<Integer, Long> newReducer(final String department) {
        return new Reducer<Integer, Long>() {
            long population;

            @Override
            public void beginReduce() // una sola vez en cada instancia
            {
                population = 0;
            }

            @Override
            public void reduce(final Integer n) {
                population++;
            }

            @Override
            public Long finalizeReduce() {
                return population;
            }
        };
    }
}

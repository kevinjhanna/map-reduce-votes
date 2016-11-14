package ar.edu.itba.pod.hz.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query3ReducerFactory implements ReducerFactory<String, Boolean, Double> {
    private static final long serialVersionUID = 7760070699178320492L;

    @Override
    public Reducer<Boolean, Double> newReducer(final String department) {
        return new Reducer<Boolean, Double>() {
            long population;
            long illiteracy;

            @Override
            public void beginReduce() // una sola vez en cada instancia
            {
                population = 0;
                illiteracy = 0;
            }

            @Override
            public void reduce(final Boolean literacy) {
                population++;
                illiteracy += literacy ? 0 : 1;
            }

            @Override
            public Double finalizeReduce() {
                double index = Double.valueOf(illiteracy) / population;
                System.out.println(String.format("FinalReduce for %s = %s", department, index));
                return index;
            }
        };
    }
}

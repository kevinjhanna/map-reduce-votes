package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Citizen;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Query5MapperFactoryPart1 implements Mapper<String, Citizen, String, Integer> {
    private static final long serialVersionUID = -3713325164465665033L;

    String province;

    public Query5MapperFactoryPart1() {
    }

    @Override
    public void map(final String keyinput, final Citizen valueinput,
            final Context<String, Integer> context) {

        String key = valueinput.getNombredepto();
        context.emit(key, 1);
    }
}
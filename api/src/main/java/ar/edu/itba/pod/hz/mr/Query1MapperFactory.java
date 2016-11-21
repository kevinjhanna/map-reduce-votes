package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Citizen;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Query1MapperFactory implements Mapper<String, Citizen, String, Integer> {
    private static final long serialVersionUID = -3713325164465665033L;

    @Override
    public void map(final String keyinput, final Citizen valueinput,
            final Context<String, Integer> context) {

        int age = valueinput.getEdad();
        String key = null;

        if (age <= 14) {
            key = "0-14";
        } else if (age <= 64) {
            key = "15-64";
        } else {
            key = "65-?";
        }


        context.emit(key, 1);
    }
}
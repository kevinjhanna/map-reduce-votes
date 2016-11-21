package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Citizen;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Query3MapperFactory implements Mapper<String, Citizen, String, Boolean> {
    private static final long serialVersionUID = -3713325164465665033L;

    @Override
    public void map(final String keyinput, final Citizen valueinput,
            final Context<String, Boolean> context) {
        String key = String.format("%s:%s", valueinput.getNombredepto(), valueinput.getNombreprov());

        context.emit(key, valueinput.getAlfabetismo() == 2);
    }
}
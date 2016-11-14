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
        System.out.println(String.format("Llega KeyInput: %s con ValueInput: %s", keyinput, valueinput));

        String key = valueinput.getNombredepto();

        context.emit(key, valueinput.isAlfabetismo());

//        System.out.println(String.format("Se emite (%s, %s)", key, 1));
    }
}
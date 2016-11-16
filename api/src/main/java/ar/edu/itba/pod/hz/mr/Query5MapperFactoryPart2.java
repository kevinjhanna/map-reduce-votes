package ar.edu.itba.pod.hz.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

// Parametrizar con los tipos de keyInput, ,valueInput, keyoutput, valueOutput
public class Query5MapperFactoryPart2 implements Mapper<String, Long, Long, String> {
    private static final long serialVersionUID = -3713325164465665033L;

    public Long getHundred(Long n) {
        return (long)(Math.floor(n / 100.0)) * 100;
    }

    @Override
    public void map(final String keyinput, final Long valueinput,
            final Context<Long, String> context) {

        context.emit(getHundred(valueinput), keyinput);
    }
}
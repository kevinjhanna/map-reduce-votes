package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by FranDepascuali on 11/13/16.
 */
public class Query2MapperFactory implements Mapper<String, Citizen, TipoVivienda, Integer> {
  private static final long serialVersionUID = -3713325164465665033L;

  @Override
  public void map(final String keyinput, final Citizen valueinput,
                  final Context<TipoVivienda, Integer> context) {
    System.out.println(String.format("Llega KeyInput: %s con ValueInput: %s", keyinput, valueinput));
    TipoVivienda key = TipoVivienda.from(valueinput.getTipovivienda());
    context.emit(key, 1);

    System.out.println(String.format("Se emite (%s, %s)", key, 1));
  }
}

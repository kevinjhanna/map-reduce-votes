package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by FranDepascuali on 11/13/16.
 */
public class Query2HomesPerHomeTypeMapperFactory implements Mapper<String, Citizen, Integer, NumberOfCitizensPerHomeType> {

  @Override
  public void map(final String keyinput,
                  final Citizen valueinput,
                  final Context<Integer, NumberOfCitizensPerHomeType> context) {
    System.out.println(String.format("Llega KeyInput: %s con ValueInput: %s", keyinput, valueinput));

    Integer key = valueinput.getHogarid();
    NumberOfCitizensPerHomeType value = new NumberOfCitizensPerHomeType(valueinput.getHogarid(), TipoVivienda.from(valueinput.getTipovivienda()));
    context.emit(key, value);

    System.out.println(String.format("Se emite (%s, %s)", key, value));
  }
}

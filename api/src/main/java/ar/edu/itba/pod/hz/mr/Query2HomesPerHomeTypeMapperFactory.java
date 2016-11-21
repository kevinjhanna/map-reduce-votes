package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.Citizen;
import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query2HomesPerHomeTypeMapperFactory implements Mapper<String, Citizen, Integer, NumberOfCitizensPerHomeType> {

  @Override
  public void map(final String keyinput,
                  final Citizen valueinput,
                  final Context<Integer, NumberOfCitizensPerHomeType> context) {

    Integer key = valueinput.getHogarid();
    NumberOfCitizensPerHomeType value = new NumberOfCitizensPerHomeType(valueinput.getHogarid(), TipoVivienda.from(valueinput.getTipovivienda()));
    context.emit(key, value);
  }
}

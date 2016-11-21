package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query2PeoplePerHomeTypeMapperFactory implements Mapper<Integer, NumberOfCitizensPerHomeType, TipoVivienda, Integer> {

  @Override
  public void map(Integer homeId,
                  NumberOfCitizensPerHomeType citizensAndType,
                  Context<TipoVivienda, Integer> context) {

    TipoVivienda key = citizensAndType.getType();
    Integer value = citizensAndType.getNumber();

    context.emit(key, value);
  }
}

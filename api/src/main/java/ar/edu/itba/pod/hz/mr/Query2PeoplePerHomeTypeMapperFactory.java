package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by FranDepascuali on 11/16/16.
 */
public class Query2PeoplePerHomeTypeMapperFactory implements Mapper<Integer, NumberOfCitizensPerHomeType, TipoVivienda, Integer> {

  @Override
  public void map(Integer homeId,
                  NumberOfCitizensPerHomeType citizensAndType,
                  Context<TipoVivienda, Integer> context) {
    System.out.println(String.format("Llega KeyInput: %d con ValueInput: %s", homeId, citizensAndType));

    TipoVivienda key = citizensAndType.getType();
    Integer value = citizensAndType.getNumber();

    context.emit(key, value);

    System.out.println(String.format("Se emite (%s, %d)", key, value));
  }
}

package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.NumberOfCitizensPerHomeType;
import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by FranDepascuali on 11/13/16.
 */
public class Query2HomesPerHomeTypeReducerFactory implements ReducerFactory<Integer, NumberOfCitizensPerHomeType, NumberOfCitizensPerHomeType> {
  private static final long serialVersionUID = 7760070699178320490L;

  @Override
  public Reducer<NumberOfCitizensPerHomeType, NumberOfCitizensPerHomeType> newReducer(final Integer hogarID) {
    return new Reducer<NumberOfCitizensPerHomeType, NumberOfCitizensPerHomeType>() {

      Integer numberOfPeoplePerHouseType;
      TipoVivienda houseType = null;

      @Override
      public void beginReduce() // una sola vez en cada instancia
      {
        numberOfPeoplePerHouseType = 0;
      }

      @Override
      public void reduce(final NumberOfCitizensPerHomeType value) {
        numberOfPeoplePerHouseType += 1;
        houseType = value.getType();
      }

      @Override
      public NumberOfCitizensPerHomeType finalizeReduce() {
//        System.out.println(String.format("FinalReduce for %s = %s", houseType, numberOfPeoplePerHouseType));
        return new NumberOfCitizensPerHomeType(numberOfPeoplePerHouseType, houseType);
      }
    };
  }
}
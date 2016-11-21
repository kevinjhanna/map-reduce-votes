package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query2PeoplePerHomeTypeReducerFactory implements ReducerFactory<TipoVivienda, Integer, Double> {
  private static final long serialVersionUID = 7760070699178320492L;

  @Override
  public Reducer<Integer, Double> newReducer(TipoVivienda tipoVivienda) {

    return new Reducer<Integer, Double>() {

      Integer numberOfCitizens;
      Integer count;

      @Override
      public void beginReduce() // una sola vez en cada instancia
      {
        numberOfCitizens = 0;
        count = 0;
      }

      @Override
      public void reduce(Integer value) {
        numberOfCitizens += value;
        count += 1;
      }

      @Override
      public Double finalizeReduce() {
        Double average = new Double(numberOfCitizens) / count;
        return average;
      }
    };
  }
}

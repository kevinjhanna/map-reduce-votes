package ar.edu.itba.pod.hz.mr;

import ar.edu.itba.pod.hz.model.TipoVivienda;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by FranDepascuali on 11/13/16.
 */
public class Query2ReducerFactory implements ReducerFactory<TipoVivienda, Integer, Integer> {
  private static final long serialVersionUID = 7760070699178320492L;

  @Override
  public Reducer<Integer, Integer> newReducer(final TipoVivienda tipoVivienda) {
    return new Reducer<Integer, Integer>() {
      //            private FormulaTupla max;
      int sum;

      @Override
      public void beginReduce() // una sola vez en cada instancia
      {
        sum = 0;
      }

      @Override
      public void reduce(final Integer value) {
        sum += value;
      }

      @Override
      public Integer finalizeReduce() {
        System.out.println(String.format("FinalReduce for %s = %s", tipoVivienda, sum));
        return sum;
      }
    };
  }
}


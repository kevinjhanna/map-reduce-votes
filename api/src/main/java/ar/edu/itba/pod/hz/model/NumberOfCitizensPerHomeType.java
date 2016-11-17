package ar.edu.itba.pod.hz.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

/**
 * Created by FranDepascuali on 11/16/16.
 */
public class NumberOfCitizensPerHomeType implements DataSerializable {

  Integer number;
  TipoVivienda type;

  public NumberOfCitizensPerHomeType() {

  }

  public NumberOfCitizensPerHomeType(Integer number, TipoVivienda type) {
    this.number = number;
    this.type = type;
  }

  @Override
  public void writeData(ObjectDataOutput out) throws IOException {
    out.writeInt(number);
    out.writeInt(type.getCode());
  }

  @Override
  public void readData(ObjectDataInput in) throws IOException {
    this.number = in.readInt();
    this.type = TipoVivienda.from(in.readInt());
  }

  public Integer getNumber() {
    return number;
  }

  public TipoVivienda getType() {
    return type;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public void setType(TipoVivienda type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "(" + number + ", " + type;
  }
}

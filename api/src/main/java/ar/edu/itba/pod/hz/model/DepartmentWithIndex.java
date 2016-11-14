package ar.edu.itba.pod.hz.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class DepartmentWithIndex  implements DataSerializable, Comparable<DepartmentWithIndex> {
    String department;
    Double index;

    public DepartmentWithIndex(String department, Double index) {
        this.department = department;
        this.index = index;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(department);
        out.writeDouble(index);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.department = in.readUTF();
        this.index = in.readDouble();
    }

    @Override
    public int compareTo(DepartmentWithIndex o) {
        return this.index.compareTo(o.index);
    }

    public String getDepartment() {
        return department;
    }

    public Double getIndex() {
        return index;
    }

}

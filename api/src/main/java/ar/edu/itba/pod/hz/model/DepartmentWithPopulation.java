package ar.edu.itba.pod.hz.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class DepartmentWithPopulation implements DataSerializable, Comparable<DepartmentWithPopulation> {
    String department;
    Long population;

    public DepartmentWithPopulation(String department, Long population) {
        this.department = department;
        this.population = population;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(department);
        out.writeLong(population);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.department = in.readUTF();
        this.population = in.readLong();
    }

    public int compareTo(DepartmentWithPopulation o) {
        return this.population.compareTo(o.population);
    }

    public String getDepartment() {
        return department;
    }

    public Long getPopulation() {
        return population;
    }

}

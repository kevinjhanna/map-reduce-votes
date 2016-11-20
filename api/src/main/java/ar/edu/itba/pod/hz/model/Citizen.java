package ar.edu.itba.pod.hz.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Citizen implements DataSerializable {
    private int edad;
    private int alfabetismo;
    private int tipoVivienda;
    private String nombreDepto;
    private String nombreProv;
    private int hogarId;

    // mantener el orden que se hizo en el write!
    @Override
    public void readData(final ObjectDataInput in) throws IOException {
        edad = in.readInt();
        alfabetismo = in.readInt();
        tipoVivienda = in.readInt();
        nombreDepto = in.readUTF();
        nombreProv = in.readUTF();
        hogarId = in.readInt();
    }

    // mantener el orden que se hizo en el read!
    @Override
    public void writeData(final ObjectDataOutput out) throws IOException {
        out.writeInt(edad);
        out.writeInt(alfabetismo);
        out.writeInt(tipoVivienda);
        out.writeUTF(nombreDepto);
        out.writeUTF(nombreProv);
        out.writeInt(hogarId);
    }

    @Override
    public String toString() {
        return String.format("edad %d, alfabetismo %d, tipoVivienda %s, nombreDepto %s, nombreProv %s, hogarId %s",
                edad, alfabetismo, tipoVivienda, nombreDepto, nombreProv, hogarId);
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setAlfabetismo(int alfabetismo) {
        this.alfabetismo = alfabetismo;
    }

    public void setTipovivienda(int tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public void setNombredepto(String nombreDepto) {
        this.nombreDepto = nombreDepto;
    }

    public void setNombreprov(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    public void setHogarId(int hogarId) {
        this.hogarId = hogarId;
    }

    public int getEdad() {
        return edad;
    }

    public int getAlfabetismo() {
        return alfabetismo;
    }

    public int getTipovivienda() {
        return tipoVivienda;
    }

    public String getNombredepto() {
        return nombreDepto;
    }

    public String getNombreprov() {
        return nombreProv;
    }

    public int getHogarid() {
        return hogarId;
    }
}

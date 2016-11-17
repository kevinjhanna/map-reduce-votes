package ar.edu.itba.pod.hz.model;

/**
 * Created by FranDepascuali on 11/13/16.
 */
public enum TipoVivienda {

  Desconocida(0), Casa(1), Rancho(2), Casilla(3), Departamento(4), PiezaEnInquilinato(5), PiezaEnHotel(6), LocalNoHabitable(7), Calle(8);

  private int code;

  private TipoVivienda(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static TipoVivienda from(int code) {
    switch (code) {
      case 0: return Desconocida;
      case 1: return Casa;
      case 2: return Rancho;
      case 3: return Casilla;
      case 4: return Departamento;
      case 5: return PiezaEnInquilinato;
      case 6: return PiezaEnHotel;
      case 7: return LocalNoHabitable;
      case 8: return Calle;
      default: return Desconocida;
    }
  }
}

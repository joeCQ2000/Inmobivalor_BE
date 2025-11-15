package pe.edu.upc.inmobivalor_be.dtos;

import pe.edu.upc.inmobivalor_be.entities.Entidad_financiera;
import pe.edu.upc.inmobivalor_be.entities.Tasa_interes;

public class Entidad_tasaDTO {

    private int id_entidad_tasa;
    private Tasa_interes tasa;
    private Entidad_financiera entidad;

    public int getId_entidad_tasa() { return id_entidad_tasa; }
    public void setId_entidad_tasa(int id_entidad_tasa) { this.id_entidad_tasa = id_entidad_tasa; }

    public Tasa_interes getTasa() { return tasa; }
    public void setTasa(Tasa_interes tasa) { this.tasa = tasa; }

    public Entidad_financiera getEntidad() { return entidad; }
    public void setEntidad(Entidad_financiera entidad) { this.entidad = entidad; }
}

package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "credito_prestamo")
public class CreditoPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_credito;

    private int monto;

    @Column(length = 50)
    private String plazo_meses;

    @Column(length = 50)
    private String tipo_gracia;

    private int monto_bono;
    private java.sql.Date fecha_inicio;
    private java.sql.Date fecha_fin;

    private boolean capitalizacion;
    private int meses_gracia;

    @OneToMany(mappedBy = "creditoPrestamo", cascade = CascadeType.ALL)
    private List<IndicadoresFinancieros> indicadores;

    public CreditoPrestamo() {

    }

    public CreditoPrestamo(int id_credito, int monto, String plazo_meses, String tipo_gracia, int monto_bono, Date fecha_inicio, Date fecha_fin, boolean capitalizacion, int meses_gracia, List<IndicadoresFinancieros> indicadores) {
        this.id_credito = id_credito;
        this.monto = monto;
        this.plazo_meses = plazo_meses;
        this.tipo_gracia = tipo_gracia;
        this.monto_bono = monto_bono;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.capitalizacion = capitalizacion;
        this.meses_gracia = meses_gracia;
        this.indicadores = indicadores;
    }

    public int getId_credito() {
        return id_credito;
    }

    public int getMonto() {
        return monto;
    }

    public String getPlazo_meses() {
        return plazo_meses;
    }

    public String getTipo_gracia() {
        return tipo_gracia;
    }

    public int getMonto_bono() {
        return monto_bono;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public boolean isCapitalizacion() {
        return capitalizacion;
    }

    public int getMeses_gracia() {
        return meses_gracia;
    }

    public List<IndicadoresFinancieros> getIndicadores() {
        return indicadores;
    }

    public void setId_credito(int id_credito) {
        this.id_credito = id_credito;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void setPlazo_meses(String plazo_meses) {
        this.plazo_meses = plazo_meses;
    }

    public void setTipo_gracia(String tipo_gracia) {
        this.tipo_gracia = tipo_gracia;
    }

    public void setMonto_bono(int monto_bono) {
        this.monto_bono = monto_bono;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setCapitalizacion(boolean capitalizacion) {
        this.capitalizacion = capitalizacion;
    }

    public void setMeses_gracia(int meses_gracia) {
        this.meses_gracia = meses_gracia;
    }

    public void setIndicadores(List<IndicadoresFinancieros> indicadores) {
        this.indicadores = indicadores;
    }
}

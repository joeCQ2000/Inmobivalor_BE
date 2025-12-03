package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "indicadores_financieros")
public class IndicadoresFinancieros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_indicador;

    private double van;
    private double tir;

    @Column(name = "fecha_calculo", nullable = false)
    private LocalDate fecha_calculo;

    private double tcea;
    private double trea;

    @ManyToOne
    @JoinColumn(name = "id_credito")
    private CreditoPrestamo id_credito;

    public IndicadoresFinancieros() {

    }

    public IndicadoresFinancieros(int id_indicador, double van, double tir, LocalDate fecha_calculo, double tcea, double trea, CreditoPrestamo id_credito) {
        this.id_indicador = id_indicador;
        this.van = van;
        this.tir = tir;
        this.fecha_calculo = fecha_calculo;
        this.tcea = tcea;
        this.trea = trea;
        this.id_credito = id_credito;
    }

    public int getId_indicador() {
        return id_indicador;
    }

    public void setId_indicador(int id_indicador) {
        this.id_indicador = id_indicador;
    }

    public double getVan() {
        return van;
    }

    public void setVan(double van) {
        this.van = van;
    }

    public double getTir() {
        return tir;
    }

    public void setTir(double tir) {
        this.tir = tir;
    }

    public LocalDate getFecha_calculo() {
        return fecha_calculo;
    }

    public void setFecha_calculo(LocalDate fecha_calculo) {
        this.fecha_calculo = fecha_calculo;
    }

    public double getTcea() {
        return tcea;
    }

    public void setTcea(double tcea) {
        this.tcea = tcea;
    }

    public double getTrea() {
        return trea;
    }

    public void setTrea(double trea) {
        this.trea = trea;
    }

    public CreditoPrestamo getId_credito() {
        return id_credito;
    }

    public void setId_credito(CreditoPrestamo id_credito) {
        this.id_credito = id_credito;
    }
}

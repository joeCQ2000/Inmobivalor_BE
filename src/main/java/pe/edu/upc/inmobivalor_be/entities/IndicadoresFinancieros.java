package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

// Prueba
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

    private int tcea;
    private int trea;


    @ManyToOne
    @JoinColumn(name = "id_credito", nullable = false)
    private CreditoPrestamo creditoPrestamo;

    public IndicadoresFinancieros() {
    }

    public IndicadoresFinancieros(int id_indicador, double van, double tir, LocalDate fecha_calculo, int tcea, int trea, CreditoPrestamo creditoPrestamo) {
        this.id_indicador = id_indicador;
        this.van = van;
        this.tir = tir;
        this.fecha_calculo = fecha_calculo;
        this.tcea = tcea;
        this.trea = trea;
        this.creditoPrestamo = creditoPrestamo;
    }

    public int getId_indicador() {
        return id_indicador;
    }

    public double getVan() {
        return van;
    }

    public double getTir() {
        return tir;
    }

    public LocalDate getFecha_calculo() {
        return fecha_calculo;
    }

    public int getTcea() {
        return tcea;
    }

    public int getTrea() {
        return trea;
    }

    public CreditoPrestamo getCreditoPrestamo() {
        return creditoPrestamo;
    }

    public void setId_indicador(int id_indicador) {
        this.id_indicador = id_indicador;
    }

    public void setVan(double van) {
        this.van = van;
    }

    public void setTir(double tir) {
        this.tir = tir;
    }

    public void setFecha_calculo(LocalDate fecha_calculo) {
        this.fecha_calculo = fecha_calculo;
    }

    public void setTcea(int tcea) {
        this.tcea = tcea;
    }

    public void setTrea(int trea) {
        this.trea = trea;
    }

    public void setCreditoPrestamo(CreditoPrestamo creditoPrestamo) {
        this.creditoPrestamo = creditoPrestamo;
    }
}

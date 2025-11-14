package pe.edu.upc.inmobivalor_be.dtos;

import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;

import java.time.LocalDate;

public class IndicadoresFinancierosDTO {
    private int id_indicador;
    private double van;
    private double tir;
    private LocalDate fecha_calculo;
    private int tcea;
    private int trea;
    private CreditoPrestamo id_credito;

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

    public int getTcea() {
        return tcea;
    }

    public void setTcea(int tcea) {
        this.tcea = tcea;
    }

    public int getTrea() {
        return trea;
    }

    public void setTrea(int trea) {
        this.trea = trea;
    }

    public CreditoPrestamo getId_credito() {
        return id_credito;
    }

    public void setId_credito(CreditoPrestamo id_credito) {
        this.id_credito = id_credito;
    }
}

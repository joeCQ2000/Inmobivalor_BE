package pe.edu.upc.inmobivalor_be.dtos;

import pe.edu.upc.inmobivalor_be.entities.CreditoPrestamo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CuotaDTO {

    private int id_cuota;
    private short version_plan;
    private int nro_periodo;
    private LocalDate fecha_venc;
    private BigDecimal saldo_inicial;
    private BigDecimal cuota;
    private BigDecimal interes;
    private BigDecimal amortizacion;
    private BigDecimal saldo_final;
    private LocalDate fecha_pago;
    private BigDecimal capital_pagado;
    private BigDecimal interes_pagado;
    private BigDecimal mora;
    private String estado_cuota;
    private CreditoPrestamo credito;

    public int getId_cuota() {
        return id_cuota;
    }

    public void setId_cuota(int id_cuota) {
        this.id_cuota = id_cuota;
    }

    public CreditoPrestamo getCredito() {
        return credito;
    }

    public void setCredito(CreditoPrestamo credito) {
        this.credito = credito;
    }

    public short getVersion_plan() {
        return version_plan;
    }

    public void setVersion_plan(short version_plan) {
        this.version_plan = version_plan;
    }

    public int getNro_periodo() {
        return nro_periodo;
    }

    public void setNro_periodo(int nro_periodo) {
        this.nro_periodo = nro_periodo;
    }

    public LocalDate getFecha_venc() {
        return fecha_venc;
    }

    public void setFecha_venc(LocalDate fecha_venc) {
        this.fecha_venc = fecha_venc;
    }

    public BigDecimal getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(BigDecimal saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getAmortizacion() {
        return amortizacion;
    }

    public void setAmortizacion(BigDecimal amortizacion) {
        this.amortizacion = amortizacion;
    }

    public BigDecimal getSaldo_final() {
        return saldo_final;
    }

    public void setSaldo_final(BigDecimal saldo_final) {
        this.saldo_final = saldo_final;
    }

    public LocalDate getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public BigDecimal getCapital_pagado() {
        return capital_pagado;
    }

    public void setCapital_pagado(BigDecimal capital_pagado) {
        this.capital_pagado = capital_pagado;
    }

    public BigDecimal getInteres_pagado() {
        return interes_pagado;
    }

    public void setInteres_pagado(BigDecimal interes_pagado) {
        this.interes_pagado = interes_pagado;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public String getEstado_cuota() {
        return estado_cuota;
    }

    public void setEstado_cuota(String estado_cuota) {
        this.estado_cuota = estado_cuota;
    }
}

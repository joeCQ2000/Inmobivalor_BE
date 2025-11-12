package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cuota")
public class Cuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cuota;

    @Column(name = "version_plan", nullable = false)
    private short version_plan;

    @Column(name = "nro_periodo", nullable = false)
    private int nro_periodo;

    @Column(name = "fecha_venc", nullable = false)
    private LocalDate fecha_venc;

    @Column(name = "saldo_inicial", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo_inicial;

    @Column(name = "cuota", nullable = false, precision = 12, scale = 2)
    private BigDecimal cuota;

    @Column(name = "interes", nullable = false, precision = 12, scale = 2)
    private BigDecimal interes;

    @Column(name = "amortizacion", nullable = false, precision = 12, scale = 2)
    private BigDecimal amortizacion;

    @Column(name = "saldo_final", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo_final;

    @Column(name = "fecha_pago")
    private LocalDate fecha_pago;

    @Column(name = "capital_pagado", precision = 12, scale = 2)
    private BigDecimal capital_pagado = BigDecimal.ZERO;

    @Column(name = "interes_pagado", precision = 12, scale = 2)
    private BigDecimal interes_pagado = BigDecimal.ZERO;

    @Column(name = "mora", precision = 12, scale = 2)
    private BigDecimal mora = BigDecimal.ZERO;

    @Column(name = "estado_cuota", length = 12)
    private String estado_cuota = "PENDIENTE";

    @ManyToOne
    @JoinColumn(name = "id_credito", nullable = false)
    private CreditoPrestamo credito;

    // Constructor vacío obligatorio para JPA
    public Cuota() {}

    // Constructor completo (útil al crear manualmente)
    public Cuota(CreditoPrestamo credito, short version_plan, int nro_periodo,
                 LocalDate fecha_venc, BigDecimal saldo_inicial, BigDecimal cuota,
                 BigDecimal interes, BigDecimal amortizacion, BigDecimal saldo_final,
                 LocalDate fecha_pago, BigDecimal capital_pagado, BigDecimal interes_pagado,
                 BigDecimal mora, String estado_cuota) {
        this.credito = credito;
        this.version_plan = version_plan;
        this.nro_periodo = nro_periodo;
        this.fecha_venc = fecha_venc;
        this.saldo_inicial = saldo_inicial;
        this.cuota = cuota;
        this.interes = interes;
        this.amortizacion = amortizacion;
        this.saldo_final = saldo_final;
        this.fecha_pago = fecha_pago;
        this.capital_pagado = capital_pagado;
        this.interes_pagado = interes_pagado;
        this.mora = mora;
        this.estado_cuota = estado_cuota;
    }

    // Getters y Setters
    public int getId_cuota() { return id_cuota; }
    public void setId_cuota(int id_cuota) { this.id_cuota = id_cuota; }

    public CreditoPrestamo getCredito() { return credito; }
    public void setCredito(CreditoPrestamo credito) { this.credito = credito; }

    public short getVersion_plan() { return version_plan; }
    public void setVersion_plan(short version_plan) { this.version_plan = version_plan; }

    public int getNro_periodo() { return nro_periodo; }
    public void setNro_periodo(int nro_periodo) { this.nro_periodo = nro_periodo; }

    public LocalDate getFecha_venc() { return fecha_venc; }
    public void setFecha_venc(LocalDate fecha_venc) { this.fecha_venc = fecha_venc; }

    public BigDecimal getSaldo_inicial() { return saldo_inicial; }
    public void setSaldo_inicial(BigDecimal saldo_inicial) { this.saldo_inicial = saldo_inicial; }

    public BigDecimal getCuota() { return cuota; }
    public void setCuota(BigDecimal cuota) { this.cuota = cuota; }

    public BigDecimal getInteres() { return interes; }
    public void setInteres(BigDecimal interes) { this.interes = interes; }

    public BigDecimal getAmortizacion() { return amortizacion; }
    public void setAmortizacion(BigDecimal amortizacion) { this.amortizacion = amortizacion; }

    public BigDecimal getSaldo_final() { return saldo_final; }
    public void setSaldo_final(BigDecimal saldo_final) { this.saldo_final = saldo_final; }

    public LocalDate getFecha_pago() { return fecha_pago; }
    public void setFecha_pago(LocalDate fecha_pago) { this.fecha_pago = fecha_pago; }

    public BigDecimal getCapital_pagado() { return capital_pagado; }
    public void setCapital_pagado(BigDecimal capital_pagado) { this.capital_pagado = capital_pagado; }

    public BigDecimal getInteres_pagado() { return interes_pagado; }
    public void setInteres_pagado(BigDecimal interes_pagado) { this.interes_pagado = interes_pagado; }

    public BigDecimal getMora() { return mora; }
    public void setMora(BigDecimal mora) { this.mora = mora; }

    public String getEstado_cuota() { return estado_cuota; }
    public void setEstado_cuota(String estado_cuota) { this.estado_cuota = estado_cuota; }
}

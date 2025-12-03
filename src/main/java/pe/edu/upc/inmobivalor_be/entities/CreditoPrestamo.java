package pe.edu.upc.inmobivalor_be.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "credito_prestamo")
public class CreditoPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_credito;

    @Column(name = "plazo_meses", length = 50)
    private String plazo_meses;

    @Column(name = "tipo_gracia", nullable = true, length = 50)
    private String tipo_gracia;

    private int monto_bono;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fecha_inicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fecha_fin;

    @Column(name = "capitalizacion", length = 20, nullable = false)
    private String capitalizacion;

    private boolean estado;

    private int meses_gracia;

    @ManyToOne
    @JoinColumn(name = "id_entidad")
    private Entidad_financiera id_entidad;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente id_cliente;

    @ManyToOne
    @JoinColumn(name = "id_inmobiliaria")
    private Inmobiliaria id_inmobiliaria;

    @ManyToOne
    @JoinColumn(name = "id_moneda")
    private Moneda id_moneda;

    public CreditoPrestamo() {

    }

    public CreditoPrestamo(int id_credito, String plazo_meses, String tipo_gracia, int monto_bono, LocalDate fecha_inicio, LocalDate fecha_fin, String capitalizacion, boolean estado, int meses_gracia, Entidad_financiera id_entidad, Cliente id_cliente, Inmobiliaria id_inmobiliaria, Moneda id_moneda) {
        this.id_credito = id_credito;
        this.plazo_meses = plazo_meses;
        this.tipo_gracia = tipo_gracia;
        this.monto_bono = monto_bono;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.capitalizacion = capitalizacion;
        this.estado = estado;
        this.meses_gracia = meses_gracia;
        this.id_entidad = id_entidad;
        this.id_cliente = id_cliente;
        this.id_inmobiliaria = id_inmobiliaria;
        this.id_moneda = id_moneda;
    }

    public int getId_credito() {
        return id_credito;
    }

    public void setId_credito(int id_credito) {
        this.id_credito = id_credito;
    }

    public String getPlazo_meses() {
        return plazo_meses;
    }

    public void setPlazo_meses(String plazo_meses) {
        this.plazo_meses = plazo_meses;
    }

    public String getTipo_gracia() {
        return tipo_gracia;
    }

    public void setTipo_gracia(String tipo_gracia) {
        this.tipo_gracia = tipo_gracia;
    }

    public int getMonto_bono() {
        return monto_bono;
    }

    public void setMonto_bono(int monto_bono) {
        this.monto_bono = monto_bono;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getCapitalizacion() {
        return capitalizacion;
    }

    public void setCapitalizacion(String capitalizacion) {
        this.capitalizacion = capitalizacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getMeses_gracia() {
        return meses_gracia;
    }

    public void setMeses_gracia(int meses_gracia) {
        this.meses_gracia = meses_gracia;
    }

    public Entidad_financiera getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(Entidad_financiera id_entidad) {
        this.id_entidad = id_entidad;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Inmobiliaria getId_inmobiliaria() {
        return id_inmobiliaria;
    }

    public void setId_inmobiliaria(Inmobiliaria id_inmobiliaria) {
        this.id_inmobiliaria = id_inmobiliaria;
    }

    public Moneda getId_moneda() {
        return id_moneda;
    }

    public void setId_moneda(Moneda id_moneda) {
        this.id_moneda = id_moneda;
    }
}

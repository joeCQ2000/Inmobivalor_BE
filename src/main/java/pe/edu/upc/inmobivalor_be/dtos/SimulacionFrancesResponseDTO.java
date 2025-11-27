package pe.edu.upc.inmobivalor_be.dtos;

import java.util.List;

public class SimulacionFrancesResponseDTO {

    private DatosCronogramaDTO datos;
    private List<CronogramaDTO> cronograma;
    private IndicadoresFinancierosDTO indicadores;

    public DatosCronogramaDTO getDatos() {
        return datos;
    }
    public void setDatos(DatosCronogramaDTO datos) {
        this.datos = datos;
    }

    public List<CronogramaDTO> getCronograma() {
        return cronograma;
    }
    public void setCronograma(List<CronogramaDTO> cronograma) {
        this.cronograma = cronograma;
    }

    public IndicadoresFinancierosDTO getIndicadores() {
        return indicadores;
    }
    public void setIndicadores(IndicadoresFinancierosDTO indicadores) {
        this.indicadores = indicadores;
    }
}

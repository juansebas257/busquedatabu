public class Ruta {
    String codigoPieza;
    String maquina;
    int orden;
    int secuenciaMaquina;
    float minutos;
    int horaLlegada;
    int horaInicio;
    int horaFin;
    int tiempoInventario;
    int tiempoEsperaMaquina;

    public Ruta(String codigoPieza, int orden, int secuenciaMaquina, float minutos, int horaLlegada, int horaInicio, int horaFin, int tiempoInventario, int tiempoEsperaMaquina) {
        this.codigoPieza = codigoPieza;
        this.orden = orden;
        this.secuenciaMaquina = secuenciaMaquina;
        this.minutos = minutos;
        this.horaLlegada = horaLlegada;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tiempoInventario = tiempoInventario;
        this.tiempoEsperaMaquina = tiempoEsperaMaquina;
    }

    Ruta() {
        
    }
    
    
    
}

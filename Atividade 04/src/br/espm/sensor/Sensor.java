package br.espm.sensor;

import java.time.LocalDate;

public class Sensor {
    private int id;
    private LocalDate dataColeta;
    private double valorAQI;

    public Sensor(int id, LocalDate dataColeta, double valorAQI) {
        this.id = id;
        this.dataColeta = dataColeta;
        this.valorAQI = valorAQI;
    }

    public int getId() { return id; }
    public LocalDate getDataColeta() { return dataColeta; }
    public double getValorAQI() { return valorAQI; }

    @Override
    public String toString() {
        return "Sensor ID: " + id +
               " | Data: " + dataColeta +
               " | AQI: " + valorAQI;
    }
}
package br.espm.zona;

import br.espm.sensor.Sensor;
import br.espm.sensor.Emergencia;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ZonaUrbana extends Zona implements Emergencia {
    private List<Sensor> sensores;

    public ZonaUrbana(String nome) {
        super(nome);
        sensores = new ArrayList<>();
    }

    public void adicionarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    public double calcularTotal() {
        return sensores.stream().mapToDouble(Sensor::getValorAQI).sum();
    }

    public double calcularMedia() {
        return sensores.isEmpty() ? 0 : calcularTotal() / sensores.size();
    }

    @Override
    public String classificarNivelEmergencia(double media) {
        if (media <= 50) return "Sem risco";
        if (media <= 100) return "Monitoramento intensificado";
        if (media <= 150) return "Alerta para grupos sensíveis";
        if (media <= 200) return "Alerta Amarelo";
        if (media <= 300) return "Alerta Laranja";
        return "Alerta Vermelho (emergência total)";
    }

    @Override
    public String relatorio() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double total = calcularTotal();
        double media = calcularMedia();
        String nivel = classificarNivelEmergencia(media);

        StringBuilder sb = new StringBuilder();
        sb.append("Zona: ").append(getNome()).append("\n");
        sb.append("Total semanal: ").append(df.format(total)).append("\n");
        sb.append("Média semanal: ").append(df.format(media)).append("\n");
        sb.append("Nível de emergência: ").append(nivel);
        if (nivel.startsWith("Alerta Vermelho")) {
            sb.append("\n>>> Ação imediata recomendada: evacuação ou restrição de atividades externas.");
        }
        return sb.toString();
    }
}
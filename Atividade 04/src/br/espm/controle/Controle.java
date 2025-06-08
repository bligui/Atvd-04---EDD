package br.espm.controle;

import br.espm.zona.Zona;
import br.espm.zona.ZonaUrbana;
import br.espm.zona.ZonaRural;
import br.espm.sensor.Sensor;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.TreeSet;

public class Controle {
    private TreeSet<Zona> zonas = new TreeSet<>();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void menu() {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(
                    "SISTEMA DE MONITORAMENTO DE QUALIDADE DO AR\n" +
                    "1. Registrar Zona\n" +
                    "2. Adicionar sensor (só urbana)\n" +
                    "3. Imprimir relatório\n" +
                    "4. Finalizar");
                if (input == null) return;
                int opc = Integer.parseInt(input);
                switch (opc) {
                    case 1: registrarZona(); break;
                    case 2: adicionarSensor(); break;
                    case 3: imprimirRelatorio(); break;
                    case 4: return;
                    default: JOptionPane.showMessageDialog(null, "Opção inválida");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite um número válido.");
            }
        }
    }

    private void registrarZona() {
        try {
            String tipo = JOptionPane.showInputDialog("Tipo (Urbana/Rural):");
            if (tipo == null) return;
            String nome = JOptionPane.showInputDialog("Nome da Zona:");
            if (nome == null || nome.isBlank()) {
                JOptionPane.showMessageDialog(null, "Nome não pode ser vazio.");
                return;
            }
            Zona z;
            if ("Urbana".equalsIgnoreCase(tipo)) z = new ZonaUrbana(nome);
            else if ("Rural".equalsIgnoreCase(tipo)) z = new ZonaRural(nome);
            else { JOptionPane.showMessageDialog(null, "Tipo inválido."); return; }
            if (!zonas.add(z)) {
                JOptionPane.showMessageDialog(null, "Zona já cadastrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar zona: " + e.getMessage());
        }
    }

    private void adicionarSensor() {
        try {
            String nome = JOptionPane.showInputDialog("Nome da Zona Urbana:");
            if (nome == null) return;
            Zona alvo = buscarZona(nome);
            if (!(alvo instanceof ZonaUrbana)) {
                JOptionPane.showMessageDialog(null, "Zona não encontrada ou não é urbana.");
                return;
            }
            String sid = JOptionPane.showInputDialog("ID do Sensor:");
            int id = Integer.parseInt(sid);
            String datas = JOptionPane.showInputDialog("Data (dd/MM/yyyy):");
            LocalDate data = LocalDate.parse(datas, fmt);
            String va = JOptionPane.showInputDialog("Valor AQI:");
            double valor = Double.parseDouble(va);
            ((ZonaUrbana) alvo).adicionarSensor(new Sensor(id, data, valor));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID ou valor inválido.");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar sensor: " + e.getMessage());
        }
    }

    private void imprimirRelatorio() {
        try {
            String nome = JOptionPane.showInputDialog("Nome da Zona:");
            if (nome == null) return;
            Zona z = buscarZona(nome);
            if (z == null) {
                JOptionPane.showMessageDialog(null, "Zona não encontrada.");
                return;
            }
            JOptionPane.showMessageDialog(null, z.relatorio());
            if (z instanceof ZonaUrbana) {
                double m = ((ZonaUrbana) z).calcularMedia();
                if (m > 300) {
                    JOptionPane.showMessageDialog(null,
                        ">>> ALERTA EXTREMO: Média crítica ultrapassada!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private Zona buscarZona(String nome) {
        for (Zona z : zonas) {
            if (z.getNome().equalsIgnoreCase(nome)) return z;
        }
        return null;
    }
}
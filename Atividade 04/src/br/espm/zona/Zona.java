package br.espm.zona;

public abstract class Zona implements Comparable<Zona> {
    private String nome;

    public Zona(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }

    public abstract String relatorio();

    @Override
    public int compareTo(Zona outra) {
        return this.nome.compareToIgnoreCase(outra.nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Zona) {
            return nome.equalsIgnoreCase(((Zona) obj).nome);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
}
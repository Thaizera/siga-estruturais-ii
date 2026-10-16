package siga;

import java.util.Objects;

public class Disciplina implements ItemCurricular {
    private final String nome;
    private final int cargaHoraria;

    public Disciplina(String nome, int cargaHoraria) {
        this.nome = Objects.requireNonNull(nome);
        if (cargaHoraria < 0) {
            throw new IllegalArgumentException("Carga horaria negativa");
        }
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    @Override
    public void imprimir(int nivel) {
        System.out.println("  ".repeat(nivel) + nome + " (" + cargaHoraria + "h)");
    }
}

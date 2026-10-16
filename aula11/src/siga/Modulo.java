package siga;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Composto: os filhos devem formar uma árvore curricular sem ciclos. */
public class Modulo implements ItemCurricular {
    private final String nome;
    private final List<ItemCurricular> filhos = new ArrayList<>();

    public Modulo(String nome) {
        this.nome = Objects.requireNonNull(nome);
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void adicionar(ItemCurricular filho) {
        Objects.requireNonNull(filho);
        if (filho == this) {
            throw new IllegalArgumentException("Modulo nao pode conter a si mesmo");
        }
        filhos.add(filho);
    }

    public void remover(ItemCurricular filho) {
        filhos.remove(filho);
    }

    public List<ItemCurricular> getFilhos() {
        return List.copyOf(filhos);
    }

    @Override
    public int getCargaHoraria() {
        int total = 0;
        for (ItemCurricular filho : filhos) {
            total += filho.getCargaHoraria();
        }
        return total;
    }

    @Override
    public void imprimir(int nivel) {
        System.out.println("  ".repeat(nivel) + "[" + nome + "]");
        for (ItemCurricular filho : filhos) {
            filho.imprimir(nivel + 1);
        }
    }
}

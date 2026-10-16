package siga;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class TestesSiga {
    private static void verificar(boolean condicao, String descricao) {
        if (!condicao) {
            throw new AssertionError(descricao);
        }
    }

    public static void main(String[] args) {
        Relatorio base = new RelatorioDeAlunos();
        String original = base.gerar();
        verificar(new ComNumeracao(base).gerar().equals(
                "1: Maria Silva\n2: Joao Souza\n3: Ana Pereira"), "Numeracao incorreta");
        Relatorio primeira = new ComCabecalho(new ComNumeracao(base));
        Relatorio segunda = new ComNumeracao(new ComCabecalho(base));
        verificar(!primeira.gerar().startsWith("1: "), "Cabecalho numerado na primeira pilha");
        verificar(primeira.gerar().contains("1: Maria Silva"), "Alunos sem numeracao");
        verificar(segunda.gerar().startsWith("1: "), "Cabecalho sem numeracao na segunda pilha");
        verificar(segunda.gerar().contains("4: Maria Silva"), "Ordem dos decoradores incorreta");
        Set<String> combinacoes = new HashSet<>();
        for (int mascara = 0; mascara < 8; mascara++) {
            Relatorio relatorio = base;
            if ((mascara & 1) != 0) { relatorio = new ComNumeracao(relatorio); }
            if ((mascara & 2) != 0) { relatorio = new ComCabecalho(relatorio); }
            if ((mascara & 4) != 0) { relatorio = new ComMarcaDagua(relatorio); }
            String texto = relatorio.gerar();
            verificar(texto.contains("Maria Silva") && texto.contains("Ana Pereira"),
                    "Conteudo perdido em uma combinacao");
            verificar(texto.contains("RASCUNHO") == ((mascara & 4) != 0), "Marca incorreta");
            combinacoes.add(texto);
        }
        verificar(combinacoes.size() == 8, "Combinacoes nao independentes");
        verificar(base.gerar().equals(original), "Decoracao alterou a base compartilhada");
        verificar(new ComNumeracao(() -> "").gerar().isEmpty(), "Relatorio vazio incorreto");
        verificar(new ComNumeracao(() -> "A\r\nB").gerar().equals("1: A\n2: B"),
                "Quebras de linha Windows incorretas");

        Modulo curso = new Modulo("Curso");
        Modulo primeiro = new Modulo("Primeiro");
        primeiro.adicionar(new Disciplina("Algoritmos", 80));
        primeiro.adicionar(new Disciplina("Banco", 60));
        Modulo segundo = new Modulo("Segundo");
        segundo.adicionar(new Disciplina("TP2", 80));
        segundo.adicionar(new Disciplina("Web", 80));
        curso.adicionar(primeiro);
        curso.adicionar(segundo);
        curso.adicionar(new Disciplina("Projeto", 40));
        verificar(curso.getCargaHoraria() == 340, "Carga original diferente de 340h");
        Modulo trilha = new Modulo("Trilha");
        trilha.adicionar(new Disciplina("Dados", 40));
        segundo.adicionar(trilha);
        verificar(curso.getCargaHoraria() == 380, "Carga com terceiro nivel incorreta");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(saida, true, StandardCharsets.UTF_8));
            curso.imprimir(0);
        } finally {
            System.setOut(console);
        }
        verificar(saida.toString(StandardCharsets.UTF_8).contains("    [Trilha]"),
                "Recuo do terceiro nivel incorreto");
        verificar(saida.toString(StandardCharsets.UTF_8).contains("      Dados (40h)"),
                "Recuo da folha incorreto");
        segundo.remover(trilha);
        verificar(curso.getCargaHoraria() == 340, "Remocao nao atualizou total");
        verificar(new Modulo("Vazio").getCargaHoraria() == 0, "Modulo vazio incorreto");
        verificar(new Disciplina("Zero", 0).getCargaHoraria() == 0, "Folha vazia incorreta");
        try {
            curso.getFilhos().clear();
            throw new AssertionError("Colecao interna exposta");
        } catch (UnsupportedOperationException esperado) {
            verificar(curso.getCargaHoraria() == 340, "Colecao externa alterou o curso");
        }
        ItemCurricular extensao = new ItemCurricular() {
            public String getNome() { return "Estagio"; }
            public int getCargaHoraria() { return 20; }
            public void imprimir(int nivel) {
                System.out.println("  ".repeat(nivel) + getNome());
            }
        };
        curso.adicionar(extensao);
        verificar(curso.getCargaHoraria() == 360, "Novo componente nao aceito pelo composto");
        System.out.println("OK: oito combinacoes, ordem, base compartilhada, numeracao, "
                + "340h originais, 380h com trilha, recuos, remocao e extensao polimorfica.");
    }
}

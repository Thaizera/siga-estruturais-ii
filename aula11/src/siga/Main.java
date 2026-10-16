package siga;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 11: Decorator e Composite ===");
        Relatorio base = new RelatorioDeAlunos();
        Relatorio primeira = new ComMarcaDagua(new ComCabecalho(new ComNumeracao(base)));
        Relatorio segunda = new ComMarcaDagua(new ComNumeracao(new ComCabecalho(base)));
        System.out.println("\n1) Cabecalho externo a numeracao:");
        System.out.println(primeira.gerar());
        System.out.println("\n2) Numeracao externa ao cabecalho:");
        System.out.println(segunda.gerar());

        Modulo curso = new Modulo("DSM - Desenvolvimento de Software Multiplataforma");
        Modulo primeiro = new Modulo("1o semestre");
        primeiro.adicionar(new Disciplina("Algoritmos", 80));
        primeiro.adicionar(new Disciplina("Banco de Dados I", 60));
        Modulo segundo = new Modulo("2o semestre");
        segundo.adicionar(new Disciplina("Tecnicas de Programacao II", 80));
        segundo.adicionar(new Disciplina("Desenvolvimento Web II", 80));
        curso.adicionar(primeiro);
        curso.adicionar(segundo);
        curso.adicionar(new Disciplina("Projeto Integrador", 40));
        System.out.println("\n3) Grade original:");
        apresentar(curso);

        Modulo trilha = new Modulo("Trilha de especializacao em dados");
        trilha.adicionar(new Disciplina("Analise de Dados", 40));
        segundo.adicionar(trilha);
        System.out.println("\n4) Grade com modulo de terceiro nivel:");
        apresentar(curso);
    }

    /** O cliente usa o mesmo contrato antes e depois de incluir a trilha. */
    private static void apresentar(ItemCurricular item) {
        item.imprimir(0);
        System.out.println("Carga total: " + item.getCargaHoraria() + "h");
    }
}

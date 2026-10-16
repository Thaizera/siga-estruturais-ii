package siga;

public class ComCabecalho extends DecoradorRelatorio {
    private static final String CABECALHO =
            "FATEC DE PORTO FERREIRA\nSistema de Gestao Academica\n---\n";

    public ComCabecalho(Relatorio relatorio) {
        super(relatorio);
    }

    @Override
    public String gerar() {
        return CABECALHO + super.gerar();
    }
}

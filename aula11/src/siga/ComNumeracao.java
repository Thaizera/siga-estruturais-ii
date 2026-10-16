package siga;

public class ComNumeracao extends DecoradorRelatorio {
    public ComNumeracao(Relatorio relatorio) {
        super(relatorio);
    }

    @Override
    public String gerar() {
        String conteudo = super.gerar();
        if (conteudo.isEmpty()) {
            return conteudo;
        }
        String[] linhas = conteudo.split("\\R", -1);
        StringBuilder numerado = new StringBuilder();
        for (int indice = 0; indice < linhas.length; indice++) {
            if (indice > 0) {
                numerado.append("\n");
            }
            numerado.append(indice + 1).append(": ").append(linhas[indice]);
        }
        return numerado.toString();
    }
}

package siga;

import java.util.Objects;

public abstract class DecoradorRelatorio implements Relatorio {
    protected final Relatorio relatorio;

    protected DecoradorRelatorio(Relatorio relatorio) {
        this.relatorio = Objects.requireNonNull(relatorio);
    }

    @Override
    public String gerar() {
        return relatorio.gerar();
    }
}

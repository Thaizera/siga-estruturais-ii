# Aula 11 — Decorator e Composite

## Análise do código inicial

Existiam quatro subclasses concretas para variações de relatório, além da base.
Cada característica opcional pode estar presente ou ausente: com n opcionais,
há 2^n combinações. Três opcionais permitem 2^3 = 8 combinações, incluindo a
base sem opcionais; quatro permitem 2^4 = 16 combinações (15 variações e a base).
O quarto opcional hipotético é um rodapé com data; ele não precisa ser
implementado nesta atividade. Com Decorator, bastaria acrescentar um decorador.

O cabeçalho e a numeração estavam duplicados nas classes combinatórias.
A grade também repetia a descoberta de tipos e a travessia em duas operações.

## Decorator

`Relatorio` é o contrato, `RelatorioDeAlunos` é a base e `DecoradorRelatorio`
guarda a referência ao relatório envolvido. `ComCabecalho`, `ComNumeracao` e
`ComMarcaDagua` acrescentam uma característica cada. As antigas classes
combinatórias foram substituídas por essas três classes concretas.

O Main compartilha a mesma instância base entre duas pilhas:

```java
new ComMarcaDagua(new ComCabecalho(new ComNumeracao(base)));
new ComMarcaDagua(new ComNumeracao(new ComCabecalho(base)));
```

Na primeira, só os alunos recebem números. Na segunda, o cabeçalho também
recebe números: Maria começa na linha 4. A marca fica externa em ambas.
A numeração é de linhas, conforme o comportamento do código inicial.

## Composite

`ItemCurricular` define `getNome`, `getCargaHoraria` e `imprimir`.
`Disciplina` é a folha e `Modulo` é o composto com `List<ItemCurricular>`.
Cada item calcula e imprime sua parte; `GradeCurricular` foi removida.

`adicionar` e `remover` ficam apenas em Modulo, pois disciplinas não têm filhos.
Isso mantém o contrato comum pequeno e evita operações sem sentido na folha.
O cliente que constrói a árvore conhece os módulos; o cliente que apresenta
a grade usa apenas ItemCurricular. Essa é a troca entre segurança de tipos
e uniformidade total do gerenciamento da estrutura.

A estrutura original soma 80 + 60 + 80 + 80 + 40 = **340h**.
O Main acrescenta uma trilha de 40h dentro do segundo semestre:
curso → semestre → trilha → disciplina. O total passa a **380h**.
O método `apresentar(ItemCurricular)` continua igual para ambas as estruturas.
Os módulos representam uma árvore sem ciclos; o chamador deve preservar essa
condição ao montar a hierarquia. A lista retornada por getFilhos é imutável.

## Compilar, executar e testar

Pré-requisito: JDK 17 ou superior. Em PowerShell, dentro de `aula11`:

```powershell
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 --release 17 -d bin src/siga/*.java
java -cp bin siga.Main
javac -encoding UTF-8 --release 17 -cp bin -d bin tests/siga/TestesSiga.java
java -cp bin siga.TestesSiga
```

Os testes cobrem as oito combinações, a mudança de ordem, a preservação da base,
relatório vazio, quebras de linha, os totais, recuos, remoção, lista protegida
e aceitação de uma nova implementação do contrato curricular.
As saídas reais estão em [evidencias/execucao.txt](evidencias/execucao.txt)
e [evidencias/testes.txt](evidencias/testes.txt).

package br.com.eagro.api;

/**
 * Ponto de entrada único e controlado para a execução dos testes e geração de relatórios.
 * ESTA É A CLASSE PRINCIPAL A SER EXECUTADA.
 * O RunCucumberTest.java NÃO deve ser usado, pois causa conflitos com a IDE.
 */
public class Executor {

    public static void main(String[] args) {
        System.out.println("\n>>> INICIANDO EXECUÇÃO CONTROLADA PELO EXECUTOR.JAVA <<<");

        String[] argv = {
                "--glue", "br.com.eagro.api",
                "--plugin", "json:target/cucumber-reports/cucumber.json",
                "src/test/resources/br/com/eagro/api"
        };

        byte exitStatus = io.cucumber.core.cli.Main.run(argv, Thread.currentThread().getContextClassLoader());

        System.out.println("\n>>> EXECUÇÃO DO CUCUMBER FINALIZADA COM STATUS: " + exitStatus + " <<<");

        if (exitStatus == 0) {
            ReportGenerator.generateReport();
        } else {
            System.out.println("\n>>> ERRO: Testes falharam. O relatório do dashboard não será gerado, mas o cucumber.json pode conter detalhes do erro. <<<");
            // Mesmo com erro, tentamos gerar o relatório para ver as falhas.
            ReportGenerator.generateReport();
            System.exit(exitStatus);
        }

        System.out.println("\n>>> PROCESSO FINALIZADO COM SUCESSO. <<<");
    }
}

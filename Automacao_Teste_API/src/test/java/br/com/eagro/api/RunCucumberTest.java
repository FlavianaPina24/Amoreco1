package br.com.eagro.api;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * !!! NÃO EXECUTE ESTA CLASSE DIRETAMENTE !!!
 *
 * A execução via JUnit (clicando no 'Play' desta classe) causa conflitos
 * com a configuração da IDE e impede a geração correta do arquivo cucumber.json,
 * resultando em relatórios de evidência vazios.
 *
 * Para executar os testes e gerar os relatórios, utilize a classe Executor.java.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("br/com/eagro/api")
public class RunCucumberTest {
    // Esta classe é mantida apenas para compatibilidade com ferramentas de CI
    // e para a estrutura do JUnit, mas não deve ser o ponto de entrada manual.
}

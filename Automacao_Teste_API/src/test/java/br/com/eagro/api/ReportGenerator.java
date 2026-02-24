package br.com.eagro.api;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportGenerator {

    public static void generateReport() {
        String projectPath = System.getProperty("user.dir");
        File reportOutputDirectory = new File(projectPath, "target/cucumber-reports");

        if (!reportOutputDirectory.exists()) {
            System.out.println("\n>>> A pasta de relatórios JSON não foi criada. O DASHBOARD NÃO SERÁ GERADO. <<<");
            return;
        }

        File jsonFile = new File(reportOutputDirectory, "cucumber.json");

        if (!jsonFile.exists() || jsonFile.length() == 0) {
            System.out.println("\n>>> ARQUIVO 'cucumber.json' NÃO ENCONTRADO OU VAZIO. O DASHBOARD NÃO SERÁ GERADO. <<<");
            return;
        }

        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add(jsonFile.getAbsolutePath());

        File evidenceBaseDir = new File(projectPath, "Evidencias");
        String buildNumber = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String projectName = "Projeto Testes de API";
        File uniqueEvidenceDir = new File(evidenceBaseDir, projectName + " " + buildNumber);

        Configuration configuration = new Configuration(uniqueEvidenceDir, projectName);
        configuration.setBuildNumber(buildNumber);
        configuration.addClassifications("Plataforma", System.getProperty("os.name"));
        configuration.addClassifications("Executor", System.getProperty("user.name"));

        // CORREÇÃO: Oculta todas as tags do relatório para uma visualização mais limpa.
        configuration.setTagsToExcludeFromChart("@.*");

        try {
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
            System.out.println("\n>>> Dashboard moderno gerado com sucesso em: " + uniqueEvidenceDir.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("\n>>> ERRO AO GERAR O DASHBOARD: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

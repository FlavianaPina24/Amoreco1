package br.com.eagro.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    // Bloco estático que é executado apenas uma vez, quando a classe é carregada.
    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("ALERTA: Não foi possível encontrar o arquivo config.properties. As configurações externas não serão carregadas.");
            } else {
                // Carrega o arquivo de propriedades
                properties.load(input);
            }
        } catch (IOException ex) {
            System.err.println("ERRO ao carregar o arquivo config.properties.");
            ex.printStackTrace();
        }
    }

    /**
     * Busca uma propriedade no arquivo config.properties.
     * @param key A chave da propriedade (ex: "base_url_tu").
     * @return O valor da propriedade como uma String.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

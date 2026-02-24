package br.com.eagro.api;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MongoConnection {

    // A string de conexão foi movida para o arquivo config.properties
    private static final String DATABASE_NAME = "automacao_api_db";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private static void connect() {
        if (mongoClient == null) {
            try {
                // CORREÇÃO: Lê a string de conexão do arquivo de configuração
                String connectionString = ConfigLoader.getProperty("mongodb.connection.string");
                if (connectionString == null || connectionString.trim().isEmpty()) {
                    throw new RuntimeException("A propriedade 'mongodb.connection.string' não foi encontrada ou está vazia no arquivo config.properties.");
                }

                System.out.println("\n[DIAGNÓSTICO MONGO] Tentando conectar com a string: " + connectionString.replaceAll(":[^@]+@", ":<password>@"));
                mongoClient = MongoClients.create(connectionString);
                database = mongoClient.getDatabase(DATABASE_NAME);
                System.out.println("[DIAGNÓSTICO MONGO] Conexão estabelecida e banco de dados '" + DATABASE_NAME + "' selecionado.");

                System.out.println("[DIAGNÓSTICO MONGO] Coleções visíveis neste banco de dados:");
                MongoIterable<String> collectionNames = database.listCollectionNames();
                for (String name : collectionNames) {
                    System.out.println("    - " + name);
                }
                System.out.println("---------------------------------------------------\n");

            } catch (Exception e) {
                System.err.println("ERRO CRÍTICO AO CONECTAR COM O MONGODB ATLAS: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Não foi possível conectar ao MongoDB Atlas.", e);
            }
        }
    }

    public static String getCenarioBody(String cenarioId, String collectionName) {
        connect();
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document doc = collection.find(eq("_id", cenarioId)).first();

            if (doc != null) {
                Object body = doc.get("body");
                if (body instanceof Document) {
                    return ((Document) body).toJson();
                } else if (body instanceof String) {
                    return (String) body;
                }
            } else {
                System.err.println("Cenário '" + cenarioId + "' NÃO encontrado na coleção '" + collectionName + "'. A busca retornou NULL.");
            }
        } catch (Exception e) {
            System.err.println("ERRO AO BUSCAR CENÁRIO '" + cenarioId + "' NA COLEÇÃO '" + collectionName + "': " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}

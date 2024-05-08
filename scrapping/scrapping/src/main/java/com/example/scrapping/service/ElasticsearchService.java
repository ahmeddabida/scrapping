package com.example.scrapping.service;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchService {

    private final RestClient restClient;

    @Autowired
    public ElasticsearchService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Map<String, Object> getById(String index, String id) throws IOException {
        String endpoint = "/" + index + "/_doc/" + id;
        Request request = new Request("GET", endpoint);
        Response response = restClient.performRequest(request);


        return convertResponseToMap(response);
    }

    private Map<String, Object> convertResponseToMap(Response response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        if (response.getEntity() != null) {
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            resultMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
        }
        return resultMap;
    }
    public List<Map<String, Object>> getAll(String index) throws IOException {
        String endpoint = "/" + index + "/_search";
        Request request = new Request("GET", endpoint);
        Response response = restClient.performRequest(request);

        // Convertir la réponse en une liste de Map<String, Object>
        return convertResponseToList(response);
    }

    private List<Map<String, Object>> convertResponseToList(Response response) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (response.getEntity() != null) {
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode hits = root.path("hits").path("hits");
            if (hits.isArray()) {
                for (JsonNode hit : hits) {
                    JsonNode source = hit.path("_source");
                    Map<String, Object> document = objectMapper.convertValue(source, new TypeReference<Map<String, Object>>() {});
                    resultList.add(document);
                }
            }
        }
        return resultList;
    }


    // Méthode pour convertir la réponse Elasticsearch en une liste de Map<String, Object>
    private List<Map<String, Object>> convertResponsetoList(Response response) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (response.getEntity() != null) {
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode hits = root.path("hits").path("hits");
            if (hits.isArray()) {
                for (JsonNode hit : hits) {
                    JsonNode source = hit.path("_source");
                    Map<String, Object> document = objectMapper.convertValue(source, new TypeReference<Map<String, Object>>() {
                    });
                    resultList.add(document);
                }
            }
        }
        return resultList;

    }
    public void searchProductByName(String productName) throws IOException, URISyntaxException {
        String endpoint = "/mytekt.products/_search";
        // Construire la requête JSON
        String query = "{\"size\":20,\"query\":{\"match_phrase_prefix\":{\"Product Name\":\"" + productName + "\"}}}";
        // Créer la requête
        Request request = new Request("GET", endpoint);
        request.addParameter("q", query);
        // Exécuter la requête
        restClient.performRequest(request);
    }
    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port}")
    private int elasticsearchPort;

    @Value("${elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    public List<Map<String, String>> searchProducts(String prefix) throws IOException {
        RestClientBuilder builder = RestClient.builder(new HttpHost(elasticsearchHost, elasticsearchPort))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(
                        new BasicCredentialsProvider() {{
                            setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchUsername, elasticsearchPassword));
                        }}));
        try (RestClient restClient = builder.build()) {
            Request request = new Request("GET", "/auth_db.mytek/_search");
            String query = "{\n" +
                    "  \"size\": 20,\n" +
                    "  \"query\": {\n" +
                    "    \"match_phrase\": {\n" +
                    "      \"Product Name\": \"" + prefix + "\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            request.setJsonEntity(query);

            Response response = restClient.performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            return parseProducts(responseBody);
        }
    }

    private List<Map<String, String>> parseProducts(String responseBody) throws IOException {
        List<Map<String, String>> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseNode = objectMapper.readTree(responseBody);
        JsonNode hitsNode = responseNode.path("hits").path("hits");
        for (JsonNode hit : hitsNode) {
            JsonNode sourceNode = hit.path("_source");
            String id = hit.path("_id").asText(); // Récupérer l'ID
            String productName = sourceNode.path("Product Name").asText();
            String image = sourceNode.path("Image").asText();
            String link = sourceNode.path("Link").asText();
            Map<String, String> product = new HashMap<>();
            product.put("id", id); // Ajouter l'ID à la liste de produits
            product.put("productName", productName);
            product.put("link", link);
            product.put("image",image);
            products.add(product);
        }
        return products;
    }
}
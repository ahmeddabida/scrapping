package com.example.scrapping.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.scrapping.service.ElasticsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/document/{id}")
    public ResponseEntity<Map<String, Object>> getDocumentById(@PathVariable String id) {
        try {
            Map<String, Object> document = elasticsearchService.getById("tunisianet_tn.products", id);
            return ResponseEntity.ok(document);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/documents")
    public ResponseEntity<List<Map<String, Object>>> getAllDocuments() {
        try {
            List<Map<String, Object>> documents = elasticsearchService.getAll("mytek_products");
            return ResponseEntity.ok(documents);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/search")
    public List<Map<String, String>> searchProducts(@RequestParam String prefix) {
        try {
            return elasticsearchService.searchProducts(prefix);
        } catch (IOException e) {
            // Gérer les erreurs d'Elasticsearch ici
            e.printStackTrace();
            return null; // Ou renvoyer une réponse d'erreur appropriée
        }
    }
    }




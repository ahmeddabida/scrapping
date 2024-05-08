package com.example.scrapping.controller;


import com.example.scrapping.model.MytekProduct;
import com.example.scrapping.service.ElasticsearchService;
import com.example.scrapping.service.MytekProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MytekProductController {

    @Autowired
    private MytekProductService mytekProductService;
    @GetMapping("/products/{productId}")
    public ResponseEntity<MytekProduct> getProductById(@PathVariable String productId) {
        MytekProduct    product = mytekProductService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

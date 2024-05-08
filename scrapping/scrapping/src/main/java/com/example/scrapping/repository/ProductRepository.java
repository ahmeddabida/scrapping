package com.example.scrapping.repository;


import com.example.scrapping.model.ProductC;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductC,String> {


}
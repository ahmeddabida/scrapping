package com.example.scrapping.repository;

import com.example.scrapping.model.MytekProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MytekProductRepository extends MongoRepository<MytekProduct, String> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}
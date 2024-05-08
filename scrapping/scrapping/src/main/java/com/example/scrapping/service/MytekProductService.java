package com.example.scrapping.service;
import com.example.scrapping.model.MytekProduct;
import com.example.scrapping.repository.MytekProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MytekProductService {
    @Autowired
    private MytekProductRepository mytekProductRepository;

    public List<MytekProduct> getAllProducts() {
        return mytekProductRepository.findAll();
    }
    public MytekProduct getProductById(String productId) {
        Optional<MytekProduct> productOptional = mytekProductRepository.findById(productId);
        return productOptional.orElse(null);
    }
    // Other methods as needed
}
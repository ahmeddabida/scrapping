package com.example.scrapping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlist")
public class WishListItem {
    @Id
    private String id;
    private MytekProduct product;

    // Constructor
    public WishListItem() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MytekProduct getProduct() {
        return product;
    }

    public void setProduct(MytekProduct product) {
        this.product = product;
    }
}
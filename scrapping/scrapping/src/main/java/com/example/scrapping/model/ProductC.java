package com.example.scrapping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comparison_re")
public class ProductC {
    @Id
    private String id;
    @Field("Mytek Product")
    private Product mytekProduct;
    @Field("Best Tunisianet Product")
    private Product bestTunisianetProduct;
    @Field("Best Tunisianet Similarity")
    private double bestTunisianetSimilarity;
    @Field("Best Zoom TN Product")
    private Product bestZoomTNProduct;
    @Field("Best Zoom TN Similarity")
    private double bestZoomTNSimilarity;
    @Field("Product Prices")
    private ProductPrices productPrices;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getMytekProduct() {
        return mytekProduct;
    }

    public void setMytekProduct(Product mytekProduct) {
        this.mytekProduct = mytekProduct;
    }

    public Product getBestTunisianetProduct() {
        return bestTunisianetProduct;
    }

    public void setBestTunisianetProduct(Product bestTunisianetProduct) {
        this.bestTunisianetProduct = bestTunisianetProduct;
    }

    public double getBestTunisianetSimilarity() {
        return bestTunisianetSimilarity;
    }

    public void setBestTunisianetSimilarity(double bestTunisianetSimilarity) {
        this.bestTunisianetSimilarity = bestTunisianetSimilarity;
    }

    public Product getBestZoomTNProduct() {
        return bestZoomTNProduct;
    }

    public void setBestZoomTNProduct(Product bestZoomTNProduct) {
        this.bestZoomTNProduct = bestZoomTNProduct;
    }

    public double getBestZoomTNSimilarity() {
        return bestZoomTNSimilarity;
    }

    public void setBestZoomTNSimilarity(double bestZoomTNSimilarity) {
        this.bestZoomTNSimilarity = bestZoomTNSimilarity;
    }

    public ProductPrices getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(ProductPrices productPrices) {
        this.productPrices = productPrices;
    }
    public String getMytekProductName() {
        if (mytekProduct != null) {
            return mytekProduct.getProductName();
        } else {
            return null;
        }
    }

    public String getBestTunisianetProductName() {
        if (bestTunisianetProduct != null) {
            return bestTunisianetProduct.getProductName();
        } else {
            return null;
        }
    }

    public String getBestZoomTNProductName() {
        if (bestZoomTNProduct != null) {
            return bestZoomTNProduct.getProductName();
        } else {
            return null;
        }
    }
}

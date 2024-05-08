package com.example.scrapping.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class ProductPrices {
    @Field("Mytek Price")
    private int mytekPrice;
    @Field("Tunisianet Price")
    private int tunisianetPrice;
    @Field("Zoom TN Price")
    private int zoomTNPrice;

    public int getMytekPrice() {
        return mytekPrice;
    }

    public void setMytekPrice(int mytekPrice) {
        this.mytekPrice = mytekPrice;
    }

    public int getTunisianetPrice() {
        return tunisianetPrice;
    }

    public void setTunisianetPrice(int tunisianetPrice) {
        this.tunisianetPrice = tunisianetPrice;
    }

    public int getZoomTNPrice() {
        return zoomTNPrice;
    }

    public void setZoomTNPrice(int zoomTNPrice) {
        this.zoomTNPrice = zoomTNPrice;
    }
}
package com.example.scrapping.service;


import com.example.scrapping.model.MytekProduct;
import com.example.scrapping.model.ProductC;
import com.example.scrapping.model.ProductWithPriceResponse;
import com.example.scrapping.model.WishListItem;
import com.example.scrapping.repository.MytekProductRepository;
import com.example.scrapping.repository.ProductRepository;
import com.example.scrapping.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository modeleRepository;
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private MytekProductRepository mytekProductRepository;

    @Autowired
    public ProductService(ProductRepository modeleRepository) {
        this.modeleRepository = modeleRepository;
    }

    public Optional<ProductC> getModeleById(String id) {
        return modeleRepository.findById(id);
    }
    public List<ProductWithPriceResponse> getProductWithPricesById(String id) {
        List<ProductWithPriceResponse> response = new ArrayList<>();

        Optional<ProductC> productOptional = modeleRepository.findById(id);
        if (productOptional.isPresent()) {
            ProductC productC = productOptional.get();

            // Ajouter le produit Mytek par défaut
            if (productC.getMytekProduct() != null) {
                response.add(new ProductWithPriceResponse(
                        productC.getMytekProduct().getProductName(),
                        productC.getProductPrices().getMytekPrice(),
                        productC.getMytekProduct().getLink(),
                        productC.getMytekProduct().getBrandImage(),
                        productC.getMytekProduct().getImage(),
                        productC.getMytekProduct().getDescription()
                ));
            }

            // Ajouter le produit Tunisianet s'il est disponible
            if (productC.getBestTunisianetProduct() != null) {
                response.add(new ProductWithPriceResponse(
                        productC.getBestTunisianetProduct().getProductName(),
                        productC.getProductPrices().getTunisianetPrice(),
                        productC.getBestTunisianetProduct().getLink(),
                        productC.getBestTunisianetProduct().getBrandImage(),
                        productC.getBestTunisianetProduct().getImage(),
                        productC.getBestTunisianetProduct().getDescription()
                ));
            }

            // Ajouter le produit Zoom TN s'il est disponible
            if (productC.getBestZoomTNProduct() != null) {
                response.add(new ProductWithPriceResponse(
                        productC.getBestZoomTNProduct().getProductName(),
                        productC.getProductPrices().getZoomTNPrice(),
                        productC.getBestZoomTNProduct().getLink(),
                        productC.getBestZoomTNProduct().getBrandImage(),
                        productC.getBestZoomTNProduct().getImage(),
                        productC.getBestZoomTNProduct().getDescription()
                ));
            }
        }

        // Trier les produits par prix minimum du plus bas au plus haut
        response.sort(Comparator.comparingInt(ProductWithPriceResponse::getPrice));

        return response;
    }

    public void addToWishlist(String productId) {
        MytekProduct product = mytekProductRepository.findById(productId).orElse(null);
        if (product != null) {
            WishListItem item = new WishListItem();
            item.setProduct(product);
            wishlistItemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    public void removeFromWishlist(String wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }

    public List<WishListItem> getWishlist() {
        return wishlistItemRepository.findAll();
    }
}


    // Vous pouvez ajouter d'autres méthodes de service ici si nécessaire


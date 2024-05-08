package com.example.scrapping.controller;

import com.example.scrapping.model.ProductC;
import com.example.scrapping.model.ProductWithPriceResponse;
import com.example.scrapping.model.WishListItem;
import com.example.scrapping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modele")
public class ProductController {

    private final ProductService modeleService;

    @Autowired
    public ProductController(ProductService modeleService) {
        this.modeleService = modeleService;
    }

    @GetMapping("/{id}")
    public Optional<ProductC> getModeleById(@PathVariable String id) {
        return modeleService.getModeleById(id);
    }
    @GetMapping("/product-with-prices/{id}")
    public List<ProductWithPriceResponse> getProductWithPricesById(@PathVariable String id) {
        return modeleService.getProductWithPricesById(id);
    }
    @PostMapping("add")
    public ResponseEntity<?> addToWishlist(@RequestParam String productId) {
        modeleService.addToWishlist(productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable String wishlistItemId) {
        modeleService.removeFromWishlist(wishlistItemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getall")
    public ResponseEntity<List<WishListItem>> getWishlist() {
        List<WishListItem> wishlist = modeleService.getWishlist();
        return ResponseEntity.ok(wishlist);
    }
}

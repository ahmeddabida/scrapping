package com.example.scrapping.repository;

import com.example.scrapping.model.WishListItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistItemRepository extends MongoRepository<WishListItem, String> {
}

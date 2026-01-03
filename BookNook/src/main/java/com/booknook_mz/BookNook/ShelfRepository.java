package com.booknook_mz.BookNook;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShelfRepository extends MongoRepository<Shelf, String> {
}

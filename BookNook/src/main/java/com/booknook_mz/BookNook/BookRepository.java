package com.booknook_mz.BookNook;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//deals with database

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    Optional<Book> findBookByBookId(String bookId);
    Optional<Book> findByTitle(String title); // Custom query method
}

package com.booknook_mz.BookNook;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired //want framework to instantiate class
    private BookRepository bookRepo;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookService(BookRepository bookRepo, MongoTemplate mongoTemplate) {
        this.bookRepo = bookRepo;
        this.mongoTemplate = mongoTemplate;
    }
    public List<Book> allBooks(){
        return bookRepo.findAll();
    }


    public Optional<Book> getBookByTitle(String title){
        return bookRepo.findByTitle(title);
    }

    public List<Book> searchTopTen(String searchInput){
        if (searchInput == null || searchInput.isBlank()) {
            // just return the first 10 of everything
            return bookRepo.findAll()
                    .stream()
                    .limit(10)
                    .toList();
        }
        //Creating a text-search criteria that handles word stemming (running -> run) & stop-word removal (the, and,etc)
        TextCriteria crit = TextCriteria.forDefaultLanguage().matching(searchInput);
        Query query = TextQuery.queryText(crit)//build query object, put textScore into score field of Book
                .sortByScore()
                .limit(10);
        return mongoTemplate.find(query, Book.class);
    }
}

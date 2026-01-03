package com.booknook_mz.BookNook;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//Getting Requests & Returning responses

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin(origins="http://localhost:3000/")
public class BookController {
    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")//finding search matches
    public List<Book> search(@RequestParam("q") String q){
        return bookService.searchTopTen(q);
    }

//    @GetMapping("/{bookId}")//search by id
//    public ResponseEntity<Optional<Book>> getSingleBook(@PathVariable String bookId){//PathVariable means whatever getting from PathVariable convert to objectid
//        return new ResponseEntity<Optional<Book>>(bookService.singleBook(bookId),HttpStatus.OK);
//    }
}

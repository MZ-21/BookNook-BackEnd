package com.booknook_mz.BookNook;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shelf")
@CrossOrigin(origins="http://localhost:3000/")
public class ShelfController {
    @Autowired
    private ShelfService shelfService;

    @PostMapping
    public ResponseEntity<Shelf> createShelf(@RequestBody Map<String, Object> payload) {
        String shelfName = payload.get("shelfName").toString();
        String icon = payload.get("icon").toString();
        List<Book> bookCollection = new ArrayList<>();
        Map<String, Object> bookMap = (Map<String, Object>) payload.get("bookCollection");

//        Book b = createBook(bookMap);
//        bookCollection.add(b);
        return new ResponseEntity<Shelf>(shelfService.createShelf(shelfName, icon, bookCollection), HttpStatus.CREATED);
    }

    @GetMapping("/shelfNames")
    public ResponseEntity<List<String>> getShelfNames(){
        List<Shelf> allShelves = shelfService.allShelves();
        List<String> shelfNames = new ArrayList<>();
        for(Shelf shelf : allShelves) {
            shelfNames.add(shelf.getShelfName());
        }
        return new ResponseEntity<>(shelfNames, HttpStatus.OK);
    }

    @GetMapping("/shelves")
    public ResponseEntity<List<Shelf>> getShelves(){
        return new ResponseEntity<>(shelfService.allShelves(), HttpStatus.OK);
    }


    @PostMapping("/deleteShelf")
    public void deleteShelf(@RequestBody Map<String, String> payload) {
        String shelfId = payload.get("shelfId");
        if (shelfId == null) {
            throw new IllegalArgumentException("Shelf name must be provided");
        }
        System.out.println(shelfId);
        shelfService.deleteShelf(shelfId);
    }
    @PostMapping("/editName")
    public void editShelfName(@RequestBody Map<String, String> payload) {
        String shelfId = payload.get("shelfId");
        String newShelfName = payload.get("shelfName");
        System.out.println(shelfId);
        System.out.println(newShelfName);
        shelfService.editShelfName(shelfId, newShelfName);
    }
    @GetMapping("/getBooks/{shelf}")
    public ResponseEntity<List<Book>> getBookCollection(@PathVariable String shelf) {
        return new ResponseEntity<>(shelfService.getBookCollection(shelf), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Shelf> addBook(@RequestBody Map<String, Object> payload) {
        System.out.println("in shelf");
        String shelfName = payload.get("id").toString();
        Map<String, Object> bookMap = (Map<String, Object>) payload.get("book");
        Book book = createBook(bookMap);
        return new ResponseEntity<Shelf>(shelfService.addBookToShelf(shelfName, book), HttpStatus.OK);
    }

    public Book createBook(Map<String, Object> bookMap) {
        Book book = new Book();
        Object raw = bookMap.get("bookId");

        if (raw instanceof String) {
            if (ObjectId.isValid((String) raw)) {
                book.setId(new ObjectId((String) raw));
            }
        } else if (raw instanceof Map) {
            // Fallback in case MongoDB ObjectId got serialized as a map
            // Try to reconstruct ObjectId from hex or just create a new one
            System.out.println("bookId is not a string — it's a map: " + raw);
        } else {
            System.out.println("bookId has unknown type: " + raw.getClass().getName());
        }

        book.setBookId((String) bookMap.get("bookId"));
        book.setTitle((String) bookMap.get("title"));
        book.setAuthor((String) bookMap.get("author"));
        Number ratingValue = (Number) bookMap.get("rating");
        book.setRating(ratingValue != null ? ratingValue.doubleValue() : null);
        book.setDescription((String) bookMap.get("description"));
        book.setPublisher((String) bookMap.get("publisher"));
        book.setCoverImg((String) bookMap.get("coverImg"));
        book.setGenres((List<String>) bookMap.get("genres"));

        return book;
    }
}


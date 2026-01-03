package com.booknook_mz.BookNook;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shelf {
    @Id
    private String id;
    private String shelfName;
    private String icon;
    //private String numberIcons;
    private List<Book> bookCollection;

    public Shelf(String shelfName, String icon, List<Book> bookCollection) {
        this.shelfName = shelfName;
        this.icon = icon;
        this.bookCollection = bookCollection;
    }
    public void addBook(Book book) {
        this.bookCollection.add(book);
    }

    public void removeBook(Book book) {
        this.bookCollection.remove(book);
    }

}
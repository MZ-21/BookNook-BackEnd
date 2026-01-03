package com.booknook_mz.BookNook;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//coordinates activities btwn different parts of the application
@Service
public class ShelfService {

    @Autowired
    private ShelfRepository shelfRepo;

    public Shelf createShelf(String shelfName, String icon, List<Book> bookCollection){
        return shelfRepo.insert(new Shelf(shelfName, icon, bookCollection));

    }

    public void deleteShelf(String id){
        shelfRepo.deleteById(id);
    }

    public void editShelfName(String id, String newShelfName){
        Shelf foundShelf =  shelfRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Shelf not found"));
        System.out.println(foundShelf);
        foundShelf.setShelfName(newShelfName);
        shelfRepo.save(foundShelf);
        System.out.println(foundShelf);
    }

    public List<Shelf> allShelves(){
        return shelfRepo.findAll();
    }

//    public ObjectId getShelfId(String shelfName){
//        List<Shelf> listShelves = shelfRepo.findAll();
//        ObjectId shelfId = null;
//
//        for(Shelf shelf : listShelves){
//            if(shelf.getShelfName().equals(shelfName)){
//                shelfId = shelf.getShelfId();
//            }
//        }
//        return shelfId;
//    }

    public List<Book> getBookCollection(String id){
        Shelf foundShelf =  shelfRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Shelf not found"));
        return foundShelf.getBookCollection();
    }

    public Shelf addBookToShelf(String id, Book bookObj){
        Shelf foundShelf =  shelfRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Shelf not found"));
        foundShelf.getBookCollection().add(bookObj);
        shelfRepo.save(foundShelf);
        return foundShelf;
    }
}
//Holds business logic & orchestrates operations btwn different parts of application -> Controller
//Repo layer deals with data access and persistence -> R
//the Object/Model layer deals with data & business logic? -> Model
//model, controller, service

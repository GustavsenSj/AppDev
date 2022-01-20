package no.ntnu.EXERCISE23;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * REST API controller for book collection
 */
@RestController
public class BookController {
    private ArrayList<Book> books;

    public BookController() {
        this.books = initializeData();
    }

    /**
     * Initialize dummy book data for the collection
     * @return List with books
     */
    private ArrayList<Book> initializeData() {
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book(1,"book1",1999,400));
        books.add(new Book(2,"book2",2021,167));
        books.add(new Book(3,"book3",2000,300));
        books.add(new Book(4,"book4",2005,500));
        return books;
    }

    /**
     * Get a specific book
     * @param id Id of the book to be returned
     * @return Book with the given ID or status 404
     */
    @GetMapping(value = "books/{bookId}")
    public ResponseEntity<Book> getOne(@PathVariable int id) {
        ResponseEntity<Book> response;
        Book book = findBookById(id);
        if (book != null) {
            response = new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Get All books
     * HTTP GET to /books
     * @return List of all books currently stored in the collection
     */
    @GetMapping(value = "books")
    public @ResponseBody ArrayList<Book> getAll() {
        return this.books;
    }

    /**
     * Add a book to the collection
     * @param book Book to be added, from HTTP response body
     * @return 200 OK status on success, 400 Bad request on error
     */
    @PostMapping(value = "books")
    public @ResponseBody ResponseEntity<String> addBook(Book book) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (book != null && book.isValid()) {
            Book existingBook = findBookById(book.getId());
            if (existingBook == null) {
                this.books.add(book);
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    /**
     * Delete a book from the collection
     * @param id ID of the book to delete
     * @return 200 OK on success, 404 Not found on error
     */
    @DeleteMapping("books/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        Book book = findBookById(id);
        if (book != null) {
            this.books.remove(book);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Update a book in the repository
     * @param id ID of the book to update, from the URL
     * @param book New book data to store, from request body
     * @return 200 OK on success, 400 Bad request on error
     */
    @PutMapping("books/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Book book) {
        String errorMessage = null;
        Book existingBook = findBookById(id);
        if (existingBook == null) {
            errorMessage = "No book with id " + id + " found";
        }
        if (book == null || !book.isValid()) {
            errorMessage = "Wrong data in request body";
        } else if (book.getId() != id) {
            errorMessage = "Book ID in the URL does not match the ID in JSON data (response body)";
        }

        ResponseEntity<String> response;
        if (errorMessage == null) {
            this.books.remove(existingBook);
            this.books.add(book);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * Search through the book collection, find the book by given ID
     * @param id Book ID
     * @return Book or null if not found
     */
    private Book findBookById(Integer id) {
        Book book = null;
        Iterator<Book> it = this.books.iterator();
        while (it.hasNext() && book == null) {
            Book b = it.next();
            if (b.getId() == id) {
                book = b;
            }
        }
        return book;
    }


}

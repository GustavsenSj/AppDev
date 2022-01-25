package no.ntnu.EXERCISE23;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * REST API controller for book collection
 */
@RestController
public class AuthorController {
    private List<Author> authors;

    public AuthorController() { this.authors = initializeData();}


    /**
     * Initialize dummy book data for the collection
     * @return LinkedList with Authors
     */
    private LinkedList<Author> initializeData() {
        LinkedList<Author> authors = new LinkedList<>();
        authors.add(new Author(1,"author1","lastName1",1996));
        authors.add(new Author(2,"author2","lastName2",1987));
        authors.add(new Author(3,"author3","lastName3",2000));
        return authors;
    }

    /**
     * Get all authors
     * @return List of all authors stored in the collection
     */
    @GetMapping(value = "authors")
    public @ResponseBody List<Author> getAll() {
        return this.authors;
    }

    /**
     * get a specific author
     * @param id Id of the author to be returned
     * @return author with the given id or status 404
     */
    @GetMapping(value = "authors/{authorId}")
    public ResponseEntity<Author> getOne(@PathVariable int id) {
        ResponseEntity<Author> response;
        Author author = findAuthorById(id);
        if (author != null) {
            response = new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * Add a Author to the collection
     * @param author Author to be added, from HTTP response body
     * @return 200 OK status on success, 400 Bad request on error
     */
    @PostMapping(value = "authors")
    public  @ResponseBody ResponseEntity<String> addAuthor(@PathVariable Author author) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (author != null && author.isValid()) {
            Author existingAuthor = findAuthorById(author.getId());
            if (existingAuthor == null) {
                this.authors.add(author);
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    /**
     * Delete an author form the collection
     * @param id id of the author to delete
     * @return 200 OK on success, 404 Not found on error
     */
    @DeleteMapping("authors/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        Author author = findAuthorById(id);
        if (author != null) {
            this.authors.remove(author);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Update an author in the collection
     * @param id Id of the author to update, from URL
     * @param author New author to store, form the request body
     * @return 200 OK on success, 400 Bad request on error
     */
    @PutMapping("authors/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Author author) {
        String errorMessage = null;
        Author existingAuthor = findAuthorById(id);
        if (existingAuthor == null) {
            errorMessage = "No author with an id of " + id + " found";
        }
        if (author == null || !author.isValid()) {
            errorMessage = "Wrong data in request body";
        } else if (author.getId() != id) {
            errorMessage = "Author Id in the URL does not match the Id in JSON data";
        }

        ResponseEntity<String> response;
        if (errorMessage == null) {
            this.authors.remove(existingAuthor);
            this.authors.add(author);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else  {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    /**
     * Search through the author collection, find the author by given id
     * @param id Author id
     * @return Author of null if not found
     */
    private Author findAuthorById(int id) {
        Author author = null;
        Iterator<Author> iterator = this.authors.iterator();
        while (iterator.hasNext() && author == null) {
            Author tempAuthor = iterator.next();
            if (tempAuthor.getId() == id) {
                author = tempAuthor;
            }
        }
        return author;
    }


}

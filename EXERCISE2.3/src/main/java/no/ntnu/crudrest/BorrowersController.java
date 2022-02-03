package no.ntnu.crudrest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BorrowersController {
    private List<Borrowers> borrowers;

    public BorrowersController() {
        initializeData();
    }
    /**
     * Initialize dummy borrower data for the collection
     */
    private void initializeData() {
        borrowers = new LinkedList<>();
        borrowers.add(new Borrowers("Michal", 1235, "Oslo", 12345678));
        borrowers.add(new Borrowers("Sjur", 1234, "Ålesund", 13245679));
    }

    /**
     * Get All books
     * HTTP GET to /borrowers
     * @return List of all books currently stored in the collection
     */
    @GetMapping
    public List<Borrowers> getAll() {
        return borrowers;
    }


    /**
     * Get a specific book
     * @param id Id of the book to be returned
     * @return Borrower with the given ID or status 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Borrowers> getOne(@PathVariable Integer id) {
        ResponseEntity<Borrowers> response;
        Borrowers borrower = findBorrowerById(id);
        if (borrower != null) {
            response = new ResponseEntity<>(borrower, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Add a book to the collection
     * @param borrowers Borrower to be added, from HTTP response body
     * @return 200 OK status on success, 400 Bad request on error
     */
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Borrowers borrowers) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (borrowers != null && borrowers.isValid()) {
            Borrowers existingBorrower = findBorrowerById(borrowers.getBorrowerID());
            if (existingBorrower == null) {
                this.borrowers.add(borrowers);
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    /**
     * Delete a book from the collection
     * @param id ID of the borrower to delete
     * @return 200 OK on success, 404 Not found on error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        Borrowers borrowers = findBorrowerById(id);
        if (borrowers != null) {
            this.borrowers.remove(borrowers);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Update a book in the repository
     * @param id ID of the borrower to update, from the URL
     * @param borrowers New book data to store, from request body
     * @return 200 OK on success, 400 Bad request on error
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Borrowers borrowers) {
        String errorMessage = null;
        Borrowers existingBook = findBorrowerById(id);
        if (existingBook == null) {
            errorMessage = "No book with id " + id + " found";
        }
        if (borrowers == null || !borrowers.isValid()) {
            errorMessage = "Wrong data in request body";
        } else if (borrowers.getBorrowerID() != id) {
            errorMessage = "Book ID in the URL does not match the ID in JSON data (response body)";
        }

        ResponseEntity<String> response;
        if (errorMessage == null) {
            this.borrowers.remove(existingBook);
            this.borrowers.add(borrowers);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    /**
     * Search through the book collection, find the book by given ID
     * @param id borrower ID
     * @return Borrower or null if not found
     */
    private Borrowers findBorrowerById(Integer id) {
        Borrowers borrower = null;
        Iterator<Borrowers> it = borrowers.iterator();
        while (it.hasNext() && borrowers == null) {
            Borrowers b = it.next();
            if (b.getBorrowerID() == id) {
                borrower = b;
            }
        }
        return borrower;
    }
}

package no.ntnu.crudrest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * REST API controller for Loans collection
 */
@RestController
@RequestMapping("/loans")
public class LoansController {

    private List<Loan> loans;

    public LoansController() {
        initializeData();
    }

    /**
     * Initialize dummy book data for the collection
     */
    private void initializeData() {
        loans = new LinkedList<>();
        loans.add(new Loan(1, 1, 1, 11111, "2021-02-21", "2021-11-11"));
        loans.add(new Loan(2, 2, 2, 11113, "2021-03-19","2021-11-11"));
    }

    /**
     * Get All books
     * HTTP GET to /books
     * @return List of all books currently stored in the collection
     */
    @GetMapping
    public List<Loan> getAll() {
        return loans;
    }

    /**
     * Get a specific book
     * @param id Id of the book to be returned
     * @return Book with the given ID or status 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getOne(@PathVariable Integer id) {
        ResponseEntity<Loan> response;
        Loan loan = findLoanById(id);
        if (loan != null) {
            response = new ResponseEntity<>(loan, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Add a book to the collection
     * @param loan Book to be added, from HTTP response body
     * @return 200 OK status on success, 400 Bad request on error
     */
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Loan loan) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (loan != null && loan.isValid()) {
            Loan existingBook = findLoanById(loan.getId());
            if (existingBook == null) {
                loans.add(loan);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        Loan loan = findLoanById(id);
        if (loan != null) {
            loans.remove(loan);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Update a book in the repository
     * @param id ID of the book to update, from the URL
     * @param loan New book data to store, from request body
     * @return 200 OK on success, 400 Bad request on error
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Loan loan) {
        String errorMessage = null;
        Loan existingBook = findLoanById(id);
        if (existingBook == null) {
            errorMessage = "No book with id " + id + " found";
        }
        if (loan == null || !loan.isValid()) {
            errorMessage = "Wrong data in request body";
        } else if (loan.getId() != id) {
            errorMessage = "Book ID in the URL does not match the ID in JSON data (response body)";
        }

        ResponseEntity<String> response;
        if (errorMessage == null) {
            loans.remove(existingBook);
            loans.add(loan);
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
    private Loan findLoanById(Integer id) {
        Loan loan = null;
        Iterator<Loan> it = loans.iterator();
        while (it.hasNext() && loan == null) {
            Loan b = it.next();
            if (b.getId() == id) {
                loan = b;
            }
        }
        return loan;
    }
}


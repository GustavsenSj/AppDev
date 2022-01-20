package no.ntnu.EXERCISE23;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private int year;
    private int numberOfPages;
    private List<Integer> authors;

    public Book(int id, String title, int year, int numberOfPages, List<Integer> authors) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.authors = authors;
    }

    /**
     * Check if this object is a valid book
     * @return True if the book is valid, false otherwise
     */
    public boolean isValid() {
        return id > 0 && title != null && !title.equals("");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Gets a list of all the authors
     * @return all the author IDs to this book as a list
     */
    public List<Integer> getAuthors() {
        return this.authors;
    }

    /**
     * Checks if this book has an author with specified id
     * @param authorToBeFound the id of the author to be found
     * @return ture if found or false if not
     */
    public boolean hasAuthor(int authorToBeFound) {
        return this.authors.contains(authorToBeFound);
    }

    public void addAuthor(int authorId) {
        if (!this.authors.contains(authorId) || authorId > 0) {
            this.authors.add(authorId);
        }
    }


}

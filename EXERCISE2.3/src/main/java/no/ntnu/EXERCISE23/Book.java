package no.ntnu.EXERCISE23;

public class Book {
    private int id;
    private String title;
    private int year;
    private int numberOfPages;

    public Book(int id, String title, int year, int numberOfPages) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
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
}

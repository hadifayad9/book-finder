package ca.mohawkcollege.fayad;

import java.util.ArrayList;

/**
 * Represents a VolumeInfo class. Is used for the JSON Google API object. Sets JSON objects to their
 * items
 */
class VolumeInfo{
    /**
     * A string of the title
     */
    public String title;
    /**
     * A ArrayList string of the authors
     */
    public ArrayList<String> authors;
    /**
     * A string of the description
     */
    public String description;
    /**
     * A string of the publisher
     */
    public String publisher;
    /**
     * A string of the publishedDate
     */
    public String publishedDate;
    /**
     * A instance of the ImageLinks imagelinks
     */
    public ImageLinks imageLinks;
}

/**
 * Represents a ImageLinks class. Is used for the JSON Google API object. Sets JSON objects to their
 * items
 */
class ImageLinks{
    /**
     * A string for the thumbnail url that gets returned
     */
    public String thumbnail;
}
/**
 * Represents a item class. Is used for the JSON Google API object. Sets JSON objects to their
 * items
 */
class Items{
    /**
     * A string for the id of the book
     */
    public String id;
    /**
     * A instance of the volumeInfo Class
     */
    public VolumeInfo volumeInfo;

    /**
     * overriding the toString method to return the title and the author
     * @return "Title: "  + volumeInfo.title + "\n" +  "Author: " + volumeInfo.authors;
     */
    @Override
    public String toString() {
        return "Title: "  + volumeInfo.title + "\n" +  "Author: " + volumeInfo.authors;
    }
}
/**
 * Represents a book class. Is used for the JSON Google API object. Sets JSON objects to their
 * items
 */
public class Books {
    /**
     * ArrayList of the class items that holds different information of the book
     */
    public ArrayList<Items> items;

}

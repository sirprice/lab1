package models;

import java.util.Date;

/**
 * Created by Scalman on 01/01/16.
 */
public class Review {

    private Date date;
    private int rating;
    private String text;


    public Review(Date date, int rating, String text) {
        this.date = date;
        this.rating = rating;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

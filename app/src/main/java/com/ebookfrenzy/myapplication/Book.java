package com.ebookfrenzy.myapplication;

public class Book {

    private String bookid, name, author, price, image, category;

    public Book(String bookid, String name, String author, String price, String image, String category) {
        this.bookid = bookid;
        this.name = name;
        this.author = author;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

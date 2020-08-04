package com.ebookfrenzy.myapplication;

public class User {
    String bookname;
    String author;

    public User(String bookname, String author) {
        this.bookname = bookname;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}


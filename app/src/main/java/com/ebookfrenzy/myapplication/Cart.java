package com.ebookfrenzy.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {

    String cartid, bookid, userid, bookname, author, image, category, quantity, total_price;

    public Cart(String cartid, String bookid, String userid, String bookname, String author, String image, String category, String quantity, String total_price) {
        this.cartid = cartid;
        this.bookid = bookid;
        this.userid = userid;
        this.bookname = bookname;
        this.author = author;
        this.image = image;
        this.category = category;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    protected Cart(Parcel in) {
        cartid = in.readString();
        bookid = in.readString();
        userid = in.readString();
        bookname = in.readString();
        author = in.readString();
        image = in.readString();
        category = in.readString();
        quantity = in.readString();
        total_price = in.readString();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cartid);
        parcel.writeString(bookid);
        parcel.writeString(userid);
        parcel.writeString(bookname);
        parcel.writeString(author);
        parcel.writeString(image);
        parcel.writeString(category);
        parcel.writeString(quantity);
        parcel.writeString(total_price);
    }
}

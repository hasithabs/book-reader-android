package com.example.enters.book_reader.book;

/**
 * Created by EnTeRs on 3/25/2018.
 */

public class Book {
    int id;
    String title;
    String author;
    String imgPath;
    String filePath;
    int type;

    public Book(int id, String title, String author, String imgPath, String filePath, int type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imgPath = imgPath;
        this.filePath = filePath;
        this.type = type;
    }

    public Book(int id, String title, String author, String imgPath, String filePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imgPath = imgPath;
        this.filePath = filePath;
        this.type = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

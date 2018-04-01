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
    int pageCount;
    int currentPage;
    String language;

    public Book(int id, String title, String author, String imgPath, String filePath, int type,
                int pageCount, int currentPage, String language) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imgPath = imgPath;
        this.filePath = filePath;
        this.type = type;
        this.pageCount = pageCount;
        this.currentPage = currentPage;
        this.language = language;
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

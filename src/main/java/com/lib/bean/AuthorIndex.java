package com.lib.bean;

public class AuthorIndex {
    private String author;
    private Integer p;
    private Integer length;

    public AuthorIndex() {
    }

    public AuthorIndex(String author, Integer p, Integer length) {
        this.author = author;
        this.p = p;
        this.length = length;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "AuthorIndex{" +
                "author='" + author + '\'' +
                ", p=" + p +
                ", length=" + length +
                '}';
    }
}

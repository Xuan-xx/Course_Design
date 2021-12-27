package com.lib.bean;


public class Book {
    private Integer id;
    private String num;
    private String name;
    private String author;
    private String press;
    private String category;
    private Integer collection;
    private Integer lending;
    private Integer p1;
    private Integer p2;
    private Integer p3;

    public Book() {
    }

    public Book(String num, String name, String author, String press,
                String category, Integer collection, Integer lending) {
        this.num = num;
        this.name = name;
        this.author = author;
        this.press = press;
        this.category = category;
        this.collection = collection;
        this.lending = lending;
    }

    public Book(String num, String name, String author, String press, String category, Integer collection,
                Integer lending, Integer p1, Integer p2, Integer p3) {
        this.num = num;
        this.name = name;
        this.author = author;
        this.press = press;
        this.category = category;
        this.collection = collection;
        this.lending = lending;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getLending() {
        return lending;
    }

    public void setLending(Integer lending) {
        this.lending = lending;
    }

    public Integer getP1() {
        return p1;
    }

    public void setP1(Integer p1) {
        this.p1 = p1;
    }

    public Integer getP2() {
        return p2;
    }

    public void setP2(Integer p2) {
        this.p2 = p2;
    }

    public Integer getP3() {
        return p3;
    }

    public void setP3(Integer p3) {
        this.p3 = p3;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", category='" + category + '\'' +
                ", collection=" + collection +
                ", lending=" + lending +
                ", p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                '}';
    }
}

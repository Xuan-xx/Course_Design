package com.lib.bean;
public class BookIndex {
    private String name;
    private Integer p;
    private Integer length;

    public BookIndex() {
    }

    public BookIndex(String name, Integer p, Integer length) {
        this.name = name;
        this.p = p;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "BookIndex{" +
                "name='" + name + '\'' +
                ", p=" + p +
                ", length=" + length +
                '}';
    }
}

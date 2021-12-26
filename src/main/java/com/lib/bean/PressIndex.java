package com.lib.bean;

public class PressIndex {
    private String press;
    private Integer p;
    private Integer length;

    public PressIndex() {
    }

    public PressIndex(String press, Integer p, Integer length) {
        this.press = press;
        this.p = p;
        this.length = length;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
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
        return "PressIndex{" +
                "press='" + press + '\'' +
                ", p=" + p +
                ", length=" + length +
                '}';
    }
}

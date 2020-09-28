package com.shujia.bean;

public class SumScore {
    private String id;
    private Integer sumSocre;

    public SumScore(String id, Integer sumSocre) {
        this.id = id;
        this.sumSocre = sumSocre;
    }

    public SumScore() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSumSocre() {
        return sumSocre;
    }

    public void setSumSocre(Integer sumSocre) {
        this.sumSocre = sumSocre;
    }

    @Override
    public String toString() {
        return "SumScore{" +
                "id='" + id + '\'' +
                ", sumSocre=" + sumSocre +
                '}';
    }
}

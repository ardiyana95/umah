package com.example.umah_app.model;

public class Attribute {
    private Integer id;
    private Integer value;
    private String name;
    private Integer kriteria_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKriteria_id() {
        return kriteria_id;
    }

    public void setKriteria_id(Integer kriteria_id) {
        this.kriteria_id = kriteria_id;
    }
}

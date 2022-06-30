package com.example.tripletest.customEnum;

public enum Type {
    REVIEW("REVIEW");
    private final String type;

    Type(String type){this.type = type;}
    public String getType(){return type;}
}

package com.example.tripletest.customEnum;

public enum Action {
    ADD("ADD") , DELETE("DELETE") , MOD("MOD");


    private final String action;

    Action(String action) {this.action = action;}
    public String getAction() {return action;}
}

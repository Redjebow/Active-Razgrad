package com.example.Active.Razgrad.community;

public enum Category {
    MUSIC("МУЗИКА"),
    DANCE("ТАНЦИ"),
    SOCIAL("СОЦИАЛНИ ДЕЙНОСТИ"),
    THEATER("ТЕАТЪР"),
    SPORT("СПОРТ"),
    MOVIE("КИНО"),
    OTHER("ДРУГО");

    private final String label;
    Category(String label) {
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
}

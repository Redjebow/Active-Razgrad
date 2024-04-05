package com.example.Active.Razgrad.user;

public enum Role {

    ROLE_ADMIN("АДМИН"),
    ROLE_COMMUNITY("ОРГАНИЗАЦИЯ"),
    ROLE_USER("ПОТРЕБИТЕЛ");
    private final String label;
    Role(String label) {
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
}

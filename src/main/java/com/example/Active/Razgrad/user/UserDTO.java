package com.example.Active.Razgrad.user;

import com.example.Active.Razgrad.community.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

public class UserDTO {
    @NotNull
    @Size(min=4)
    private String username;

    private String password;
    private String repeatPassword;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size  (max = 200)
    private String firstName;

    @Size  (max = 20)
    private String lastName;
    @Column(nullable = true, length = 64)
    private String photos;
    @NotNull
    @Min(10)
    private int telephone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Size  (max = 100)
    private String address;


    @Max(1000000000)
    private int bulstat;
    private String website;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBulstat() {
        return bulstat;
    }

    public void setBulstat(int bulstat) {
        this.bulstat = bulstat;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}

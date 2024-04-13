package com.example.Active.Razgrad.user;

import com.example.Active.Razgrad.community.Category;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;//ще го ползваме за цялото име на организацивта
    private String lastName;
    private int telephone;
    @Enumerated(EnumType.STRING)
    private Role role;
    //полетата на организацията
    private String address;
    private int bulstat;
    private String website;
    @Column(nullable = true, length = 64)
    private String photos;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;

        return "/user-photos/" + id + "/" + photos;
    }
}

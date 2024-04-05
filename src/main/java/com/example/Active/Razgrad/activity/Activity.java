package com.example.Active.Razgrad.activity;

import com.example.Active.Razgrad.comments.Comment;
import com.example.Active.Razgrad.community.Category;
//import com.example.Active.Razgrad.community.Community;
import com.example.Active.Razgrad.user.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Date data;
    private String address;
    private int duration;
    private int price;
    @ManyToOne
    @JoinColumn(name = "community_id")
    private User communityCreator;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> commentsList;

    public User getCommunityCreator() {
        return communityCreator;
    }

    public void setCommunityCreator(User communityCreator) {
        this.communityCreator = communityCreator;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

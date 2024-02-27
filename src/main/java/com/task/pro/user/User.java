package com.task.pro.user;

import com.task.pro.task.Task;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private  String email;
    private String password;
    private LocalDateTime createdAtDate;
    private LocalDateTime updatedAtDate;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Task> tasks;
    public User() {
    }

    public User(Long id, String firstName, String lastName, String email, String password, LocalDateTime createdAtDate, LocalDateTime updatedAtDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAtDate = createdAtDate;
        this.updatedAtDate = updatedAtDate;
    }

    public User(String firstName, String lastName, String email, String password, LocalDateTime createdAtDate, LocalDateTime updatedAtDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAtDate = createdAtDate;
        this.updatedAtDate = updatedAtDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(LocalDateTime createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public LocalDateTime getUpdatedAtDate() {
        return updatedAtDate;
    }

    public void setUpdatedAtDate(LocalDateTime updatedAtDate) {
        this.updatedAtDate = updatedAtDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAtDate=" + createdAtDate +
                ", updatedAtDate=" + updatedAtDate +
                '}';
    }
}

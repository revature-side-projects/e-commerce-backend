package com.revature.models;

import com.revature.dtos.RegisterRequest;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private long user_id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User(RegisterRequest regReq) {
        this.email = regReq.getEmail();
        this.password = regReq.getPassword();
        this.firstName = regReq.getFirstName();
        this.lastName = regReq.getLastName();
    }

    public long getUser_id() { return user_id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setUser_id(long user_id) { this.user_id = user_id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

}

package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@TypeAlias("user")
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;

    private String password;

    @DBRef
    private List<Role> authorities;

    public User() {
        this.authorities=new ArrayList<>();
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.name = username;
        this.password = password;
        this.authorities=new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends Role> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }
}

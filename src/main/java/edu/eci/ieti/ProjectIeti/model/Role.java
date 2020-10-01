package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;


@Document(collection = "roles")
@TypeAlias("role")
public class Role implements GrantedAuthority {

    @Id
    private  String id;

    @Indexed(unique = true)
    private String role;

    public Role() {

    }
    @Override
    public String getAuthority() {
        return this.role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}

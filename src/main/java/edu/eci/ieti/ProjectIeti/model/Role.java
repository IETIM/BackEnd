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
    private ERole role;

    public Role() {

    }
    @Override
    public String getAuthority() {
        return this.role.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }


}

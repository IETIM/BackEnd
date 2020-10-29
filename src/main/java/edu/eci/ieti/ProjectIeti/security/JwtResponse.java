package edu.eci.ieti.ProjectIeti.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private String jwttoken;

    public JwtResponse(){

    }
    public JwtResponse(String jwttoken) {

        this.jwttoken = jwttoken;

    }

    public String getToken(){
        return this.jwttoken;
    }

    public void setToken(String token){
        this.jwttoken = token;
    }

}

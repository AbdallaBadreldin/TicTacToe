/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author Bossm
 */
public class LoginRequest implements Serializable {

    private String username;
    private String password;
    private String token;

    public void LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
       public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }
     public void setPassword(String Password) {
        this.password = Password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    


}

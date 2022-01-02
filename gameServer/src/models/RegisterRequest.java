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
public class RegisterRequest implements Serializable {

    private String username;
    private String password;
    private boolean succussStatue;
    private final boolean REGISTERED_SUCCESSFULLY = true;
    private final boolean REGISTERED_FAILED = false;

    public void RegisterRequest(boolean succussStatue) {
        this.succussStatue = succussStatue;
    }

    public void RegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRegisterSucceed() {
        return succussStatue;
    }
}

package com.example.mysqliteappnueva;

public class Usuario {

    private String user;
    private String passw;
    private String rol;


    public Usuario() {

        user = "";
        passw= "";
        rol ="";

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

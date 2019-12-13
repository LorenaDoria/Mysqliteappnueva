package com.example.mysqliteappnueva;

public class Usuario {

    private String user;
    private String passw;
    private String rol;
    private String date;


    public Usuario(String user, String passw, String rol, String date) {
        this.user = user;
        this.passw= passw;
        this.rol = rol;
        this.date = date;
    }

    public Usuario() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

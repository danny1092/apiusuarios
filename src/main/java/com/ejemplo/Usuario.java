package com.ejemplo;

public class Usuario {
  public int id;
  public String nombre;
  public String correo;

  public Usuario() {} // necesario para que Javalin lo convierta desde JSON

  public Usuario(String nombre, String correo) {
    this.nombre = nombre;
    this.correo = correo;
  }
}

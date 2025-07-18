package com.ejemplo;

public class Error {
  public int id;
  public String mensaje;
  public String descripcion;

  public Error() {} // necesario para que Javalin lo convierta desde JSON

  public Error(String mensaje, String descripcion) {
    this.mensaje = mensaje;
    this.descripcion = descripcion;
  }
}

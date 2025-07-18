package com.ejemplo;

import java.sql.*;
import java.util.*;

public class UsuarioDAO {
  private final Connection conexion;

  public UsuarioDAO() {
    try {
      conexion = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/miapp",
          "root",  // <-- tu usuario
          ""  // <-- tu contraseña
      );
    } catch (SQLException e) {
      throw new RuntimeException("Error al conectar a MySQL", e);
    }
  }
  
  public List<Usuario> listar() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios";
    try (Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        Usuario u = new Usuario();
        u.id = rs.getInt("id");
        u.nombre = rs.getString("nombre");
        u.correo = rs.getString("correo");
        lista.add(u);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return lista;
  }

  public List<Usuario> listarId(int id) {
    List<Usuario> lista = new ArrayList<>();
    List<Error> listaE = new ArrayList<>();
    String sql = "SELECT * FROM usuarios WHERE id=?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)){
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Usuario u = new Usuario();
        u.id = rs.getInt("id");
        u.nombre = rs.getString("nombre");
        u.correo = rs.getString("correo");
        lista.add(u);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return lista;
  }

  public void insertar(Usuario u) {
    String sql = "INSERT INTO usuarios (nombre, correo) VALUES (?, ?)";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
      stmt.setString(1, u.nombre);
      stmt.setString(2, u.correo);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void actualizar(int id, Usuario u) {
    String sql = "UPDATE usuarios SET nombre=?, correo=? WHERE id=?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
      stmt.setString(1, u.nombre);
      stmt.setString(2, u.correo);
      stmt.setInt(3, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void eliminar(int id) {
    String sql = "DELETE FROM usuarios WHERE id=?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
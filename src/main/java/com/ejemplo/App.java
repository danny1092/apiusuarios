package com.ejemplo;

import io.javalin.Javalin;
import java.util.List;
import java.util.Map;

public class App {
  public static void main(String[] args) {

    UsuarioDAO dao = new UsuarioDAO(); // acceso a la BD

    Javalin app = Javalin.create().start(7070); // inicia servidor en puerto 7070

// Middleware para permitir CORS en todas las respuestas
    app.before(ctx -> {
      ctx.header("Access-Control-Allow-Origin", "*");
      ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      ctx.header("Access-Control-Allow-Headers", "Content-Type");
    });
    // Comentario
    //Comentario 2
    // comentario 3
    // comentario 4
    // Ruta para manejar la preflight OPTIONS (CORS)
    app.options("/*", ctx -> {
      ctx.header("Access-Control-Allow-Origin", "*");
      ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      ctx.header("Access-Control-Allow-Headers", "Content-Type");
      ctx.status(204);
    });

    // Ruta para obtener todos los usuarios
    app.get("/usuarios", ctx -> {
      ctx.json(dao.listar()); // devuelve lista en formato JSON
    });

    // Ruta para obtener un usuario
    app.get("/usuarios/{id}", ctx -> {
      int id = Integer.parseInt(ctx.pathParam("id"));
      List<Usuario> usuarios = dao.listarId(id); // o el tipo real de la lista

      if (usuarios.isEmpty()) {
        ctx.status(404).json(Map.of("mensaje", "Usuario no encontrado"));
      } else {
        ctx.json(usuarios);
      }

      //ctx.json(dao.listarId(id)); // devuelve lista en formato JSON
      //ctx.status(404).json(Map.of("mensaje", "Usuario no encontrado"));
    });



    // Ruta para insertar un nuevo usuario
    app.post("/usuarios", ctx -> {
      Usuario nuevo = ctx.bodyAsClass(Usuario.class); // convierte JSON a Usuario
      dao.insertar(nuevo);
      ctx.status(201).result("Usuario creado");
    });

    // Ruta para actualizar usuario por ID
    app.put("/usuarios/{id}", ctx -> {
      int id = Integer.parseInt(ctx.pathParam("id"));
      Usuario actualizado = ctx.bodyAsClass(Usuario.class);
      dao.actualizar(id, actualizado);
      ctx.result("Usuario actualizado");
    });

    // Ruta para eliminar usuario por ID
    app.delete("/usuarios/{id}", ctx -> {
      int id = Integer.parseInt(ctx.pathParam("id"));
      dao.eliminar(id);
      ctx.result("Usuario eliminado");
    });
  }
}

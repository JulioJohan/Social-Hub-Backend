package com.socialhub.controller;

import com.socialhub.model.dto.CommentDTO;
import com.socialhub.model.entity.Comment;
import com.socialhub.model.entity.Post;
import com.socialhub.model.entity.Response;
import com.socialhub.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    /**
     * Maneja la solicitud para obtener todos los componentes existentes.
     *
     * @return ResponseEntity con una lista de comentarios en el cuerpo de la respuesta.
     */
    @GetMapping(path = "/findAllComment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> findAllPost(){
        // Invoca al servicio para obtener todos los comentarios
        Response<Comment> response = commentService.findAllComment();

        // Retorna una respuesta con la lista de comentarios y el estado HTTP OK (200)
        return new ResponseEntity<Response<Comment>> (response, HttpStatus.OK);
    }
    
    /**
     * Maneja la solicitud para obtener todos los componentes existentes por paginaciones.
     *
     * @return ResponseEntity con una lista de comentarios en el cuerpo de la respuesta.
     */
    @GetMapping(path = "/findAllComment/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> findAllPost(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        // Invoca al servicio para obtener todos los comentarios
        Response<Comment> response = commentService.findAllComment(page, size);

        // Retorna una respuesta con la lista de comentarios y el estado HTTP OK (200)
        return new ResponseEntity<Response<Comment>> (response, HttpStatus.OK);
    }

    /**
     * Maneja la solicitud para obtener un comentario específico por su ID.
     *
     * @param idComment el ID del comentario a buscar.
     * @return ResponseEntity con el post encontrado en el cuerpo de la respuesta.
     */
    @GetMapping(path = "/findByIdComment/{idComment}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> findByIdComment(@PathVariable("idComment") Integer idComment){
        // Invoca al servicio para buscar el post por su ID
        Response<Comment> response = commentService.findByIdComment(idComment);

        // Retorna una respuesta con el post encontrado y el estado HTTP OK (200)
        return new ResponseEntity<Response<Comment>> (response, HttpStatus.OK);
    }

    /**
     * Maneja la solicitud para obtener los comentarios de un usuario específico por su ID.
     *
     * @param idUsuario el ID del usuario cuyos comentarios se desean buscar.
     * @return ResponseEntity con una lista de comentarios del usuario en el cuerpo de la respuesta.
     */
    @GetMapping(path = "/findByPostComment/{idComment}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> findByPostComment(@PathVariable("idComment") Integer idComment){
        // Invoca al servicio para buscar los comentarios del usuario por su ID
        Response<Comment> response = commentService.findByPostComment(idComment);

        // Retorna una respuesta con la lista de comentarios del usuario y el estado HTTP OK (200)
        return new ResponseEntity<Response<Comment>> (response, HttpStatus.OK);
    }


    /**
     * Maneja la solicitud para crear un nuevo post.
     *
     * @param comment los datos del post a crear.
     * @return ResponseEntity con el post creado en el cuerpo de la respuesta.
     * @throws IOException si ocurre algún error relacionado con la entrada/salida de datos.
     */
    @PostMapping(path = "/createComment", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<Comment>> createComment(@ModelAttribute CommentDTO comment) throws IOException {
        // Invoca al servicio para crear el post utilizando los datos proporcionados

        //Se envia el numero 0 para indicar que es una publicacion de bookface
        //Se envia el numero 1 para indicar que es una publicacion de toktic
        Response<Comment> response = commentService.createComment(comment);

        // Retorna una respuesta con el post creado y el estado HTTP OK (200)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Maneja la solicitud para actualizar un post existente.
     *
     * @param comment los datos del post actualizado.
     * @return ResponseEntity con el post actualizado en el cuerpo de la respuesta.
     * @throws IOException si ocurre algún error relacionado con la entrada/salida de datos.
     */
    @PutMapping(path = "/updateComment", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<Comment>> updateComment(@ModelAttribute CommentDTO comment) throws IOException {
        // Invoca al servicio para actualizar el post utilizando los datos proporcionados
        Response<Comment> response = commentService.updateComment(comment);

        // Retorna una respuesta con el post actualizado y el estado HTTP OK (200)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Maneja la solicitud para eliminar un post existente por su ID.
     *
     * @param idPost el ID del post a eliminar.
     * @return ResponseEntity con información sobre el resultado de la eliminación en el cuerpo de la respuesta.
     */
    @DeleteMapping(path = "/deleteComment/{idPost}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> deleteComment(@PathVariable("idPost") Integer idPost) {
        // Invoca al servicio para eliminar el post por su ID
        Response<Comment> response = commentService.deleteComment(idPost);

        // Retorna una respuesta con información sobre el resultado de la eliminación y el estado HTTP OK (200)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Maneja la solicitud para incrementar el contador de "likes" de un post por su ID.
     *
     * @param idPost el ID del post al cual se desea incrementar el contador de "likes".
     * @return ResponseEntity con el post actualizado en el cuerpo de la respuesta.
     */
    @PutMapping(path = "/sumLike/{idComment}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> sumLike(@PathVariable("idComment") Integer idComment) {
        // Invoca al servicio para incrementar el contador de "likes" del post por su ID
        Response<Comment> response = commentService.sumLike(idComment);

        // Retorna una respuesta con el post actualizado y el estado HTTP OK (200)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Maneja la solicitud para restar el contador de "likes" de un post por su ID.
     *
     * @param idPost el ID del post al cual se desea decrementar el contador de "likes".
     * @return ResponseEntity con el post actualizado en el cuerpo de la respuesta.
     */
    @PutMapping(path = "/subtractLike/{idComment}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<Comment>> subtractLike(@PathVariable("idComment") Integer idComment) {
        // Invoca al servicio para decrementar el contador de "likes" del post por su ID
        Response<Comment> response = commentService.subtractLike(idComment);

        // Retorna una respuesta con el post actualizado y el estado HTTP OK (200)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

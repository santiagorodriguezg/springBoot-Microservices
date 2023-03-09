package com.rest.webservices.restfulweb.services.jpa;

import com.rest.webservices.restfulweb.services.user.User;
import com.rest.webservices.restfulweb.services.user.UserDaoService;
import com.rest.webservices.restfulweb.services.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private UserDaoService service;

    private UserRepository repository;

    public UserJpaResource(UserRepository repository){
        this.repository=repository;
    }

    //GET /users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<Optional<User>> retrieveOneUser(@PathVariable int id){
        Optional<User> user = repository.findById(id);

        if (user.isEmpty() )
            throw new UserNotFoundException("id: "+id);
        EntityModel<Optional<User>> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteOneUser(@PathVariable int id){
         repository.deleteById(id);
    }

    //POST /users
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = repository.save(user);
        //  /users/4=> /users/{id}, user.getID
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

      return ResponseEntity.created(location).build();
    }
}

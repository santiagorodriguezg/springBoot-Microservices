package com.rest.webservices.restfulweb.services.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService service;

    public UserResource(UserDaoService service){
        this.service=service;
    }

    //GET /users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveOneUser(@PathVariable int id){
        User user = service.findOne(id);

        if (    user == null)
            throw new UserNotFoundException("id: "+id);

        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteOneUser(@PathVariable int id){
         service.deleteById(id);
    }

    //POST /users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        //  /users/4=> /users/{id}, user.getID
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

      return ResponseEntity.created(location).build();
    }
}

package com.cashmanager.cashmanager.controller;


import com.cashmanager.cashmanager.exception.ResourceNotFoundException;
import com.cashmanager.cashmanager.model.User;
import com.cashmanager.cashmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody User noteDetails) {

        User user = userRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        user.setUserName(noteDetails.getUserName());
        user.setPassword(noteDetails.getPassword());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }






}

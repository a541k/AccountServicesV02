package com.telcobright.accountservicev02.controllers;

import com.telcobright.accountservicev02.dto.ResellerRelationBody;
import com.telcobright.accountservicev02.dto.UserDto;
import com.telcobright.accountservicev02.entities.User;
import com.telcobright.accountservicev02.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserCrudController {

    @Autowired
    UserCrudService userService;

    //testing
    @RequestMapping("/test01")
    String testTheController(){ return "Hello world"; }


    //create user
    @PostMapping("/user")
    ResponseEntity<String> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    //get user
    @GetMapping("/user/{userId}")
    ResponseEntity<UserDto> getUserById(@PathVariable int userId){
        return userService.getUserById(userId);
    }


    @PostMapping("/addResellerRelation")
    ResponseEntity<String> addResellerRelation(@RequestParam Integer userId, @RequestBody ResellerRelationBody relation){
        return userService.addResellerRelation(userId, relation);
    }



}

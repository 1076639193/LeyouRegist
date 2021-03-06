package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("data") String data,@PathVariable("type") Integer type){
        Boolean bol=userService.check(data,type);
        if(false==bol){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        return  ResponseEntity.ok(bol);

    }

    @PostMapping("code")
    public  ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone){
        Boolean b=userService.sendVerifyCode(phone);//136

        if(null!=b&&b){
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping("register")
    public ResponseEntity<Void> createUser(@Valid User user, @RequestParam("code") String code){
        Boolean b=userService.createUser(user,code);
        if(null!=b&&b){
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,@RequestParam("password") String password){

        User user=userService.queryUser(username,password);
        if(null!=user){
            return ResponseEntity.ok().build();

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

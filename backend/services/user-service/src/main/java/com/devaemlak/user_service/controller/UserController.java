package com.devaemlak.user_service.controller;

import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.dto.response.GenericResponse;
import com.devaemlak.user_service.dto.response.UserResponse;
import com.devaemlak.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody UserSaveRequest request){
        userService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public GenericResponse<List<UserResponse>> getAll(){
        return GenericResponse.success(userService.getAll());
    }

    @GetMapping("/{id}")
    public GenericResponse<UserResponse> getById(@PathVariable Long id){
        return GenericResponse.success(userService.getById(id));
    }
}

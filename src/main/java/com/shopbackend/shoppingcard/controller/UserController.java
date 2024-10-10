package com.shopbackend.shoppingcard.controller;

import com.shopbackend.shoppingcard.Model.User;
import com.shopbackend.shoppingcard.Request.CreateUserRequest;
import com.shopbackend.shoppingcard.Request.UserUpdateRequest;
import com.shopbackend.shoppingcard.Service.user.IUserService;
import com.shopbackend.shoppingcard.Service.user.UserService;
import com.shopbackend.shoppingcard.dto.UserDto;
import com.shopbackend.shoppingcard.exception.AlreadyExistsException;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import com.shopbackend.shoppingcard.response.ApiResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse(userDto, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

@PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse(userDto, "Success"));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse(userDto, "Success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse(null, "Delete Success"));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));        }
    }

}

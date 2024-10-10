package com.shopbackend.shoppingcard.Service.user;

import com.shopbackend.shoppingcard.Model.User;
import com.shopbackend.shoppingcard.Request.CreateUserRequest;
import com.shopbackend.shoppingcard.Request.UserUpdateRequest;
import com.shopbackend.shoppingcard.dto.UserDto;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long UserId);
    void deleteUser(Long UserId);


    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}

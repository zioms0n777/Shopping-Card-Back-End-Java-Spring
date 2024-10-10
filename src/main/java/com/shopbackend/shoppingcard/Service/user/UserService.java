package com.shopbackend.shoppingcard.Service.user;

import com.shopbackend.shoppingcard.Model.User;
import com.shopbackend.shoppingcard.Repository.UserRepository;
import com.shopbackend.shoppingcard.Request.CreateUserRequest;
import com.shopbackend.shoppingcard.Request.UserUpdateRequest;
import com.shopbackend.shoppingcard.dto.UserDto;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).filter(user-> !userRepository.existsByEmail(request.getEmail())).map(req->

                {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirst_name(request.getFirst_name());
                    user.setLast_name(request.getLast_name());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Oops!"+ request.getEmail()+ "already exists"));

    }

    @Override
    public User updateUser(UserUpdateRequest request, Long UserId) {
        return userRepository.findById(UserId).map(existingUser-> {
            existingUser.setFirst_name(request.getFirst_name());
            existingUser.setLast_name(request.getLast_name());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long UserId) {
         userRepository.findById(UserId).ifPresentOrElse(userRepository::delete, () -> System.out.println("User not found"));    }


    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);

    }

}

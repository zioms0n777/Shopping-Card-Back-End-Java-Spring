package com.shopbackend.shoppingcard.Request;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class CreateUserRequest {


    private String first_name;
    private String last_name;
    private String email;
    private String password;

}

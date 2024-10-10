package com.shopbackend.shoppingcard.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Object data;
    private String message;

}

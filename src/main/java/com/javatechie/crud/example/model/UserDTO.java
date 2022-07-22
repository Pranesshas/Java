package com.javatechie.crud.example.model;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    
public UserDTO(){}

public UserDTO (String email,Long id){
this.setEmail(email);
this.setId(id);
}

}

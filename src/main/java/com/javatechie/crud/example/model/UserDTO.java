package com.javatechie.crud.example.model;

import lombok.Data;

@Data
public class UserDTO {

    private String employment_status;
    private String employment_category;
    private String designation;
    private String email;
    private String contact_number;
    private Long userId;
    
public UserDTO(){}

public UserDTO (Long user_id,String employee_category,String designation,String email,String phone){
this.setEmail(email);
this.setUserId(user_id);
this.setContact_number(phone);
this.setDesignation(designation);
this.setEmployment_category(employment_category);
}

}

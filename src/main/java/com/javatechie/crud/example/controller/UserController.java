package com.javatechie.crud.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.javatechie.crud.example.entity.User;
import com.javatechie.crud.example.model.UserDTO;
import com.javatechie.crud.example.response.DashboardData;
import com.javatechie.crud.example.response.Metric;
import com.javatechie.crud.example.response.OperationResponse;
import com.javatechie.crud.example.response.ResponseStatusEnum;
import com.javatechie.crud.example.response.SearchResponse;
import com.javatechie.crud.example.service.UserService;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;


   
    
    
    @GetMapping(value = "/getUserDetails")   
public List<UserDTO> getUserDetails(){
    return userService.getUserDetails();
}

@GetMapping("/dashboard")
    public List<DashboardData> getDashboardData(){
        return userService.getDashboardData(0);
    }

    @GetMapping("/dashboard/metrics")
    public Metric getDashboardMetric(){
        return userService.getDashboardMetric();
    }
    
@RequestMapping("/userMap/search")
public SearchResponse getSearchResponse(@RequestBody DashboardData dashboardDTO){
return userService.getSearchResponse(dashboardDTO);
}
    
}

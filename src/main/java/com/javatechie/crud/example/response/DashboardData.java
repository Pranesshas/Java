package com.javatechie.crud.example.response;

import java.time.Instant;

import lombok.Data;

@Data
public class DashboardData {
    private Long user_id;
    private Long asset_id;
    private String name;
    private String project;
    private String make;
    private String product_type;
    private String model_no;
    private String product_number;
    private Instant created_date;
    private Instant last_modified_date;

    public DashboardData() {}

    public DashboardData(Long user_id,Long asset_id,String name, String project,String make,String product_type,String model_no,String product_number,Instant created_date,Instant last_modified_date) {
		this.setUser_id(user_id);
        this.setAsset_id(asset_id);
		this.setName(name);
        this.setMake(make);
        this.setModel_no(model_no);
        this.setProject(project);
        this.setProduct_number(product_number);
        this.setProduct_type(product_type);
        this.setCreated_date(created_date);
        this.setLast_modified_date(last_modified_date);
	}

    
}

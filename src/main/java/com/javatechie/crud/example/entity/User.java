package com.javatechie.crud.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javatechie.crud.example.model.UserDTO;
import com.javatechie.crud.example.response.DashboardData;
@SqlResultSetMapping(
		name = "ProductUserMapping",
		classes = @ConstructorResult(
					targetClass = DashboardData.class,
					columns = {
                            @ColumnResult(name="user_id", type = Long.class),
                            @ColumnResult(name="asset_id", type = Long.class),
							@ColumnResult(name="name", type = String.class),
							@ColumnResult(name="designation", type = String.class),
							@ColumnResult(name="make", type = String.class),
							@ColumnResult(name="product_type", type = String.class),
                            @ColumnResult(name="model_no", type = String.class),
                            @ColumnResult(name="product_number", type = String.class),
                            @ColumnResult(name="created_date", type = Instant.class),
                            @ColumnResult(name="last_modified_date", type = Instant.class),





				}
		)
)

@SqlResultSetMapping(
		name = "UserDetailsMapping",
		classes = @ConstructorResult(
					targetClass = UserDTO.class,
					columns = {
                            
                            @ColumnResult(name="user_id", type = Long.class),
                            @ColumnResult(name="employment_category", type = String.class),
                            @ColumnResult(name="designation", type = String.class),
                           
                            @ColumnResult(name="email", type = String.class),
                            
                                      
                            @ColumnResult(name="phone", type = String.class),
                            }
		)
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_details")

public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employment_status")
    private String employment_status;



    @Column(name = "employment_category")
    private String employment_category;

    @Column(name = "designation")
    private String designation;

    @Column(name = "personal_mail_id")
    private String personal_mail_id;

    @Column(name = "contact_number")
    private String contact_number;

    @Column(name = "employee_name_id")
    private Long employee_name_id;
    
}

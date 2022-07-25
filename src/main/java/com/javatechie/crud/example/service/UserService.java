package com.javatechie.crud.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.crud.example.entity.User;
import com.javatechie.crud.example.model.UserDTO;
import com.javatechie.crud.example.repository.ProductRepository;
import com.javatechie.crud.example.repository.UserRepository;
import com.javatechie.crud.example.response.DashboardData;
import com.javatechie.crud.example.response.Metric;

@Service
public class UserService {
    @PersistenceContext
	private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;


  
    

    public List<UserDTO> getUserDetails(){
        // return userRepository.getUserDetails();
        StringBuilder queryStr = new StringBuilder("select employee_name_id as user_id,employment_category,designation, "
        +" personal_mail_id as email,contact_number as phone from employee_details where employment_status='InService'");
         Query query = em.createNativeQuery(queryStr.toString(),"UserDetailsMapping");
         
         
         List<UserDTO> list = (List<UserDTO>)query.getResultList();
         return list;
    }

    public List<DashboardData>getDashboardData(Integer startPosition){
        StringBuilder queryStr = new StringBuilder("SELECT o.employee_name_id as user_id,i.id as asset_id,o.personal_mail_id as name, o.designation, i.make , k.asset_name as product_type,i.model_no,i.product_number, "
        +" j.created_date,j.last_modified_date FROM employee_details o JOIN map j On o.id=j.assigned_user_id "
        +" JOIN product i On i.id =  j.assigned_asset_id JOIN asset k On k.id=i.product_type where j.status=1 order by j.id desc ");

        Query query = em.createNativeQuery(queryStr.toString(),"ProductUserMapping");
		
        query.setFirstResult(startPosition);
		query.setMaxResults(10);
        List<DashboardData> list = (List<DashboardData>)query.getResultList();
        return list;

    }

    public Metric getDashboardMetric(){
        Metric metric=new Metric();
        metric.setTotalUsers(userRepository.count());
        metric.setTotalAssets(productRepository.count());
        metric.setTotalMap(userRepository.getTotalMapped());
        return metric;
       
    }
    
}

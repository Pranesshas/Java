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


    public boolean saveUser(User user) {
       
        
        if(user.getEmail() ==null){
            return false;
        }
       User newUser = this.getUserInfoByEmail(user.getEmail());
       if (newUser == null || newUser.getId() == null) {
			// This means the username is not found therfore its is returning a default
			// value of "new"
			return this.insertOrSaveUser(user);
		} else {
			return false;
		}
		
   
    }
    public User getUserInfoByEmail(String email) {
		User user = this.userRepository.findOneByEmail(email).orElseGet(() -> new User());
		return user;
	}

    public boolean insertOrSaveUser(User user) {
		this.userRepository.save(user);
		return true;
	}

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUsersById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // public User getProductByName(String name) {
    //     return userRepository.
    // }

    public List<String> getProjects(){
        return userRepository.getProjects();
    }

    public List<UserDTO> getUserDetails(){
        // return userRepository.getUserDetails();
        StringBuilder queryStr = new StringBuilder("Select personal_mail_id as email,employee_name_id as user_id " 
        + " from employee_details where employment_status='InService'");
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

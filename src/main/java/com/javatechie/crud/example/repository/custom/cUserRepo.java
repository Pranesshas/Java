package com.javatechie.crud.example.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javatechie.crud.example.model.UserDTO;

@Repository
public class cUserRepo {
    
    @PersistenceContext
	private EntityManager em;


    
   

    public UserDTO findbyEmpId(Long userId){
        StringBuilder queryStr=new StringBuilder("select employee_name_id as user_id,employment_category,designation, "
        +" personal_mail_id as email,contact_number as phone from employee_details  where employee_name_id=:userId");

        Query query = em.createNativeQuery(queryStr.toString(),"UserDetailsMapping");
    
        query.setParameter("userId",userId);
       
        UserDTO user=(UserDTO) query.getSingleResult();
        return user;
    }
}

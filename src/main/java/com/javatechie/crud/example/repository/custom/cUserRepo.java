package com.javatechie.crud.example.repository.custom;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javatechie.crud.example.model.UserDTO;
import com.javatechie.crud.example.response.DashboardData;
import com.javatechie.crud.example.response.SearchResponse;

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

    public SearchResponse getSearchResponse(DashboardData dashboardDTO){

        SearchResponse resp= new SearchResponse();

        StringBuilder queryStr = new StringBuilder("SELECT o.employee_name_id as user_id,i.id as asset_id,o.personal_mail_id as name, o.designation, i.make , k.asset_name as product_type,i.model_no,i.product_number, "
        +" j.created_date,j.last_modified_date FROM employee_details o JOIN map j On o.employee_name_id=j.assigned_user_id "
        +" JOIN product i On i.id =  j.assigned_asset_id JOIN asset k On k.id=i.product_type where j.status=1 ");

        StringBuilder countQuery = new StringBuilder("SELECT count(o.employee_name_id) FROM employee_details o JOIN map j On o.employee_name_id=j.assigned_user_id "
        +" JOIN product i On i.id =  j.assigned_asset_id JOIN asset k On k.id=i.product_type where j.status=1 ");
        
        
        if(dashboardDTO.getUser_id()!=null && !dashboardDTO.getUser_id().equals(0L) ){
            queryStr.append(" and o.employee_name_id= :userId ");
            countQuery.append(" and o.employee_name_id= :userId ");
        }

        if(dashboardDTO.getAsset_id()!=null && dashboardDTO.getAsset_id().equals(0L)){
            queryStr.append(" and i.id= :assetId ");
            countQuery.append(" and i.id= :assetId ");
        }

        if(dashboardDTO.getName()!=null && !dashboardDTO.getName().equalsIgnoreCase("")){
            queryStr.append(" and  lower(o.personal_mail_id) Like :name ");
            countQuery.append(" and  lower(o.personal_mail_id) Like :name ");
        }

        if(dashboardDTO.getProject()!=null && !dashboardDTO.getProject().equalsIgnoreCase("")){
            queryStr.append("  and lower(o.designation) Like :designation ");
            countQuery.append("  and lower(o.designation) Like :designation ");
        }

        if(dashboardDTO.getMake()!=null && !dashboardDTO.getMake().equalsIgnoreCase("")){
            queryStr.append("  and lower(i.make) Like :make ");
            countQuery.append("  and lower(i.make) Like :make ");
        }

        if(dashboardDTO.getProduct_type()!=null && !dashboardDTO.getProduct_type().equalsIgnoreCase("")){
            queryStr.append("  and lower(k.asset_name) Like :productType ");
            countQuery.append("  and lower(k.asset_name) Like :productType ");
        }

        if(dashboardDTO.getModel_no()!=null && !dashboardDTO.getModel_no().equalsIgnoreCase("")){
            queryStr.append("  and lower(i.model_no) Like :modelNo ");
            countQuery.append("  and lower(i.model_no) Like :modelNo ");
        }

        int num = dashboardDTO.getPage_number()*dashboardDTO.getPage_size();
        queryStr.append(" ORDER BY j.id desc limit "+dashboardDTO.getPage_size()+" offset  "+num);
		countQuery.append(" ORDER BY j.id desc ");

        Query query = em.createNativeQuery(queryStr.toString(),"ProductUserMapping");

		Query counterQuery = em.createNativeQuery(countQuery.toString());

        if(dashboardDTO.getUser_id()!=null && !dashboardDTO.getUser_id().equals(0L) ){
            query.setParameter("userId", dashboardDTO.getUser_id());
            counterQuery.setParameter("userId", dashboardDTO.getUser_id());

        }

        if(dashboardDTO.getAsset_id()!=null && !dashboardDTO.getAsset_id().equals(0L)){
            query.setParameter("assetId", dashboardDTO.getAsset_id());
            counterQuery.setParameter("assetId", dashboardDTO.getAsset_id());

        }

        if(dashboardDTO.getName()!=null && !dashboardDTO.getName().equalsIgnoreCase("")){
            query.setParameter("name","%"+ dashboardDTO.getName().trim()+"%");
            counterQuery.setParameter("name", "%"+dashboardDTO.getName() +"%");

        }

        if(dashboardDTO.getProject()!=null && !dashboardDTO.getProject().equalsIgnoreCase("")){
            query.setParameter("designation", "%"+dashboardDTO.getProject().trim()+"%");
            counterQuery.setParameter("designation", "%"+dashboardDTO.getProject().trim()+"%");

        }

        if(dashboardDTO.getMake()!=null && !dashboardDTO.getMake().equalsIgnoreCase("")){
            query.setParameter("make", "%"+dashboardDTO.getMake().trim()+"%");
            counterQuery.setParameter("make", "%"+dashboardDTO.getMake().trim()+"%");

        }

        if(dashboardDTO.getProduct_type()!=null && !dashboardDTO.getProduct_type().equalsIgnoreCase("")){
            query.setParameter("productType", "%"+dashboardDTO.getProduct_type().trim()+"%");
            counterQuery.setParameter("productType", "%"+dashboardDTO.getProduct_type().trim()+"%");

        }

        if(dashboardDTO.getModel_no()!=null && !dashboardDTO.getModel_no().equalsIgnoreCase("")){
            query.setParameter("modelNo", "%"+dashboardDTO.getModel_no().trim()+"%");
            counterQuery.setParameter("modelNo", "%"+dashboardDTO.getModel_no().trim()+"%");

        }

        List<DashboardData> mapList=query.getResultList();
        BigInteger countResults = (BigInteger) counterQuery.getSingleResult();

        resp.setMapList(mapList);
        resp.setAssetCount(countResults);
         return resp;
        
}
}

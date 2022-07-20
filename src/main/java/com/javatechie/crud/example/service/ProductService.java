package com.javatechie.crud.example.service;

import com.javatechie.crud.example.entity.Asset;
import com.javatechie.crud.example.entity.Map;
import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.model.ProductDTO;
import com.javatechie.crud.example.repository.AssetRepository;
import com.javatechie.crud.example.repository.MapRepository;
import com.javatechie.crud.example.repository.ProductRepository;
import com.javatechie.crud.example.repository.UserRepository;
import com.javatechie.crud.example.repository.custom.cProductRepo;
import com.javatechie.crud.example.response.OperationResponse;
import com.javatechie.crud.example.response.ResponseStatusEnum;
import com.javatechie.crud.example.response.SearchResponse;
import com.javatechie.crud.example.response.UserAssetMap;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private cProductRepo cproductRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AssetRepository assetRepo;

    public OperationResponse saveProduct(ProductDTO productDTO) {
        // return repository.save(product);
        
        
        OperationResponse resp = new OperationResponse();

        try{
            Asset asset=assetRepo.getOne(productDTO.getProduct_type());
        if(asset!=null){
            Product product=new Product();
            
            if(productDTO.getCd_rom()!=null){
            product.setCd_rom(productDTO.getCd_rom().trim());
            }
            
            if(productDTO.getHdd()!=null){
            product.setHdd(productDTO.getHdd().trim());
            }

            if(productDTO.getLaptop_number()!=null){
            product.setLaptop_number(productDTO.getLaptop_number().trim());
            }

            if(productDTO.getMake()!=null){
            product.setMake(productDTO.getMake().trim());
            }

            if(productDTO.getModel_no()!=null){
            product.setModel_no(productDTO.getModel_no().trim());
            }

            if(productDTO.getOs()!=null){
            product.setOs(productDTO.getOs().trim());
            }

            if(productDTO.getProcessor()!=null){
            product.setProcessor(productDTO.getProcessor().trim());
            }

            if(productDTO.getProduct_number()!=null){
            product.setProduct_number(productDTO.getProduct_number().trim());
            }

          

            if(productDTO.getRam()!=null){
            product.setRam(productDTO.getRam().trim());
            }

            if(productDTO.getNote()!=null){
            product.setNote(productDTO.getNote().trim());
            }

            if(productDTO.getAsset_number()!=null){
            product.setAsset_number(productDTO.getAsset_number().trim());
            }

            product.setProduct_type(asset);
            product.setAsset_date(productDTO.getAsset_date());
           
            repository.save(product);
            
            resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("Asset Added successfully");

            
        } 
    } catch(Exception e){
        resp.setOperationStatus(ResponseStatusEnum.ERROR);
        resp.setOperationMessage(e.toString());
    }
        
        return resp;
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public UserAssetMap getProductById(Long assetId) {
        Product product=repository.findById(assetId).orElse(null);
        UserAssetMap userAssets= new UserAssetMap();
        userAssets.setProduct(product);
        if(product!=null){
            if(!product.is_available()){
               userAssets.setUser(userRepo.getUserDetailsPerAssets(assetId));
                }
            
            }

            return userAssets;

        
        
    }

    // public Product getProductByName(String name) {
    //     return repository.findByName(name);
    // }

    // public String deleteProduct(int id) {
    //     repository.deleteById(id);
    //     return "product removed !! " + id;
    // }

    public List<Product> getAvailableProducts(){
        return repository.getAllAssets();
    }
    // public Product updateProduct(Product product) {
    //     Product existingProduct = repository.findById(product.getId()).orElse(null);
    //     existingProduct.setName(product.getName());
    //     existingProduct.setQuantity(product.getQuantity());
    //     existingProduct.setPrice(product.getPrice());
    //     return repository.save(existingProduct);
    // }

    public Product getProductbyId(Long assetId){
        return repository.findById(assetId).get();
    }

    public boolean deleteAsset(Long asset_id){
        Product product=repository.findById(asset_id).orElse(null);
        if(product.is_available()){
            product.set_active(false);
            repository.save(product);
            return true;
        }

        return false;

    }

    public SearchResponse searchUsersPagination(ProductDTO productDTO,Integer startPosition){

        return cproductRepo.getAllUsersPagination(productDTO, startPosition);

    }

    public UserAssetMap getAssetsByUserId(Long userId) {
        List<Product> product=repository.getUserDetailsPerAssets(userId);
        UserAssetMap userAssets= new UserAssetMap();
        userAssets.setProductList(product);
        userAssets.setUser(userRepo.findById(userId));

        

            return userAssets;

        
        
    }

}

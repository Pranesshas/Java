package com.javatechie.crud.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.model.ProductDTO;
import com.javatechie.crud.example.response.OperationResponse;
import com.javatechie.crud.example.response.ResponseStatusEnum;
import com.javatechie.crud.example.response.SearchResponse;
import com.javatechie.crud.example.response.UserAssetMap;
import com.javatechie.crud.example.service.ProductService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private Environment env;

    @PostMapping("/addAssets")
    public OperationResponse addProduct(@RequestParam(value = "file", required = false) MultipartFile file,
    @RequestParam("assetDetails") String assetDetails) throws IOException {
       
        OperationResponse resp = new OperationResponse();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());   
        ProductDTO productDTO=mapper.readValue(assetDetails, ProductDTO.class);
        
        

        if (file!= null) {
			// get Original file name
			String fileName = file.getOriginalFilename();

			// set image name like : 2018_10_26_18_34_33.png(yyy-MM-dd-HH-mm-ss)
			String modifiedFileName = FilenameUtils.getBaseName(fileName) +"."
					+ FilenameUtils.getExtension(fileName);
           productDTO.setPurchase_document(modifiedFileName);
			String assetUploadDirPath = env.getProperty("asset.upload.dir.path");
			String dirPath = assetUploadDirPath + File.separator + "purchase_document" + File.separator;
			File serverFile = new File(dirPath + modifiedFileName);
			FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
            
        }
        resp =  service.saveProduct(productDTO);
        
		
		return resp;
    }

    @DeleteMapping("/delete/{asset_id}")
    public OperationResponse deleteAsset(@PathVariable Long asset_id) {
        
        boolean assetDeleteSuccess= service.deleteAsset(asset_id);
        OperationResponse resp = new OperationResponse();
		if (assetDeleteSuccess == true) {
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
			resp.setOperationMessage("Asset Deleted");
		} else {
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
			resp.setOperationMessage("Unable to delete Asset");
		}
		return resp;
    }


    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return service.saveProducts(products);
    }

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return service.getProducts();
    }

    // @GetMapping("/productById/{id}")
    // public Product findProductById(@PathVariable int id) {
    //     return service.getProductById(id);
    // }

    @GetMapping("/available")
    public List<Product> findAvailableProduct() {
        return service.getAvailableProducts();
    }
    // @GetMapping("/product/{name}")
    // public Product findProductByName(@PathVariable String name) {
    //     return service.getProductByName(name);
    // }
    @RequestMapping(value = "/asset/search")
    public SearchResponse searchUsersPagination(@RequestBody ProductDTO productDTO) {
        return service.searchUsersPagination(productDTO);
    }

    //properly working stuff
    // @GetMapping(value = "/get/{assetId}")   
    // public Product getProductById(@PathVariable("assetId") Long assetId){
    //     return service.getProductById(assetId);
    // }
    // @PutMapping("/update")
    // public Product updateProduct(@RequestBody Product product) {
    //     return service.updateProduct(product);
    // }

    // @DeleteMapping("/delete/{id}")
    // public String deleteProduct(@PathVariable Long id) {
    //     return service.deleteProduct(id);
    // }



    @GetMapping(value = "/get/{assetId}")   
    public UserAssetMap getProductById(@PathVariable("assetId") Long assetId){
        return service.getProductById(assetId);
    }


    @GetMapping(value = "/getAssets/{userId}")   
    public UserAssetMap getAssetsByUserId(@PathVariable("userId") Long userId){
        return service.getAssetsByUserId(userId);
    }


    
}

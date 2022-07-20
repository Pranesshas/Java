package com.javatechie.crud.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.crud.example.entity.Asset;
import com.javatechie.crud.example.model.AssetDTO;
import com.javatechie.crud.example.repository.AssetRepository;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepo;


    public boolean saveAssetType(AssetDTO assetDTO){
        Asset asset=new Asset();
        Long check=assetRepo.findByAsset(assetDTO.getAsset_name());
        if(check==0){
        asset.setName(assetDTO.getAsset_name());
        if(assetDTO.getIs_confi()){
        asset.set_configuration(assetDTO.getIs_confi());
        } else {
            asset.set_configuration(false);
        }
        assetRepo.save(asset);
        return true;
        }
        return false;
    }

    public List<Asset> getAssetType(){
        List<Asset> list=new ArrayList<Asset>();
        list=assetRepo.findAll();
        return list;
    }
    
}

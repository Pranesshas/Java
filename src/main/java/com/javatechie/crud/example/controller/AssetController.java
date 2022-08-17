package com.javatechie.crud.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.crud.example.entity.Asset;
import com.javatechie.crud.example.model.AssetDTO;
import com.javatechie.crud.example.service.AssetService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/api")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping("/saveAssetType")
    public boolean saveAssetType(@RequestBody AssetDTO assetDTO){
        return assetService.saveAssetType(assetDTO);

    }

    @GetMapping("/getAssetTypes")
    public List<Asset> getAssetTypes(){
        return assetService.getAssetType();
    }

    }

    


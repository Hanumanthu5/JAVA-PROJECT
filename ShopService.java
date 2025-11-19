package com.example.shopproject.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopproject.model.Shop;
import com.example.shopproject.repo.ShopRepo;

@Service

public class ShopService {
	@Autowired
	private ShopRepo sr;
	
	//insert or Add shop
	public Shop addShop(Shop shop) {
		return sr.save(shop);
	}
	
	//Retrieve all shops
	public List<Shop> getShops(){
		return sr.findAll();
	}
	
	// Retrieve specific shop by ID
	public Shop getShopById(int id) {
	    return sr.findById(id).orElse(null);
	}

	
	//Update shop
	public Shop updateShop(int shopId,Shop shopDetails) {
		Shop s=sr.findById(shopId).orElse(null);
		
		if(s!=null) {
		s.setShopCategory(shopDetails.getShopCategory());
		s.setShopEmployeeId(shopDetails.getShopEmployeeId());
		s.setShopName(shopDetails.getShopName());
		s.setCustomers(shopDetails.getCustomers());
		s.setShopStatus(shopDetails.getShopStatus());
		s.setShopOwner(shopDetails.getShopOwner());
		s.setLeaseStatus(shopDetails.getLeaseStatus());
		return sr.save(s);
		}
		else {
			System.out.println("Shop not found with ID:"+shopId);
		}
		return null;
	}
	
	//delete shop
	public void deleteShop(int shopId) {
		sr.deleteById(shopId);
	}


}

package com.example.shopproject.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopproject.model.Shop;
import com.example.shopproject.service.ShopService;

@RestController


public class ShopController {@Autowired
	private ShopService ss;
	
	//Insert or add shop
	@PostMapping("/addshop")
	public Shop addShop(@RequestBody Shop shop) {
		return ss.addShop(shop);
	}
	
	//Retrieve all shops
	@GetMapping("/all")
	public List<Shop> getShops(){
		return ss.getShops();
	}
	
	// Retrieve specific shop by ID
	@GetMapping("/shop/{id}")
	public Shop getShopById(@PathVariable int id) {
		return ss.getShopById(id);
	}
	
	//Update shop by id
	@PutMapping("/update/{id}")
	public Shop updateShop(@PathVariable int id,@RequestBody Shop shopDetails) {
		return ss.updateShop(id, shopDetails);
	}
	
	//Delete shop by ID
	@DeleteMapping("/delete/{id}")
	public void deleteShop(@PathVariable int id) {
		ss.deleteShop(id);
	}


}

package com.example.shopproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopproject.model.Shop;

public interface ShopRepo extends JpaRepository<Shop,Integer>{
}

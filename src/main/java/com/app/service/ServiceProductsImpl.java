package com.app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.app.entity.Product;
import com.app.scraping.ScrapingImpl;
import org.json.JSONObject;

@Service

public class ServiceProductsImpl implements ServiceProducts {

	@Autowired
	private ScrapingImpl scraping;

	

	@Override
 @Cacheable(value="product",key="#id")
	public String getProduct(int id) {
		System.out.println("TEST getProduct(int id)*************)");
		for (Product product : scraping.getAllProducts()) {
			if (product.getId() == id)
				return product.toString();
		}
		return  "Product with id'{{" + id + "}}' was not found";
	}
 @CacheEvict(value="product",key="#id")
	public void cacheProductEvict (int id) {
		 
	}
}
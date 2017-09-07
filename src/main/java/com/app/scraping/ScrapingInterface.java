package com.app.scraping;

import java.util.List;

import com.app.entity.Product;

public interface ScrapingInterface {
	public String getTextUser();
	public String getTextProduct();
	public String getTextPurchasesByUser();
	public List<Product> getAllProducts();
	
}

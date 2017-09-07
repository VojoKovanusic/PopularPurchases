package com.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.app.entity.PopularPurchases;
import com.app.entity.Product;
import com.app.entity.Purchas;
import com.app.scraping.ScrapingInterface;

@Component
public class ServicePopularPurchasesImpl implements ServicePopularPurchases {

	@Autowired
	private ServicePurchasesByUser userService;
	@Autowired
	private ScrapingInterface scraping;

	@Override
	@Cacheable("popular")
	public ArrayList<PopularPurchases> popular() {
		
		List<Product> products = scraping.getAllProducts();

		ArrayList<PopularPurchases> popularList = new ArrayList<>();

		for (Product product : products) {
			popularList.add(
					new PopularPurchases(product.getId(), product.getFace(), product.getPrice(), product.getSize()));
		}

		for (PopularPurchases popularPurchases : popularList) {
			for (Purchas purchas : userService.getAllPurchases()) {
				if (popularPurchases.getId().equals(purchas.getProductId()))
					popularPurchases.getRecent().add(purchas.getUsername());
			}
		}

		Collections.sort(popularList);
		return popularList;
	}

	@CacheEvict("popular")
	public void cacheEvictAccounts() {

	}
}

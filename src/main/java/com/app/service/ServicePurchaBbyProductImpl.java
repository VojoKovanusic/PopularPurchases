package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.app.entity.Purchas;

/*	 for each of those products, get a list of all people who previously 
	purchased that product: GET /api/purchases/by_product/:product_id*/

@Service
public class ServicePurchaBbyProductImpl implements ServicePurchaBbyProduct {

	@Autowired
	private ServicePurchasesByUser servicePurchasesByUser;

	@Override
	@Cacheable(value = "ppp", key = "#productId")
	public List<Purchas> peopleWhoPreviouslyPurchasedProduct(int productId) {
		System.out.println("TEST @Cacheable(value = \"ppp\", key = \"#productId\"*************)");
		try {
			ArrayList<Purchas> purchase = new ArrayList<>();

			for (Purchas purchas : servicePurchasesByUser.getAllPurchases()) {

				if (purchas.getProductId() == (productId)) {
					purchase.add(purchas);
				}
			}

			return purchase;
		} catch (MethodArgumentTypeMismatchException ex) {
			return null;
		}
	}

	@CacheEvict(value = "ppp", key = "#productId")
	public void cacheEvict(String productId) {

	}
}

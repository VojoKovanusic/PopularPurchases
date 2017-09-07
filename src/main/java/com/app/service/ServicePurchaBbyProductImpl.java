package com.app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.app.entity.Purchas;

@Service
public class ServicePurchaBbyProductImpl implements ServicePurchaBbyProduct {

	@Autowired
	private ServicePurchasesByUser servicePurchasesByUser;

	@Override
	@Cacheable(value = "ppp", key = "#productId")
	public List<Purchas> peopleWhoPreviouslyPurchasedProduct(int productId) {
		System.out.println("*************Test cache*******peopleWhoPreviouslyPurchasedProduct(int productId)");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}		
		ArrayList<Purchas> purchase = new ArrayList<>();

			for (Purchas purchas : servicePurchasesByUser.getAllPurchases()) {

				if (purchas.getProductId() == (productId)) {
					purchase.add(purchas);
				}
			}

			return purchase;
	}

	@CacheEvict(value = "ppp", key = "#productId")
	public void cacheEvict(String productId) {

	}
}

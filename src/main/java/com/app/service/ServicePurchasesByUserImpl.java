package com.app.service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.app.entity.Purchas;
import com.app.json.JsonUtil;
import com.app.scraping.ScrapingImpl;

@Service
public class ServicePurchasesByUserImpl implements ServicePurchasesByUser {

	@Autowired
	private ScrapingImpl scraping;

	@Override
	public List<Purchas> getAllPurchases() {

		List<Purchas> listOfPurchases = new ArrayList<>();
		String text = scraping.getTextPurchasesByUser().replace("]}{\"purchases\":[", ",");

		try {
			JSONObject jsonObject = new JSONObject(text);
			JSONArray purchases = (JSONArray) jsonObject.get("purchases");
			int i = 0;
			while (true) {

				++i;
				JSONObject jsonObj = purchases.getJSONObject(i);
				int id = jsonObj.getInt("id");
				String username = jsonObj.getString("username");
				Long productId = jsonObj.getLong("productId");
				String date = jsonObj.getString("date");
				if (date == null)
					break;
				listOfPurchases.add(new Purchas(id, username, productId, date));

			}
		} catch (Exception e) {
			e.getMessage();
		}

		return listOfPurchases;
	}

	@Override
	@Cacheable(value = "purchasesByUsername", key = "#username")
	public ArrayList<String> getPurchasesByUsername(String username) {

		System.out.println("*************Test cache*******getPurchasesByUsername(String username)");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		ArrayList<Purchas> listpurchase = new ArrayList<>();

		for (Purchas purchas : getAllPurchases()) {

			if (purchas.getUsername().equals(username)) {
				listpurchase.add(purchas);
			}
		}

		return convertJavaObjectToJson(listpurchase);
	}

	@Override
	public ArrayList<Purchas> getFiveRecentPurchases(ArrayList<Purchas> listpurchase) {

		ArrayList<Purchas> recentFive = new ArrayList<>();

		Collections.sort(listpurchase);

		if (listpurchase.size() <= 5)
			return listpurchase;
		for (int i = 0; i < 5; i++) {
			recentFive.add(listpurchase.get(i));
		}
		return recentFive;
	}

	private ArrayList<String> convertJavaObjectToJson(ArrayList<Purchas> listpurchase) {
		ArrayList<String> jsonList = new ArrayList<>();
		for (Purchas purchas : getFiveRecentPurchases(listpurchase)) {
			String jsonFormat = JsonUtil.convertJavaToJSON(purchas);
			jsonList.add(jsonFormat);
		}
		return jsonList;
	}
	
	

	@CacheEvict(value = "purchasesByUsername", key = "#username")
	public void cacheEvict(String username) {

	}
}
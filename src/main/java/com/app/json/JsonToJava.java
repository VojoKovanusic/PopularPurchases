package com.app.json;

import java.io.IOException;
import java.util.ArrayList;
 
import java.util.List;
 

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import com.app.entity.Purchas;
import com.app.entity.PurchaseWrapper;
import com.app.entity.User;
 
import com.app.service.ServiceUser;
import com.app.service.ServiceUserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
 
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class JsonToJava   {

private static final String URL = "http://localhost:8000/api/purchases/by_user/";
	 
	public  ArrayList<Purchas> getPurchasesByName(String userName) throws IOException {
		
		PurchaseWrapper purchaseWrapper = getPurchaseWrapper(userName);
		ArrayList<Purchas> listOfPurchases = new ArrayList<>();

		Purchas[] purchases = purchaseWrapper.getPurchas();

		for (Purchas purchase : purchases) {
			listOfPurchases.add(purchase);
		}
		return listOfPurchases;
	}
	

	public PurchaseWrapper getPurchaseWrapper(String userName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		 
		PurchaseWrapper purchaseWrapper = mapper.readValue(Utill.getData(Utill.getUrl(URL + userName)),
				PurchaseWrapper.class);

		return purchaseWrapper;
	}
	
}

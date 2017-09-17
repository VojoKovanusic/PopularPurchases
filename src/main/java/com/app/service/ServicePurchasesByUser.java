package com.app.service;

import java.util.ArrayList;
import java.util.List;
 
import com.app.entity.Purchas;
 

public interface ServicePurchasesByUser {
	
	ArrayList<Purchas> getFiveRecentPurchases(ArrayList<Purchas> list);	
	public List<Purchas>  getAllPurchases();
 
	public ArrayList<String> getPurchasesByUsername(String username);
	 
}

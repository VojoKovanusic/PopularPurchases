package com.app.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.app.entity.PopularPurchases;

@Component
public class ServiceOtherUsersWhoRecentlyPurchased {
 
	@Autowired
	private ServicePopularPurchases popularService;
	 
	@Cacheable(value="popularP", key="#username")
	public ArrayList<PopularPurchases> usersWhoRecentlyPurchased(String username) {
	try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		 
		//list of all users, producst and purchases  
		 ArrayList<PopularPurchases> all= popularService.popular();
		 
		  
		 ArrayList<PopularPurchases> sameProduct=new ArrayList<>();
		 
		  
		 
		 for (PopularPurchases objFromAll : all) {
			 if(isContainsUsernameBy(objFromAll.getRecent(),username)) {
				 objFromAll.getRecent().remove(username);
				 
			 sameProduct.add(objFromAll);}
		}
		
		return sameProduct;
		
	}
	private boolean isContainsUsernameBy(ArrayList<String> names,String username) {
		
		for (String name : names) {
			if (name.equals(username))
				return true;	
		}
		return false;
	}
	@CacheEvict(value="popularP", key="#username")
	public void evict(String username) {
		
	}
}

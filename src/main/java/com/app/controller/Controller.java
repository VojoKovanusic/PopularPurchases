package com.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
import com.app.entity.User;
import com.app.scraping.ScrapingInterface;
import com.app.service.ServiceOtherUsersWhoRecentlyPurchased;
import com.app.service.ServicePopularPurchases;
import com.app.service.ServiceProducts;
import com.app.service.ServicePurchaBbyProduct;
import com.app.service.ServicePurchasesByUser;
import com.app.service.ServiceUser;
import com.app.json.JsonToJava;

@RestController
public class Controller {

	@Autowired
	private ServicePurchasesByUser servicePurchasesByUser;
	@Autowired
	private ServicePurchaBbyProduct purchasesByProduct;
	@Autowired
	private ServiceUser serviceUser;
	@Autowired
	private ServiceProducts serviceProduct;
	@Autowired
	private ServicePopularPurchases popularService;
	@Autowired
	private ServiceOtherUsersWhoRecentlyPurchased service;
	@Autowired
	private ScrapingInterface scraping;
	
	
	
	// fetch 5 recent purchases for the user, oreder by date
	
	@RequestMapping(path = "api/purchases/by_user/{username:.+}", method = RequestMethod.GET)
	public String allPurchases(@PathVariable String username) throws IOException {

		ArrayList<String> listpurchase = servicePurchasesByUser.getPurchasesByUsername(username);
		
		if (listpurchase.isEmpty())
			return "User with username of '{{" + username + "}}' was not found";
		return "{\"purchasesByUser\":" + listpurchase.toString() + "}";

	}

	// list of all people who previously purchased that product
	@RequestMapping(path = "api/purchases/by_product/{product_id}", method = RequestMethod.GET)
	public String peopleWhoPreviouslyPurchasedProduct(@PathVariable String product_id) {
		// nisam uspio sa Exceptionima uhvatiti ilegalan unos...pa sam pjeske
		if (isNumber(product_id))
			return "Entered  id'{{" + product_id + "}}' was not found, because is not in number format";

		int id = Integer.parseInt(product_id);

		List<String> listpurchase = purchasesByProduct.peopleWhoPreviouslyPurchasedProduct(id);
		if (listpurchase.isEmpty())
			return "Purchase with  product id'{{" + id + "}}' was not found";
		return "{\"purchasesByProductId\":" + listpurchase.toString() + "}";

	}

	// info about the products
	@RequestMapping(path = "api/products/{product_id}", method = RequestMethod.GET)
	public String getProductByID(@PathVariable String product_id) {

		// nisam uspio sa Exceptionima uhvatiti ilegalan unos...pa sam pjeske
		if (isNumber(product_id))
			return "Entered  id'{{" + product_id + "}}' was not found, because is not in number format ";

		int id = Integer.parseInt(product_id);

		return "{\"product\":" + serviceProduct.getProduct(id) + "}";

	}
	 
	@RequestMapping(path = "/api/recent_purchases/{username:.+}", method = RequestMethod.GET)
	public String recentPurchasesByUsername(@PathVariable String username) {

		if (service.usersWhoRecentlyPurchased(username).isEmpty()) {
			return "Try with another user, \"" + username + "\" did not buy anything.";
		}

		return "{\"recentPurchases\":" + service.usersWhoRecentlyPurchased(username).toString() + "}";
	}

	// testini kontroleri:
	//DOBAR
	@RequestMapping("scr/prchases")
	public String getAllUsers() {
		return scraping.getTextPurchasesByUser();
	//	return serviceUser.getUsers();

	}
	// testini kontroleri:
		@RequestMapping("/prchasesNemanja")
		public String scrgetAllUsers() throws IOException {
			String result="";
			for (User user : serviceUser.getUsers()) {	 
				result+=new JsonToJava().getPurchasesByName(user.getUsername());
		}
			return result;}
		 
		 
		
				 
//validacija unosa
	private boolean isNumber(String id) {
		char ch[] = id.toCharArray();
		for (char c : ch) {
			if (!Character.isDigit(c))
				return true;
		}
		return false;
	}
}

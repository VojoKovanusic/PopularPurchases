package com.app.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.app.entity.PopularPurchases;
import com.app.entity.Product;
import com.app.entity.Purchas;
import com.app.entity.User;
import com.app.service.ServiceOtherUsersWhoRecentlyPurchased;
import com.app.service.ServicePopularPurchases;
import com.app.service.ServiceProducts;
import com.app.service.ServicePurchaBbyProduct;
import com.app.service.ServicePurchasesByUser;
import com.app.service.ServiceUser;

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
	
	@RequestMapping(path = "/api/recent_purchases/{username:.+}", method = RequestMethod.GET)
	public String recentPurchasesByUsername(@PathVariable String username) {
		
		if (service.usersWhoRecentlyPurchased(username).isEmpty()) {
			return "Try with another user, \"" + username + "\" did not buy anything.";
		}

		return "{ \"purchases\":"+service.usersWhoRecentlyPurchased(username).toString()+"}";
	}

	@RequestMapping(path = "api/purchases/by_user/{username:.+}", method = RequestMethod.GET)
	public String allPurchases(@PathVariable String username) {

		ArrayList<String> listpurchase = servicePurchasesByUser.getPurchasesByUsername(username);

		if (listpurchase.isEmpty())
			return "User with username of '{{" + username + "}}' was not found";
		return "{ \"usernames\":"+listpurchase.toString()+"}";
		
	}

	@RequestMapping(path = "api/purchases/by_product/{product_id}", method = RequestMethod.GET)
	public String peopleWhoPreviouslyPurchasedProduct(@PathVariable String product_id) {
		//nisam uspio sa Exceptionima uhvatiti ilegalan unos...pa sam pjeske
		if (validation(product_id))
			return "Entered  id'{{" + product_id + "}}' was not found, because is not in number format";

		int id = Integer.parseInt(product_id);

		List<String> listpurchase = purchasesByProduct.peopleWhoPreviouslyPurchasedProduct(id);
		if (listpurchase.isEmpty())
			return "Purchase with  product id'{{" + id + "}}' was not found";
		return "{ \"products\":"+listpurchase.toString()+"}";

	}

	@RequestMapping(path = "api/products/{stringId}", method = RequestMethod.GET)
	public String getProductByID(@PathVariable String stringId) {
		
		//nisam uspio sa Exceptionima uhvatiti ilegalan unos...pa sam pjeske
		if (validation(stringId))
			return "Entered  id'{{" + stringId + "}}' was not found, because is not in number format ";
		
		int id = Integer.parseInt(stringId);
		
		return serviceProduct.getProduct(id);

	}

	// testini kontroleri:
	@RequestMapping("api/users")
	public List<User> getAllUsers() {
		return serviceUser.getUsers();

	}
	@RequestMapping("/test")
	public String test() {
		return "serviceUser.getUsers()";

	}

	@RequestMapping("api/products")
	public List<Product> getAllProducts() {
//nije dobar poziv metode....
		return  getAllProducts();

	}

	// for test
	@RequestMapping("popular")
	public ArrayList<PopularPurchases> popular() {
		return  popularService.popular();
	}
	
	
	private boolean validation(String string) {
		char ch[] = string.toCharArray();
		for (char c : ch) {
			if (!Character.isDigit(c))
				return true;
		}
		return false;
	}
}

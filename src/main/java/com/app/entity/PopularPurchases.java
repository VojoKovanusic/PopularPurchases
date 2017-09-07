package com.app.entity;

import java.util.ArrayList;

public class PopularPurchases implements Comparable<PopularPurchases> {

	private Long id;
	private String face;
	private double price;
	private int size;

	private ArrayList<String> recent;

	public PopularPurchases(Long id, String face, double price, int size) {

		this.id = id;
		this.face = face;
		this.price = price;
		this.size = size;
		recent = new ArrayList<>();
	}

	public PopularPurchases() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<String> getRecent() {
		return recent;
	}

	public void setRecent(ArrayList<String> recent) {
		this.recent = recent;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", face=" + face + ", price=" + price + ", size=" + size + ", recent="
				+ recent + "]";
	}

	@Override
	public int compareTo(PopularPurchases o) {
		Integer recent = getRecent().size();
		Integer recentNew = o.getRecent().size();
		return recentNew.compareTo(recent);
	}

}

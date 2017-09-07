package com.app.entity;

public class Product {

	private long id;
	private String face;
	private double price;
	private int size;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Product(long id, String face, double price, int size) {
		super();
		this.id = id;
		this.face = face;
		this.price = price;
		this.size = size;
	}

	public Product() {

	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", face=" + face + ", price=" + price + ", size=" + size + "]";
	}

}

package com.mzherdev.salesavingsystem.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = -5461065208725881504L;

	public Product() {
	}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	@NotEmpty
	private String name;

	@Column
	@NotNull
	private double price;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	@OrderBy("id DESC")
	private Set<OrderItem> items = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	private Set<Discount> discounts = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}

	public boolean isNew() {
		return this.id == 0;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", price=" + price +
				'}';
	}
}

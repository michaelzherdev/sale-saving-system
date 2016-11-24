package com.mzherdev.salesavingsystem.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

	private static final long serialVersionUID = -5461065208725881504L;

	public Product() {
	}

	//for test mock
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public Product(int id, String name, double price) {
		this(name, price);
		this.id = id;
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
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<OrderItem> items = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Discount> discounts = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name.trim();
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

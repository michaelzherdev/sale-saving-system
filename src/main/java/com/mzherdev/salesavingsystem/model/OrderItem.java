package com.mzherdev.salesavingsystem.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "order_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 2532611949971908819L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column
	@NotNull
	private int quantity;

	@Column
	private BigDecimal sum;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Product product;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sale_id")
	private Sale sale;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSum() {
		return sum.setScale(2, RoundingMode.HALF_UP);
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public boolean isDiscountAvailable(Discount discount) {
		return product.getDiscounts().contains(discount);
	}

	public void calculateSum() {
		if (product != null && product.getPrice() != null) {
			sum = product.getPrice().multiply(BigDecimal.valueOf(quantity));
		}
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", product.getName(), product.getPrice(), quantity);
	}


}

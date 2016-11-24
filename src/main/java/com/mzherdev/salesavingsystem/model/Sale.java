package com.mzherdev.salesavingsystem.model;

import com.mzherdev.salesavingsystem.tools.TimeUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sale implements Serializable {

	private static final long serialVersionUID = 5789981007587667953L;

	public Sale() {
	}

	//for test mock
	public Sale(LocalDateTime date, double cost, double costWithDiscount) {
		this.date = date;
		this.cost = cost;
		this.costWithDiscount = costWithDiscount;
	}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(insertable = false, updatable = false)
	private LocalDateTime date;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sale")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<OrderItem> items = new ArrayList<OrderItem>();

	@Column
	@NotNull
	private double cost;

	@Column(name = "cost_with_discount")
	@NotNull
	private double costWithDiscount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public double getCost() {
	return new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getCostWithDiscount() {
		return new BigDecimal(costWithDiscount).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public void setCostWithDiscount(double costWithDiscount) {
		this.costWithDiscount = costWithDiscount;
	}

	public int getItemsSize() {
		return items.size();
	}

	public String getLocalDateTimeAsString() {
		return TimeUtils.toString(date);
	}

	@Override
	public String toString() {
		return "Sale{" +
				"id=" + id +
				", cost=" + cost +
				", date=" + getLocalDateTimeAsString() +
				'}';
	}
}

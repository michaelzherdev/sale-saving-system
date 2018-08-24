package com.mzherdev.salesavingsystem.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.mzherdev.salesavingsystem.tools.TimeUtils;

@Entity
@Table(name = "sales")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sale implements Serializable {

    private static final long serialVersionUID = 5789981007587667953L;

    public Sale() {
        this.cost = BigDecimal.ZERO;
        this.costWithDiscount = BigDecimal.ZERO;
    }

    //for test mock
    public Sale(LocalDateTime date, BigDecimal cost, BigDecimal costWithDiscount) {
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
    private BigDecimal cost;

    @Column(name = "cost_with_discount")
    @NotNull
    private BigDecimal costWithDiscount;

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

    public BigDecimal getCost() {
        return cost.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCostWithDiscount() {
        return costWithDiscount.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostWithDiscount(BigDecimal costWithDiscount) {
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

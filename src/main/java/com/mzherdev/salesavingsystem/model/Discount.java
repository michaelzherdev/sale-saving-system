package com.mzherdev.salesavingsystem.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import org.hibernate.validator.constraints.Range;

import com.mzherdev.salesavingsystem.tools.TimeUtils;

@Entity
@Table(name = "discounts")
public class Discount implements Serializable {

    public static final int MIN_DISCOUNT_AMOUNT = 5;
    public static final int MAX_DISCOUNT_AMOUNT = 10;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "time_start")
    @NotNull
    private LocalDateTime timeStart;

    @Column(name = "time_end")
    private LocalDateTime timeEnd;

    @Column
    @Range(min = 5, max = 10)
    private Integer amount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    public Discount() {
    }

    public Discount(LocalDateTime timeStart, LocalDateTime timeEnd, Integer amount, Product product) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.amount = amount;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStartTimeAsString() {
        return TimeUtils.toString(timeStart);
    }

    public String getEndTimeAsString() {
        return TimeUtils.toString(timeEnd);
    }
}

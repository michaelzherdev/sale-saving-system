package com.mzherdev.salesavingsystem.model;

import com.mzherdev.salesavingsystem.tools.TimeUtils;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
public class Discount implements Serializable {

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

    @ManyToOne(cascade = CascadeType.ALL)
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

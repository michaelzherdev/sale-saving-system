package com.mzherdev.salesavingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "order_items")
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
    private double sum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
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

    public double getSum() {
        return new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setSum(double sum) {
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

    @Override
    public String toString() {
        return product.getName() + new BigDecimal(product.getPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue() + "$ * " + quantity;
    }


}

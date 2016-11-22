package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.model.OrderItem;

import java.util.List;

public interface OrderItemService {

	void add(OrderItem orderItem);

	void edit(OrderItem orderItem);

	OrderItem getOrderItem(int orderItemId);

	List<OrderItem> getAllOrderItems();
}

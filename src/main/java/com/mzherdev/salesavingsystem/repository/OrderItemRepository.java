package com.mzherdev.salesavingsystem.repository;

import com.mzherdev.salesavingsystem.model.OrderItem;

import java.util.List;

public interface OrderItemRepository {

	void add(OrderItem orderItem);
	
	void edit(OrderItem orderItem);
	
	OrderItem getOrderItem(int orderItemId);
	
	List<OrderItem> getAllOrderItems();
}

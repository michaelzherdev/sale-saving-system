package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.repository.OrderItemRepository;
import com.mzherdev.salesavingsystem.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void add(OrderItem orderItem) {
		orderItemRepository.add(orderItem);
	}

	@Override
	public void edit(OrderItem orderItem) {
		orderItemRepository.edit(orderItem);
	}

	@Override
	public OrderItem getOrderItem(int orderItemId) {
		return orderItemRepository.getOrderItem(orderItemId);
	}

	@Override
	public List<OrderItem> getAllOrderItems() {
		return orderItemRepository.getAllOrderItems();
	}
}

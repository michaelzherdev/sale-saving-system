package com.mzherdev.salesavingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzherdev.salesavingsystem.model.OrderItem;
import com.mzherdev.salesavingsystem.repository.OrderItemRepository;

@Service("orderItemService")
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void save(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}
}

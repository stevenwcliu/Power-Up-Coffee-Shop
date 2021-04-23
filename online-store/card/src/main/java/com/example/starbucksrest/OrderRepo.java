package com.example.starbucksrest;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.starbucksrest.Order;


public interface OrderRepo extends CrudRepository<Order, Integer> {

	@Query(value = "SELECT o from Order o")
	List<Order> findAllOrder();

}

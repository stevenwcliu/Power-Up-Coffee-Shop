package com.example.onlinestore;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.onlinestore.PaymentInfo;

public interface PaymentRepo extends CrudRepository<PaymentInfo, Integer> {

	@Query(value = "SELECT card FROM PaymentInfo card")
	List<PaymentInfo> findAllCard();


}
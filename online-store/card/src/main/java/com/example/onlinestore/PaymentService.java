package com.example.onlinestore;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired PaymentRepo paymentRepo;
     
    public void save(PaymentInfo card) {
        paymentRepo.save(card);
    }
     
    public List<PaymentInfo> listAll() {
        return (List<PaymentInfo>) paymentRepo.findAll();
    }
     
     
    // public void delete(Long id) {
    //  	return repo.deleteById(id);
    // }
     
}
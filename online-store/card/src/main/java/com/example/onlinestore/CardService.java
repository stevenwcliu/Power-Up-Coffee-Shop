package com.example.onlinestore;

import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired CardRepo repo;
     
    public void save(StarbucksCard card) {
        repo.save(card);
    }
     
    public List<StarbucksCard> listAll() {
        return (List<StarbucksCard>) repo.findAll();
    }
     
     
    // public void delete(Long id) {
    //  	return repo.deleteById(id);
    // }
     
}
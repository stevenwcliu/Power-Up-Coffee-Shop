package com.example.starbucksapi;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

@RestController
class StarbucksCardController {

    private final StarbucksCardRepository repository;

    class Message {
        private String status;

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String msg) {
            this.status = msg;
        }
    }

    StarbucksCardController(StarbucksCardRepository repository)
    {
        this.repository = repository;
    }

    //Create new card
    @PostMapping("/cards")
    StarbucksCard newCard() {
        StarbucksCard newCard = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;

        newCard.setCardNumber(String.valueOf(num));
        newCard.setCardCode(String.valueOf(code));
        newCard.setBalance(20.00);
        newCard.setActivated(false);
        newCard.setStatus("New Card");
        newCard.setReward(0);
        return repository.save(newCard);

    }

    //Get all cards
    @GetMapping("/cards")
    List<StarbucksCard> all() {
        return repository.findAll();
    }

    //Delete all cards
    @DeleteMapping("/cards")
    Message deleteAll() {
        repository.deleteAllInBatch();
        Message msg = new Message();
        msg.setStatus("All Cards Cleared!");
        return msg;
    }

    @GetMapping("/cards/{num}")
    StarbucksCard getOne(@PathVariable String num, HttpServletResponse response) {
        StarbucksCard card = repository.findByCardNumber(num);
        if(card == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
        return  card;
    }

    @PostMapping("/card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable  String code, HttpServletResponse response) {
        StarbucksCard card = repository.findByCardNumber(num);
        if(card == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
        if(card.getCardCode().equals(code)) {
            card.setActivated(true);
            repository.save(card);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Valid!");
        }
        return card;
    }
}
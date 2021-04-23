package com.example.starbucksrest;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.starbucksrest.StarbucksCard;

@RestController
class StarbucksCardController{

	private final CardRepo repo;

	StarbucksCardController(CardRepo repo){
		this.repo = repo;
	}

	@PostMapping("/cards")
	StarbucksCard newcard(){
	StarbucksCard newcard = new StarbucksCard();

	Random random = new Random();
	int num = random.nextInt(900000000) + 100000000;
	int code = random.nextInt(900) + 100;


	newcard.setCardNumber(String.valueOf(num));
	newcard.setCardCode(String.valueOf(code));
	newcard.setBalance(20.00);
	newcard.setActivated(false);
	newcard.setStatus("New card");
	return repo.save(newcard);

	}

	@GetMapping("/cards")
	List<StarbucksCard> getAll(){

		return repo.findAllCard();
	}

	@GetMapping("/card/{num}")
	StarbucksCard getOne(@PathVariable String num,HttpServletResponse response){
		StarbucksCard card = repo.findByCardNumber(num);
		if(card == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error! Card Not found.");
		}
		return card;
	}

	@PostMapping("/card/activate/{num}/{code}")
	StarbucksCard activate(@PathVariable String num,@PathVariable String code,HttpServletResponse response){
		StarbucksCard card = repo.findByCardNumber(num);
		if(card == null){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error! Card Not found.");
		}
		if(card.getCardCode().equals(code)){
			card.setActivated(true);
			repo.save(card);
		}else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error! Card Not Valid.");
		}

		return card;
	}

}
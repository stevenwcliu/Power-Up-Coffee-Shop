package com.example.onlinestore;


import java.util.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.example.onlinestore.StarbucksCard;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

@Slf4j
@Controller
public class StarbucksCardController{

	@Autowired
	private CardService cardservice;


  @GetMapping("/rewards")
  public String rewards( @ModelAttribute("command") StarbucksCard command, 
    Model model) {

    List<StarbucksCard> cards = cardservice.listAll();
    model.addAttribute("cardlist",cards);

    return "rewards" ;

  }

    //rendering the card page
  @RequestMapping("/cards")
  public String cards(@ModelAttribute("command") StarbucksCard command, 
    Model model){

    // StarbucksCard card = new StarbucksCard();
    // String name = card.getNickName();

    List<StarbucksCard> cards = cardservice.listAll();
    model.addAttribute("cardlist",cards);
    
    return "cards";
  }

     //rendering the add payment page
  @PostMapping("/adds")
  public String addCards(@ModelAttribute("command") StarbucksCard command, 
    Model model){

      StarbucksCard card = new StarbucksCard();

      card.setNickName(command.getNickName());
      card.setCardType(command.getCardType());
      card.setFirstname(command.getFirstname());
      card.setLastname(command.getLastname());
      card.setExpYear(command.getExpYear());
      card.setExpMon(command.getExpMon());
      card.setCardNum(command.getCardNum());
      card.setCvv(command.getCvv());
      card.setBalance(100.00);
      card.setRewards(0);
      cardservice.save(card);
 

    return "cards";
  }

  @GetMapping("/add")
  public String addPage(@ModelAttribute("command") StarbucksCard command, 
    Model model){

    return "add";
  } 


}
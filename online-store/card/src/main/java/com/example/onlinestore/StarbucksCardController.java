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
@RequestMapping("/")
public class StarbucksCardController{

	@Autowired
	private CardRepo repo;
   

  @GetMapping("/rewards")
  public String rewards( @ModelAttribute("command") StarbucksCard command, 
    Model model) {

    String reward = "145";
    String name = "Kay";
    command.setRewards(reward);
    command.setName(name);
      // repo.save(command);

    model.addAttribute("message","Reward shown.");
    model.addAttribute("custName",command.getName());
    model.addAttribute("starBal",command.getRewards());

    return "rewards" ;

  }

    //rendering the card page
    @RequestMapping("/cards")
    public String cards(@ModelAttribute("command") StarbucksCard command, 
      Model model){

      Double bal = 26.18;
      command.setBalance(bal);
      // repo.save(command);

      model.addAttribute("cardBal",command.getBalance());
      return "cards";
    }

     //rendering the add payment page
    @RequestMapping("/add")
    public String addCards(@ModelAttribute("command") StarbucksCard command, 
      Model model){

      // Double bal = 26.18;
      // command.setBalance(bal);
      // // repo.save(command);

      // model.addAttribute("cardBal",command.getBalance());
      return "add";
    }


}
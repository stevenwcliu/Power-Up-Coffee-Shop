package com.example.starbucksrest;

import java.util.*;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.example.starbucksrest.StarbucksCard;
import com.example.starbucksrest.OrderRepo;
import com.example.starbucksrest.CardRepo;
import com.example.starbucksrest.Order;

@RestController
class StarbucksOrderController{

	private final OrderRepo repo;

	@Autowired
	private CardRepo cardRepo;

	class Message{
		private String status;

		public String getStatus(){
			return status;
		}

		public void setStatus(String msg){
			status = msg;
		}
	}


	private HashMap<String, Order> orders = new HashMap<>();

	public StarbucksOrderController(OrderRepo repo){
		this.repo = repo;
	}


	@GetMapping("/orders")
	List<Order> getAll(){
		return repo.findAllOrder();
	}

	// @GetMapping("/order/register/{regid}")
	// Message getState(@PathVariable String regid){
	// 	Order active = orders.get(regid);
	// 	if(active != null){
	// 		Message msg = new Message();
	// 		msg.setStatus(active.getStatus());
	// 		return msg;
	// 	}else{
	// 		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Order not found.");
	// 	}
	// }

	@DeleteMapping("/orders")
	Message deleteAll(){
		repo.deleteAll();
		orders.clear();
		Message msg = new Message();
		msg.setStatus("All cleared");
		return msg;
	}


	@DeleteMapping("/order/register/{regid}")
	Message deleteActive(@PathVariable String regid){
		Order active = orders.get(regid);
		if(active != null){
		orders.remove(regid);
		Message msg = new Message();
		msg.setStatus("Active order cleared");
		return msg;
		}else{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Order not found.");
		}
	}
		

	@PostMapping("/order/register/{regid}")
	@ResponseStatus(HttpStatus.CREATED)
	Order newOrder(@PathVariable String regid, @RequestBody Order order){
		System.out.println("Placing order: Reg id = (" + regid + ") =>" + order);
		if(order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order.");
		}


		Order active = orders.get(regid);
		if(active != null){
			System.out.println("Active order: Reg id = (" + regid + ") =>" + active);
			if(active.getStatus().equals("Ready for Payment")){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Active order exists.");
			}
		}

		double price = 0.0;
		switch(order.getDrink()){
			case "Latte":
				switch(order.getSize()){
					case "Tall":
					price = 2.95;
					break;
					case "Grande":
					price = 3.95;
					break;
					case "Venti":
					price = 4.95;
					break;
					default:
					 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid size.");
				}
			break;

			case "Americano":
				switch(order.getSize()){
					case "Tall":
					price = 2.25;
					break;
					case "Grande":
					price = 3.35;
					break;
					case "Venti":
					price = 4.65;
					break;
					default:
					 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid size.");
				}
			break;

			case "Mocha":
				switch(order.getSize()){
					case "Tall":
					price = 2.85;
					break;
					case "Grande":
					price = 3.55;
					break;
					case "Venti":
					price = 4.75;
					break;
					default:
					 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid size.");
				}
			break;

		}

		double tax = 0.1;
		double total = price + (price * tax);
		double scale = Math.pow(10,2);
		double rounded = Math.round(total * scale) / scale;
		order.setTotal(rounded);
		order.setStatus("Ready for Payment");
		Order new_order = repo.save(order);
		orders.put(regid,new_order);
		return new_order;
	}

	@PostMapping(" /order/register/{regid}/pay/{cardnum}")
	StarbucksCard process(@PathVariable String regid, @PathVariable String cardnum){
		System.out.println("Placing order: Reg id = (" + regid + ") with card: "+ cardnum);
		Order active = orders.get(regid);
		if(active ==null ){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Order not found.");
		}
		if(cardnum.equals("")){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid card number.");
		}
		if(active.getStatus().startsWith("Paid with Card")){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Order has been paid.");
		}
		StarbucksCard card = cardRepo.findByCardNumber(cardnum);
		if(card == null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Card not found.");
		}
		if(!card.isActivated()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Card not acitivate.");
		}


		double price = active.getTotal();
		double balance = card.getBalance();
		if(balance - price < 0){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient funds.");
		}

		double newBala = balance - price;
		card.setBalance(newBala);
		String status = "Paid with Card: " + cardnum + " New balance: $ " + newBala;
		active.setStatus(status);
		cardRepo.save(card);
		repo.save(active);
		return card;
	}




}
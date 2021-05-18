package com.example.springcashier;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.print.attribute.standard.MediaTray;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import lombok.extern.slf4j.Slf4j;
import com.example.springcashier.DrinkOrder;
import com.example.springcashier.Ingredient;
import com.example.springcashier.Ingredient.Type;

import lombok.Getter;
import lombok.Setter;


@Slf4j
@Controller
@RequestMapping("/pay")
public class PaymentController {

  @Getter
  @Setter
  class Message {
      private String msg;
      public Message(String m){ msg = m; }
  }
  
  class ErrorMessages {
      private ArrayList<Message> messages = new ArrayList<Message>();
      public void add(String msg) { messages.add(new Message(msg));}
      public ArrayList<Message> getMessages() { return messages; }
      public void print() {
          for(Message m : messages)
          {
                  System.out.println(m.msg);
          }
      }
  }

    // API Key
    final String API_KEY = "apikey";
    // API Key Value
    final String API_KEY_VALUE = "Zkfokey2311";
	  // KONG URL
	final String KONG = "http://146.148.103.73/api";


    @GetMapping
    public String showPayForm(Model model) {
    model.addAttribute("orderPayment", new Payment());
    return "pay";
    }

    @PostMapping
    public String getOrders(@Valid @ModelAttribute("orderPayment") Payment orderPayment, Errors errors, Model model) {

        String name = "";
        String orderNumber = "";
        String cardNumber = "";
        boolean hasError = false;
        ErrorMessages msgs = new ErrorMessages();

        if (errors.hasErrors()) {
            return "pay";
        }

        log.info("Processing payment: " + orderPayment);

        name = orderPayment.getName();
        orderNumber = orderPayment.getOrderNumber();
        cardNumber = orderPayment.getCardNumber();
        log.info("Name: "+name);
        log.info("Order Number: "+orderNumber);
        log.info("Card Number: "+cardNumber);

        
        if(orderNumber.equals("")){
            hasError=true; 
            msgs.add( "Valid Order Number is Required!" );
        }
        if(hasError) {
            msgs.print();
            model.addAttribute( "messages", msgs.getMessages() ) ;
            return "pay";
        }

        var values = new HashMap<String, String>() {{ }};

        var objectMapper = new ObjectMapper();
        try {
        String requestBody = objectMapper.writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();

        // Create HTTP request object
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(KONG+"/order/register/"+orderNumber+"/pay/"+cardNumber))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header(API_KEY, API_KEY_VALUE)
                .header("Content-Type", "application/json")
                .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());
                    if(response.body().contains("Order Not Found")){
                        hasError=true; 
                        msgs.add( "Incorrect Order Number!" );
                    }
                    if(response.body().contains("Card Not Found"))
                    {
                        hasError=true; 
                        msgs.add( "Incorrect Card Number!" );    
                    }
                    if(response.body().contains("Clear Paid Active Order")) {
                        hasError=true; 
                        msgs.add( "Order Paid Already!" ); 
                    }
                    if(response.body().contains("Insufficient Funds on Card")){
                        hasError=true; 
                        msgs.add( "Insufficient Funds in Starbucks Card!" ); 
                    }
                    if(response.body().contains("Card Not Activated")){
                        hasError=true; 
                        msgs.add( "Starbucks Card Not Activated! Contact Help Desk!" ); 
                    }
                    if(hasError) {
                        msgs.print();
                        model.addAttribute( "messages", msgs.getMessages() ) ;
                        return "pay";
                    }
                    if(!response.body().contains("Order Not Found") && !response.body().contains("Card Not Found")){
                    ObjectMapper cardMapper =  new ObjectMapper();
                    Card currentCard = cardMapper.readValue(response.body(), Card.class);
                    System.out.println("New Card Balance: "+currentCard.getBalance());
                    model.addAttribute( "message", "Order Paid with Card Number: "+currentCard.getCardNumber()+"! Updated Starbucks Card Balance: "+currentCard.getBalance());
                    return "pay";
                    }
                    
                } catch (Exception e) {
                    System.out.println("Error 2"+e);
                }
            } catch (Exception e) {
              System.out.println("Error 1"+e);
            }
            
            return "redirect:pay";
    }

}
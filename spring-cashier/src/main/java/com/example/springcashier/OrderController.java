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
@RequestMapping("/order")
public class OrderController {

  @Getter
  @Setter
  class OrderNumber {
      private String orderNum;
      public OrderNumber(String m){ orderNum = m; }
  }
  
  class OrderNumbers {
      private ArrayList<OrderNumber> orderNumber = new ArrayList<OrderNumber>();
      public void add(String orderNum) { orderNumber.add(new OrderNumber(orderNum));}
      public ArrayList<OrderNumber> getOrders() { return orderNumber; }
      public void print() {
          for(OrderNumber m : orderNumber)
          {
                  System.out.println(m.orderNum);
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
  public String getOrders(Model model) {
    OrderNumbers orderNums = new OrderNumbers();
    Boolean hasError = false;
    HttpClient client = HttpClient.newHttpClient();

    // Create HTTP request object
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(KONG+"/orders"))
            .GET()
            .header(API_KEY, API_KEY_VALUE)
            .header("Content-Type", "application/json")
            .build();
        
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("GET All Cards Reponse"+response.body());
                ObjectMapper ordersMapper =  new ObjectMapper();
                List<OrderResponse> orderListNumber = new ArrayList<OrderResponse>();
                List<Order> orderList  = ordersMapper.readValue(response.body(), new TypeReference<List<Order>>(){});
                if(orderList.size() < 1) {
                    hasError = true;
                    model.addAttribute( "message", "No Active Orders in Register");
                    return "order";
                }
                System.out.println(hasError);
                for(Order each: orderList) {
                    System.out.println(each);
                    System.out.println("Order Number: "+DesignDrinkOrderController.orderNumber.get(String.valueOf(each.getId())));
                    orderNums.add(DesignDrinkOrderController.orderNumber.get(String.valueOf(each.getId())));
                    orderListNumber.add(new OrderResponse(each, DesignDrinkOrderController.orderNumber.get(String.valueOf(each.getId()))));
                }
                model.addAttribute("orders", orderListNumber);
                // model.addAttribute("orderNumbers", orderNums.getOrders());
                // System.out.println(orderNums.getOrders().size());
            } catch (Exception e) {
                System.out.println("Error"+e);
            }
            
    return "order";
  }


}

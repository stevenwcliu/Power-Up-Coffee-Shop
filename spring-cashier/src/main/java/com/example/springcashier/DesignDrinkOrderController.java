package com.example.springcashier;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

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
@RequestMapping("/design")
public class DesignDrinkOrderController {

  public static HashMap<String, String> orderNumber = new HashMap<>();

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
	
    public static Map<String, String> itemType = new HashMap<>();
    static {
      itemType.put("CFLA","DRINK");
      itemType.put("CFAM","DRINK");
      itemType.put("CFMO","DRINK");
      itemType.put("ESPO","DRINK");
      itemType.put("WHML","MILK");
      itemType.put("SYML","MILK");
      itemType.put("ALML","MILK");
      itemType.put("COML","MILK");
      itemType.put("VENT","SIZE");
      itemType.put("TALL","SIZE");
      itemType.put("OWNC","SIZE");
      itemType.put("GRND","SIZE");
    }

@ModelAttribute
public void addIngredientsToModel(Model model) {
	List<Ingredient> ingredients = Arrays.asList(
	  new Ingredient("CFLA", "Caffe Latte", Type.DRINK),
	  new Ingredient("CFAM", "Caffe Americano", Type.DRINK),
    new Ingredient("CFMO", "Caffe Mocha", Type.DRINK),
    new Ingredient("ESPO", "Espresso", Type.DRINK),
	  new Ingredient("WHML", "Whole Milk", Type.MILK),
	  new Ingredient("SYML", "Soy Milk", Type.MILK),
    new Ingredient("ALML", "Almond Milk", Type.MILK),
    new Ingredient("COML", "Coconut Milk", Type.MILK),
	  new Ingredient("VENT", "Venti", Type.SIZE),
    new Ingredient("TALL", "Tall", Type.SIZE),
    new Ingredient("OWNC", "Your Own Cup", Type.SIZE),
	  new Ingredient("GRND", "Grande", Type.SIZE)
	);

	Type[] types = Ingredient.Type.values();
	for (Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(ingredients, type));
	}
}
	

  @GetMapping
  public String showDesignForm(Model model) {
    model.addAttribute("design", new DrinkOrder());
    return "design";
  }


  @PostMapping
  public String processDesign(@Valid @ModelAttribute("design") DrinkOrder design, Errors errors, Model model) {
    
    String drink = "";
    String milk = "";
    String size = "";
    int drinkTypeCount = 0;
    int milkTypeCount = 0;
    int sizeTypeCount = 0;
    ErrorMessages msgs = new ErrorMessages();

    boolean hasError = false;

    if (errors.hasErrors()) {
      return "design";
    }

    log.info("Processing design: " + design);

    System.out.println(design.getIngredients());
    for(String item: design.getIngredients())
    {
      System.out.println("Each item: "+item.substring(0, item.length()-4));
      System.out.println("Item Type: "+itemType.get(item.substring(item.length()-4)));
      if(itemType.get(item.substring(item.length()-4)).equals("DRINK")) {
        drinkTypeCount++;
      }
      else if(itemType.get(item.substring(item.length()-4)).equals("MILK")) {
        milkTypeCount++;
      }
      else if(itemType.get(item.substring(item.length()-4)).equals("SIZE")){
        sizeTypeCount++;
      }
    }

    if(drinkTypeCount != 1) { hasError=true; msgs.add( "One Drink Type Required" ); }
    if(milkTypeCount != 1) { hasError=true; msgs.add( "One Milk Type Required" ); } 
    if(sizeTypeCount != 1) { hasError=true; msgs.add( "One Size Type Required" ); } 
    
    if(hasError) {
      msgs.print();
      model.addAttribute( "messages", msgs.getMessages() ) ;
      return "design";
    }

    int max = 10000000;
    int min = 10000;
    Random randomNum = new Random();
    int generatedOrderNumber = min + randomNum.nextInt(max);
    System.out.println(generatedOrderNumber);


    var values = new HashMap<String, String>() {{
      put("drink", design.getIngredients().get(0).substring(0, design.getIngredients().get(0).length()-4));
      put ("milk", design.getIngredients().get(1).substring(0, design.getIngredients().get(1).length()-4));
      put ("size", design.getIngredients().get(2).substring(0, design.getIngredients().get(2).length()-4));
    }};

    var objectMapper = new ObjectMapper();
    try {
      String requestBody = objectMapper.writeValueAsString(values);

      HttpClient client = HttpClient.newHttpClient();

    // Create HTTP request object
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(KONG+"/order/register/"+generatedOrderNumber))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header(API_KEY, API_KEY_VALUE)
            .header("Content-Type", "application/json")
            .build();
    // Send HTTP request
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
      ObjectMapper orderMapper =  new ObjectMapper();
      Order newOrder =  orderMapper.readValue(response.body(), Order.class);
      System.out.println(newOrder);
      orderNumber.put(String.valueOf(newOrder.getId()), String.valueOf(generatedOrderNumber));
      System.out.println("HashMap: "+orderNumber);
    } catch (Exception e) {
      System.out.println(e);
    }
    
      System.out.println("New Order Placed!");
      model.addAttribute( "message", "New Order Placed! Your Order Number is: "+ generatedOrderNumber);
      return "design";
    } catch (Exception e) {
      System.out.println(e);
    }
    
    return "redirect:design";
  }


  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }


}

package com.example.springcashier;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

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
import com.example.springcashier.Taco;
import com.example.springcashier.Ingredient;
import com.example.springcashier.Ingredient.Type;



@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    // API Key
    final String API_KEY = "apikey";
    // API Key Value
    final String API_KEY_VALUE = "Zkfokey2311";
	  // KONG URL
	  final String KONG = "http://146.148.103.73/api";
	
@ModelAttribute
public void addIngredientsToModel(Model model) {
	List<Ingredient> ingredients = Arrays.asList(
	  new Ingredient("CFLA", "Caffe Latte", Type.DRINK),
	  new Ingredient("CFAM", "Caffe Americano", Type.DRINK),
	  new Ingredient("WHML", "Whole Milk", Type.MILK),
	  new Ingredient("SYML", "Soy Milk", Type.MILK),
	  new Ingredient("VENT", "Venti", Type.SIZE),
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
    model.addAttribute("design", new Taco());
    return "design";
  }



  @PostMapping
  public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
    
    if (errors.hasErrors()) {
      return "design";
    }

    log.info("Processing design: " + design);

    System.out.println(design.getIngredients());

    int max = 10000000;
    int min = 10000;
    Random randomNum = new Random();
    int generatedOrderNumber = min + randomNum.nextInt(max);
	

    HttpClient client = HttpClient.newHttpClient();

    // Create HTTP request object
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(KONG+"/ping"))
            .GET()
            .header(API_KEY, API_KEY_VALUE)
            .header("Content-Type", "application/json")
            .build();
    // Send HTTP request
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(response.body());
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("Done!");

    return "redirect:/";
  }


  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }


}

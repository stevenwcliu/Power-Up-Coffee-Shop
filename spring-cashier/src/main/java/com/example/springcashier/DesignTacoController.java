package com.example.springcashier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    return "redirect:/orders/current";
  }


  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

}

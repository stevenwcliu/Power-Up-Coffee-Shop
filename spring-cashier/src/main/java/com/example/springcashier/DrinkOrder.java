package com.example.springcashier;

import java.util.HashMap;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class DrinkOrder {
    
    @NotNull
    @Size(min=1, message="Name must be at least 1 characters long")
    private String name;
    
    @Size(min=3, max=3, message="You must choose only 1 ingredient from all ingredient sections")
    private List<String> ingredients;

}

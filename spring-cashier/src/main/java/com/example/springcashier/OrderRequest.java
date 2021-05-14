package com.example.springcashier;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class OrderRequest {

    private String drink;
    private String milk;
    private String size;

}
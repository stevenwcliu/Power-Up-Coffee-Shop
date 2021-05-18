package com.example.onlinestore;


import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.Base64;
import javax.validation.Valid;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import com.example.onlinestore.StarbucksCard;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import com.example.springcybersource.*;


@Slf4j
@Controller
// @RequestMapping("/")
public class StarbucksCardController{



  @Value("${cybersource.apihost}") String apiHost ;
  @Value("${cybersource.merchantkeyid}")  String merchantKeyId ;
  @Value("${cybersource.merchantsecretkey}")  String merchantsecretKey ;
  @Value("${cybersource.merchantid}")  String merchantId ;

  private CyberSourceAPI api = new CyberSourceAPI();

    // API Key
  final String API_KEY = "apikey";
    // API Key Value
  final String API_KEY_VALUE = "Zkfokey2311";
    // KONG URL
  final String KONG = "http://146.148.103.73/api";

  private StarbucksCard card;


  @GetMapping("/rewards")
  public String rewards( @ModelAttribute("command") StarbucksCard command, 
    Model model) {

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(KONG+"/cards/"+card.getCardNumber()))
      .header(API_KEY, API_KEY_VALUE)
      .header("Content-Type", "application/json")
      .build();

      try {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper cardMapper = new ObjectMapper();
        card = cardMapper.readValue(response.body(),StarbucksCard.class);
        System.out.println(response.body());
        System.out.println(card);
      } catch (Exception e) {
        System.out.println(e);
      }
      System.out.println("Done!");
    } catch (Exception e) {
      System.out.println(e);
    }

    model.addAttribute("cardlist",card);


    return "rewards" ;

  }

    //rendering the card page
  @GetMapping("/cards")
  public String cards(Model model){

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(KONG+"/cards/"+card.getCardNumber()))
      .header(API_KEY, API_KEY_VALUE)
      .header("Content-Type", "application/json")
      .build();

      try {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper cardMapper = new ObjectMapper();
        card = cardMapper.readValue(response.body(),StarbucksCard.class);
        System.out.println(response.body());
        System.out.println(card);
      } catch (Exception e) {
        System.out.println(e);
      }
      System.out.println("Done!");
    } catch (Exception e) {
      System.out.println(e);
    }

    model.addAttribute("cardlist",card);

    return "cards";
  }

     //rendering the add payment page
  @PostMapping("/adds")
  public String addCards(@ModelAttribute("command") PaymentInfo command, 
    Model model){


    CyberSourceAPI api = new CyberSourceAPI();
    CyberSourceAPI.setHost(apiHost);
    CyberSourceAPI.setKey(merchantKeyId);
    CyberSourceAPI.setSecret(merchantsecretKey);
    CyberSourceAPI.setMerchant(merchantId);
    CyberSourceAPI.debugConfig();



    int min = 1239871;
    int max = 9999999;
    int random_1 = (int)Math.floor(Math.random()*(max-min+1)+min);
    String order_num = String.valueOf(random_1);
    AuthRequest auth = new AuthRequest() ;
    auth.reference = order_num;
    auth.billToFirstName = command.getFirstname();
    auth.billToLastName = command.getLastname() ;
    auth.transactionAmount = "20.00" ;
    auth.transactionCurrency = "USD" ;
    auth.cardNumnber = command.getCardNum();
    auth.cardExpMonth = command.getExpMon();
    auth.cardExpYear = command.getExpYear() ;
    auth.cardCVV = command.getCvv();
    auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
    auth.billToAddress = command.getAddress() ;
    auth.billToCity = command.getCity() ;
    auth.billToState =command.getState() ;
    auth.billToZipCode =command.getZip();
    auth.billToPhone = command.getPhonenumber() ;
    auth.billToEmail = command.getEmail() ;
    if(auth.cardType.equals("ERROR")){
      System.out.println("Unsupported card type.");
      return "add";
    }

    boolean authValid = true ;
    AuthResponse authResponse = new AuthResponse() ;
    System.out.println("\n\nAuth Request: " + auth.toJson() ) ;
    authResponse = api.authorize(auth) ;  
    System.out.println("\n\nAuth Response: " + authResponse.toJson() ) ;
    if ( !authResponse.status.equals("AUTHORIZED") ) {
      authValid = false;
      System.out.println(authResponse.message);
      return "add";
    }

    boolean captureValid = true ;
    CaptureRequest capture = new CaptureRequest() ;
    CaptureResponse captureResponse = new CaptureResponse() ;
    if ( authValid ) {
      capture.paymentId = authResponse.id ;
      capture.transactionAmount = "20.00" ;
      capture.transactionCurrency = "USD" ;
      System.out.println("\n\nCapture Request: " + capture.toJson() ) ;
      captureResponse = api.capture(capture) ;
      System.out.println("\n\nCapture Response: " + captureResponse.toJson() ) ;
      if ( !captureResponse.status.equals("PENDING") ) {
        captureValid = false ;
        System.out.println(captureResponse.message);
        return "add";
      }

    }


    if(authValid && captureValid){

      var values = new HashMap<String,String>(){{}};
      var objectMapper = new ObjectMapper();

      try {
        String requestBody = objectMapper.writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(KONG+"/cards"))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .header(API_KEY, API_KEY_VALUE)
        .header("Content-Type", "application/json")
        .build();


        try {
          HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
          ObjectMapper cardMapper = new ObjectMapper();
          card = cardMapper.readValue(response.body(),StarbucksCard.class);
          System.out.println(response.body());
          System.out.println(card);
        } catch (Exception e) {
          System.out.println(e);
        }
        System.out.println("Done!");
      } catch (Exception e) {
        System.out.println(e);
      }

    }

    return "add";
  }

  @GetMapping("/add")
  public String addPage(@ModelAttribute("command") PaymentInfo command,
    Model model){

    return "add";
  } 


}
# Team Project (Skyline Cloud)

Team Journals should include
* Overall Architecture Diagram of your Cloud Deployment  

A section for each of the following discussion the features implemented  
## Cashier's App  
What features were implemented? 

## Backoffice Help Desk App  
Due to only having 2 members out of 4 members for the group project. We decided that we would implement the Backoffice Help Desk App in the future if we were to continue to work on this project.  
The feature of the Backoffice Help Desk App would be to assist customer who are having problems with rewards and their Starbucks Card credits. Also would have a register feature for registering Starbucks employees working the shops like the Baristas and Managers. 

## Online Store  
What features were implemented?  

## REST API  
Final design with sample request/response  
The Starbucks API Specification is as follows:

```
GET 	/ping  
  Ping Health Check.  
  {  
    "Test": "Starbucks API version 1.0 alive!"  
  }		
GET 	/cards 
   Get all Starbucks Cards from MySQL Database (along with balances).  
   [
      {
         "id": 42,
         "cardNumber": "419401969",
         "cardCode": "199",
         "balance": 20.0,
         "activated": false,
         "status": "New Card",
         "reward": 0
     },
     {
        "id": 43,
        "cardNumber": "607770170",
        "cardCode": "354",
        "balance": 20.0,
        "activated": false,
        "status": "New Card",
        "reward": 0
      }
    ]		
POST 	/cards
		Create a new Starbucks Card.  
  {  
    "id": 58,
    "cardNumber": "261276112",
    "cardCode": "105",
    "balance": 20.0,
    "activated": false,
    "status": "New Card",
    "reward": 0
 }  
GET 	/cards/{num}  
		Get the details of a specific Starbucks Card.  
 {  
    "id": 58,  
    "cardNumber": "261276112",  
    "cardCode": "105",  
    "balance": 3.59,  
    "activated": false,  
    "status": "New Card",  
    "reward": 40 
 }		
POST 	/card/activate/{num}/{code}  
		Activate Card  
 {  
    "id": 58,  
    "cardNumber": "261276112",  
    "cardCode": "105",  
    "balance": 3.59,  
    "activated": true,  
    "status": "New Card",  
    "reward": 40  
 }     
POST    /order/register/{regid}  
        Create a new order. Set order as "active" for register.  
        Request:  
     {  
	      "Drink": "Caffe Latte",  
	      "Milk":  "Whole Milk",  
	      "Size":  "Grande"  
     }            
	    Response:   
     {  
       "id": 65,  
       "drink": "Caffe Latte",  
       "milk": "Whole Milk",  
       "size": "Grande",  
       "total": 3.91,  
       "status": "Ready for Payment"  
     }    	    
GET     /order/register/{regid}
        Request the current state of the "active" Order with a particular order number.  
   {
      "id": 3,
      "drink": "Caffe Latte",
      "milk": "Whole Milk",
      "size": "Grande",
      "total": 3.91,
      "status": "Ready for Payment"    
   }  
DELETE  /order/register/{regid}
        Clear the "active" Order with that particular order number.  
   {  
		  "Status": "Active Order Cleared!"  
   }   
POST    /order/register/{regid}/pay/{cardnum}
        Process payment for the "active" Order with order number and activated Starbucks Card. 
        Response: (with updated card balance)  
    {
       "id": 1,
       "cardNumber": "573639666",
       "cardCode": "916",
       "balance": 12.18,
       "activated": true,
       "status": "New Card",
       "reward": 20    
     }
GET     /orders
        Get a list of all active orders (for all registers) 
  [   
    {
        "id": 64,
        "drink": "Caffe Latte",
        "milk": "Whole Milk",
        "size": "Venti",
        "total": 4.24,
        "status": "Paid with Card: 261276112 Balance: $3.59" 
    },  
    {  
        "id": 65,
        "drink": "Caffe Latte",
        "milk": "Whole Milk",
        "size": "Grande",
        "total": 3.91,
        "status": "Ready for Payment"
    }
  ]   
DELETE 	/cards  
    Delete all Cards (Use for Unit Testing Teardown)
    {  
      "Status": "All Cards Cleared!"  
    }
DELETE 	/orders  
    Delete all Orders (Use for Unit Testing Teardown)
   {
      "Status": "All Orders Cleared!"
   }
```

## Integrations  
Which integrations were selected?  

## Cloud Deployments  
Design Notes on GitHub an Architecture Diagram of the overall Deployment.  
How does your Team's System Scale?  Can it handle > 1 Million Mobile Devices?  

## Technical Requirements  
Discussion with screenshot evidence of how each technical requirement is meet.  

## Challenges


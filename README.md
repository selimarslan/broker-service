## Broker Service

## to build and run it
    # java version is 17

    first $ mvn clean package
    then $ mvn spring-boot:run

## endpoints
    login - POST /api/v1/auth/login
    request - 
        {
            "username":"tom",
            "password":"tom"
        }


    create order - POST /api/v1/orders
    reqeust 
        {
            "assetName": "APPL",
            "orderSide": "BUY",
            "size":15,
            "price": 12
        }

    create order (admin) - POST /api/v1/orders/customer/2
    reqeust 
        {
            "assetName": "MSFT",
            "orderSide": "SELL",
            "size":10,
            "price": 12
        }


    list order - GET /api/v1/orders?startDate=2017-07-12&endDate=2025-07-12
    
    list order (admin) - GET /api/v1/orders/customer/4?startDate=2017-07-12&endDate=2025-07-12
    

    delete order - DELETE /api/v1/orders/3

    delete order (admin) - DELETE /api/v1/orders/6/customer/4
    
    
    match order - POST /api/v1/orders/5/match

    
    assets - GET /api/v1/assets
    
    assets - GET /api/v1/assets/customer/4

## initial data 
    Edit src/main/resources/data.sql to change the initial data.
    
    There are 1 admin user 
        user: admin, pass: admin
        
    There are 3 customers
        user: tom, pass: tom
        user: john, pass: john
        user: ahmet, pass: ahmet

    Bcrpyt used for password encryption.

    
## sample request to create order
```console
# login with customer 'ahmet'

curl --request POST \
  --url http://localhost:8080/api/v1/auth/login \
  --header 'Content-Type: application/json' \
  --data '{
	"username":"ahmet",
	"password":"ahmet"
}'
```

```console
# create order for logged in customer (ahmet),
# add JSESSIONID in cookie returned from login request

$ curl --request POST \
    --url http://localhost:8080/api/v1/orders \
    --header 'Content-Type: application/json' \
    --cookie JSESSIONID=11665B82472AB06184AFB83C7FB87430 \
    --data '{
    "assetName": "APPL",
    "orderSide": "BUY",
    "size":15,
    "price": 12
    }'
```

## how to use
    login with one of the customer
    create/modify orders and list assets 

---

    then login with admin
    you can also use admin endpoints to create/modify orders and list assets
    and use match order api to match pending orders



    
    


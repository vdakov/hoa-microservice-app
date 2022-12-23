# System level testing with Postman

Our application consists of a network of microservices, which are each tested independently as standalone services. However, to fully cover our functionality with tests, we also need to test whether the connections *between* the microservices work as intended. 

To do this, we are using Postman. In this file, you find a guide for testing all relevant functionality on the system level. This will include all API endpoints in the Gateway (working title authentication) microservice, since these are the ones that the user will have access to.

Between each test instance, we recommend restarting every microservice, to make sure IDs are assigned correctly.

When running the system locally, the URL of the gateway microservice will be "http://localhost:8081", and all the endpoints not related to authorization are located within the "/gateway" subdomain. Because of this, this testing guide will default to this prefix for all requests.

## Creating an HOA
We will make a POST request to the following URL:

**"http://localhost:8081/gateway/hoa/createHoa"**

An HOA has the following inherent attributes:
 - Name
 - Country
 - City

Accordingly, the user will have to provide these within the model object in the request. So the request body will look like the following:

**{  
"name":"hoa1",  
"country":"USA",  
"city":"Cincinnati"  
}**

The response entity should look like the following:

**{  
"country": "USA",  
"city": "Cincinnati",  
"name": "h2",  
"members": []  
}**

(Since for now, the every HOA is initialized without any members.)

![image](../instructions/createHoa.png)
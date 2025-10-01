# StockSyncService
A **Spring Boot microservice** that synchronizes product stock levels from multiple vendors(Sync runs every min) and exposes the current stock via a REST API.

## Build & Run Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker (To run DB via container)

### Steps to run locally
1. Clone the repo
2. Import the project to any IDE (e.g IntelliJ or Eclipse)
3. Locate StockSyncApplication and run the file or you can run this command: **mvn spring-boot:run**
4. Access the API documentation through swagger (http://localhost:8080/swagger-ui/index.html#/)
   <img width="1665" height="985" alt="image" src="https://github.com/user-attachments/assets/da5850f3-c273-4f6d-aa7a-9a124b245d9d" />
5. Test the APIs

### Additional APIS:
1. Sync runs every min via cron job but still added a manual trigger for the sync.
   <img width="1602" height="887" alt="image" src="https://github.com/user-attachments/assets/6ca8aca3-02b7-4665-b17e-981e227ece33" />

2. Added APIs to get products for Vendor A and Vendor B
   <img width="1645" height="215" alt="image" src="https://github.com/user-attachments/assets/dad4ec47-1fb5-46f1-ac04-80dd050b83df" />


### Simulating Vendor A
- Mocked with a Spring Boot controller at [/vendor/a/stock](http://localhost:8080/api/vendor/a/product)
- Sample Json Response
- [
  {
    "sku": "ABC123",
    "name": "Product A",
    "stockQuantity": 8,
    "vendorName": "VENDOR_A"
  },
  {
    "sku": "LMN789",
    "name": "Product C",
    "stockQuantity": 0,
    "vendorName": "VENDOR_A"
  }]

 ### Simulating Vendor B
 - A sample is given inside resources/csv folder. Place this to any system path in PC(c:/tmp/vendor-b/stock.csv)
 - Update **vendor.b.path=c:/tmp/vendor-b/stock.csv** in application properties if needed
 - A sample controller that will return the products from Vendor B was created [/vendor/b/stock](http://localhost:8080/swagger-ui/index.html#/vendor-controller/getBProducts)
 - Sample Json Response
 - [
  {
    "sku": "ABC123",
    "name": "Product A",
    "stockQuantity": 10,
    "vendorName": "VENDOR_B"
  },
  {
    "sku": "XYZ456",
    "name": "Product B",
    "stockQuantity": 0,
    "vendorName": "VENDOR_B"
  }
]

### Assumptions & Decisions Made
- Assumed 3 attempts only for Vendor A Api in case of failure
- Only out-of-stock transitions are logged and recorded in table (stock_out_event)
- Used PostgreSQL instead of using in memory (h2) since DB is inside container
- Runs at configurable intervals (1 min)
- Extensibility: New vendors implement VendorService interface.
- Sync will continue even if one vendor processing results into an error.
- Fetching vendor products as asynchronous.

### Trade-offs or Ideas for Improvement
#### Trade-offs
- Only out of stock event is saved and not full history of the product. In this case, the DB stays small
- Simple error handling (e.g CSV error handling, not every error scenario was handled)
- Simple entity to object (vice versa) mapping. Can use mapstruct to avoid boilerplate code in case of more attributes.

#### Ideas for Improvement
- Since Products can scale, it is better to use pagination for the response to get all products
- If vendors will scale more, we can update the vendorController to only have one getVendorProducts API with additional filters
- Add command to automatically transfer csv file from resources/ to container to minimize local setup.
- Full stock audit history
- Add Global Exception Handling (Using @ControllerAdvice) for better API error response.
- Only added few unit test samples. Need to improve code coverage (ideally above 80%)
  

   



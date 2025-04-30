# Boost Voucher API

A Springboot based API application to manage recipients, offers, and voucher creation/redeeming.

## Features

- Create and manage Recipients and SepcialOffers
- Generate Vouchers for a recipient and offer
- Redeem vouchers by code
- Built-in error handling
- Uses H2 in-memory database

---

## Getting Started

### Prerequisites
- Java 17
- Maven or Gradle

### Clone the Repository
git clone https://github.com/your-username/voucher-api.git
cd voucher-api

### Run using Maven
./mvnw spring-boot:run

### Run using Gradle
./gradlew bootRun

### View and manage H2 Database from browser
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (leave blank)

### Folder Structure
src/main/java/com/boost/voucher_api/
│
├── controller         # REST Controllers
├── model              # Entity classes
├── repository         # JPA Repositories
├── service            # Business logic
├── exception          # Custom exception handlers
└── VoucherApiApplication.java

## API Endpoints
- endpoints are used to create recipients, special offers and vouchers
- always make sure to create a the recipient and special offer first before trying to create and redeem vouchers
- sequence should follow
  - creating recipients
  - creating special offers
  - creating vouchers
  - redeem vouchers (use vouchher id returned in previous step)

## POST /recipients

Request Body (JSON)
{
"name": "andrew",
"email": "andrew@boost.com"
}

Response HTTP 200 OK
{
"id": 1,
"name": "andrew",
"email": "andrew@boost.com"
}

## POST /offers
Request Body (JSON)
{
"name": "NEW2BOOST",
"percentageDiscount": 15.0
}

Response HTTP 200 OK
{
"id":1,
"name":"10% OFF",
"percentageDiscount":10.0
}

## POST /vouchers/create
Request Body (JSON)
{
"recipientEmail": "andrew@boost.com",
"offerName": "NEW2BOOST",
"expirationDate": "2025-12-31"
}

Response HTTP 200 OK
{
"id":1,"code":"6364ECF6",
"recipient":{"id":3,"name":"andrew","email":"andrew@boost.com"},
"specialOffer":{"id":2,"name":"NEW2BOOST","percentageDiscount":15.0},
"expirationDate":"2025-12-31","usedAt":null
}

## POST /vouchers/redeem/{code}
Response HTTP 200 OK
{
"id":1,"code":"6364ECF6",
"recipient":{"id":3,"name":"andrew","email":"andrew@boost.com"},
"specialOffer":{"id":2,"name":"NEW2BOOST","percentageDiscount":15.0},
"expirationDate":"2025-12-31",
"usedAt":"2025-05-01T01:11:48.8592583"
}




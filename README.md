# ADR Waybill

### I is a new version ADR List [**https://github.com/mszyba/adr-lists**]

## Introduction
Application for the preparation of the bill of lading for the transport of hazardous materials (ADR Transport)
User can log in to app, add new Customer, prepare document to transport, generate to .pdf, check list of documnets.

## Technologies
Project is created with:
* Java 8
* Spring Boot
* Spring MVC
* Spring Data
* Spring Security
* Hibernate
* Thymeleaf
* MSQL
* Docker

## How to check
You can check application here

**http://adr-waybill.michalszyba.eu/**

I used own VPS Ubuntu, nginx and Docker to run app.

## How to run
Clone repository
```
git clone https://github.com/mszyba/adr-waybill.git
cd adr-waybill
```

prepare `.jar` file

`mvn clean package -dSkipTests`

run docker-compose

`docker-compose up`

check `http://localhost:8092/register`

##### License
<sub><sup>Copyright (C) 2022 Michał Szyba <m.szyba@gmail.com>.</sup></sub>

# Spring Boot Security example

In this example, we secure the app with Basic authentication and Form based authentication

Authentication Provider for the application is an InMemoryAuthentication with delegating password encoder. From spring-security 5 we need to provide a PasswordEncoder @Bean

#How to use App

Run the application, it will start at http://localhost:8087/

username: admin
password: admin

### Basic Authentication

Create HTTP request to endpoint http://localhost:8087/api/whoami with Authorization Basic header.

### Form based login

Open up http://localhost:8087/ in browser and complete login form.
Upon successful authentication, you'll be redirected to http://localhost:8087/api/whoami

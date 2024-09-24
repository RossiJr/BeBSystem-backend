# B&B System

**This is the repository of the back-end for B&B System, a system which manages reservations for Bed and Breakfast businesses.
Along this repository, is possible to find the endpoints (APIs) that handle user authentication, reservation management and room availability tracking. 
(Soon inventory systems will be also available)**

![GitHub license](https://img.shields.io/github/license/RossiJr/BeBSystem-backend)


## Table of Contents
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Getting started

These instructions will help you to set up the backend project locally for development or testing purposes.
Make sure you have the right version of the technologies used.
>Also, a Docker build file will be available for download and use. Meanwhile, make sure to have the necessary requirements to run the system. 

###  Prerequisites
- Java: version 22 or higher
- Spring Boot: version 3.3.2 or higher
- Maven
- PostgreSQL (newest version)

> If you want, you can use another database of your choice, just make sure to adjust some native queries that my differ.

## Installation
The next steps will show how to install and run B&B System. If you have any problems, do not hesite in communicating the [repository owner](https://github.com/RossiJr).

1. Clone the repository:
	```bash
	git clone https://github.com/RossiJr/BeBSystem-backend.git
	cd BeBSystem-backend
	```

2. Set up the database you are going to use (PostgreSQL is recommended):
	- Create a PostgreSQL database (or you can use the default database, but it is not recommended)
	- Save the `username` and `password` for this database

3. Set up the environment variables
	- If you check, in the `application.properties` file is being used environment variables to connect to the database
	- You have two choices, either you can override those values in the file, or you can keep it and define those values as environment variables when running. 
	- The values are the following:
		```properties
		spring.datasource.url=jdbc:postgresql://{databaseUrl}:{databasePort}/{databaseName}
		spring.datasource.username={yourDbUsername}
		spring.datasource.password={yourDbPassword}
		```
		> If you have any question about how this works, you can check up on [this guide](https://spring.io/guides/gs/accessing-data-mysql#_preparing_to_build_the_application) that explains how to set up those variables.

4. Build and run the application:
	 - To run your spring application using Maven, you can simply use the following command:
	 ```bash
	 mvnw spring-boot:run
	 ```

## Usage

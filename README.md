## ARJ-Stack: Implementaion of Auth Service using SpringBoot, JWT and Swagger

Swagger documentation of Springboot based Auth Service rest API backed by JWT using annotations

- Configure the Database as defined in [SCHEMA](./schema.sql) file.
- Swagger URL - http://localhost:8080/swagger-ui.html

#### APIs exposed
---

#### Registration (Signup)

- <b>URL:</b> http://localhost:8080/signup
- <b>Method:</b> POST
- <b>Payload:</b> JSON
```
{
  "userName": "string",
  "firstName": "string",
  "lastName": "string",
  "password": "string"
}
```

#### Authentication (Signin)

- <b>URL:</b> http://localhost:8080/auth/signin
- <b>Method:</b> POST
- <b>Payload:</b> JSON
```
{
  "userName": "string",
  "password": "string"
}
```

#### Access Management: Change Password

- <b>URL:</b> http://localhost:8080/access/change-password
- <b>Method:</b> POST
- <b>Payload:</b> JSON
```
{
  "currentPassword": "string",
  "newPassword": "string"
}
```

#### Access Management: Refersh Token

- <b>URL:</b> http://localhost:8080/access/refresh-token
- <b>Method:</b> POST
- <b>Payload:</b> JSON
```
{
  "userName": "string",
  "token": "string"
}
```

#### Access Management: Signout

- <b>URL:</b> http://localhost:8080/access/signout
- <b>Method:</b> POST
- <b>Payload:</b> JSON
```
{
  "userName": "string",
  "token": "string"
}
```

#### User Management: Get Users

- <b>URL:</b> http://localhost:8080/management/users
- <b>Method:</b> GET

### Requirements

| Name | Version |
|------|---------|
| <a name="requirement_java"></a> [java](#requirement\_java) | 1.8 |
| <a name="requirement_maven"></a> [maven](#requirement\_maven) | 3.5.3 |
| <a name="requirement_mysql"></a> [mysql](#requirement\_maven) | MySQL Server 5.5 |

### Usages

To run this example you need to execute:

```bash
$ mvn clean install
$ java -jar target/auth-service-1.0.0.jar
$ terraform apply
```

### Authors

Module is maintained by [Ankit Jain](https://github.com/ankit-jn) with help from [these professional](https://github.com/ankit-jn/auth-service-with-springboot-jwt-swagger/graphs/contributors).

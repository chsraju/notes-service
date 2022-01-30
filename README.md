# notes-service
This application uses PostgreSQL

**DB Info:** 
  DB Instance Name = notesdb,
  schema = public,
  usename/passwrd = note_user/note_user
  
The flywaydb being used to create a schema with data  

The context path of service: /Notes
The swagger URL of the application

  http://localhost:8080/Notes/swagger-ui/index.html

  The swagger has full documentaiton of Notes API CRUD API's and also provide the cURL commands

The CRUD operations for the Note require the Bearer Token. The /token API provides the token to access Notes API
This Bearer Token is generated from user email, password and secret. 
The Intereceptor in Notes Service will decrypt the token for the userId.
The userId is sensirive field and is not being exposed any of the Note API path parameters

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

  The swagger has full documentaiton and cURL commands

The CRUD operations for the Note require the Bearer Token. The /token API provides the token to access Notes API

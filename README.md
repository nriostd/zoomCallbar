# zoomCallbar

Downloads
* JDK 1.8 or later <https://www.oracle.com/java/technologies/javase-downloads.html>
* Maven 3.2+ <https://www.java.com/en/download/>
* You could also follow along this tutorial to set up IntelliJ IDEA <https://spring.io/guides/gs/intellij-idea/>

In order to run the code, download the repository and confirm the above required resources. 
From the Command Line, open the project directory and run code `./mvnw spring-boot:run` 

Several methods are run and printed out in a command line runner, which should populate the window. While the application is being hosted on your local computer, you can test the following endpoints in another tab or import the following lines into Postman:
* `curl --location --request GET 'localhost:8080/bespoke/zoom/agent'`
* `curl --location --request POST 'localhost:8080/bespoke/zoom/notify' \
--header 'Content-Type: application/json' \
--data-raw '{
   "event": "user.updated",
   "payload": {
       "account_id": "lAA_EBBBBBBB",
       "operator": "shrija2016+dev_ma@gmail.com",
       "operator_id": "uLobbbbbbbb_qQsQ",
       "object": {
           "id": "KfUi5raET_aHM5IYpIKLZg",
           "email": "fcooper@email.com",
           "type": "3"
       },
       "old_object": {
           "id": "uLobbbbbbbb_qQsQ",
           "email": "kevin.pierson@talkdesk.com"
       }
   }
}
'`

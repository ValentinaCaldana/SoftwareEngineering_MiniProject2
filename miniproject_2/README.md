# miniproject_2
**Team members:**
- Rocco Saracino
- Valentina Caldana

**All Minimum Requirements are implemented:**
- Server is able to serve multiple clients
- All API commands implemented:
    - CreateLogin
    - Login
    - ChangePassword
    - Logout
    - CreateToDo
    - GetToDo
    - DeleteToDo
    - LisToDo
    - Ping
- Console based application

**The optional Features below are implemented:**
- Supports due dates
- Data validation on the server:
    - Username is an email address
    - Minimum string length
    - Due dates today or in the future
- Hashing passwords:
    - A one-way hash funtion to store and compare passwords is used
- Real tokens for user logins are used
- Save and restore data:
    - Saves data to a file
    - Reads saved data when server starts
    - Saves accounts and ToDo-entries

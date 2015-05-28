### Google spreadsheet API sample with oauth2

1. go to [google developers console](https://console.developers.google.com/project)

  1.1 create new project
  
  1.2 disable all enabled APIs 
  
  (because of Google Spreadsheet API is no *popular* Google Apps API)
  
  1.3 create new client ID for native application
  
  1.4 fetch *client_secrets.json*
  
  It will contains *Client ID* and *Client Secret*

2. clone this repository

 2.1 download and copy *client_secrets.json* to `src/main/resources`
 
 2.2 add dependency for maven
 
 2.3 run *Sample.launch*

# google spreadsheet api samples
Working java samples for usage of google spreadsheet api in contrast to:
1. [Google Sheets API version 3.0 Dev Guide](https://developers.google.com/google-apps/spreadsheets/?csw=1#authorizing_requests_with_oauth_20).


## spreadsheet OAuth 2.0 (console sample)
The console application asks user to print out names of all spreadsheets on google drive.
Sample shows how to use Google Spreadsheet API v3 (gdata) with OAuth 2.0. 
* Use [playground](https://developers.google.com/oauthplayground/) to play with OAuth 2.0.
* Have a look into good [example](https://templth.wordpress.com/2014/11/19/interacting-with-google-spreadsheet-with-java/) to know how to deal with deprecated OAuth 1.0.


## google sheets api v4 (web sample)
Web application connects with google spreadsheet and reads all data as table. Sample shows how to use Google Sheet API v4.

1. modify application.properties - add spdreadsheed id and sheet name.
2. replace clien_secrets.json with right one
3. Start as normal spring boot application 
4. open brouser: http://localhost:5000/api/sheet

# Cashmanager SERVER
*Java full writen TCP server.*  

### Auth method  
Client : HELLO FROM `sessionId`  
Server : REQUEST PASSWORD  
Client : PASSWORD `password`  
Server : OK / KO

### Command methods
Client (auth) : COMMAND ADD_ART WITH `id_article`  
Server : OK / KO  

### Article get infos    
Client (auth) : COMMAND GET_ART WITH `id_article`  
Server : id#nom#description#imgurl#prix  
  
### Simple PING/PONG  
Client (auth) : PING
Server : PONG

```
Extended commands : 
  * add_art
  * rem_art
  * get_art
  * process_payment 
```

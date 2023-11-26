//
// Client Java pour communiquer avec le Serveur C++ 
// eric lecolinet - telecom paristech - 2015
//

import java.io.*;
import java.net.*;

public class Client
{
 
  static final String DEFAULT_HOST = "localhost";
  static final int DEFAULT_PORT = 3331;
  private Socket sock;
  private BufferedReader input;
  private BufferedWriter output;

 
  public Client() throws UnknownHostException, IOException {
    try {
      sock = new java.net.Socket(DEFAULT_HOST, DEFAULT_PORT);
    }
    catch (java.net.UnknownHostException e) {
      System.err.println("Client: Couldn't find host " + DEFAULT_HOST+":"+DEFAULT_PORT);
      throw e;
    }
    catch (java.io.IOException e) {
      System.err.println("Client: Couldn't reach host "+DEFAULT_HOST+":"+DEFAULT_HOST);
      throw e;
    }
    
    try {
      input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    }
    catch (java.io.IOException e) {
      System.err.println("Client: Couldn't open input or output streams");
      throw e;
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  
  ///
  /// Envoie une requete au server et retourne sa reponse.
  /// Noter que la methode bloque si le serveur ne repond pas.
  ///
  public String send(String request) {
    // Envoyer la requete au serveur
    try {
      request += "\n";  // ajouter le separateur de lignes
      output.write(request, 0, request.length());
      output.flush();
    }
    catch (java.io.IOException e) {
      System.err.println("Client: Couldn't send message: " + e);
      return null;
    }
    
    // Recuperer le resultat envoye par le serveur
    try {
      return input.readLine();
    }
    catch (java.io.IOException e) {
      System.err.println("Client: Couldn't receive message: " + e);
      return null;
    }
  }

  public void closeConnection() {
    try {
        input.close();
        output.close();
        sock.close();
        System.err.println("Client: Connection is closed");

    } catch (IOException e) {
        System.err.println("Client: Couldn't close connection: " + e);
    }
}

}


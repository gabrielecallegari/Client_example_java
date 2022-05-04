package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

public class Client {
    Client(String hostName, int portNumber) {
        Socket echoSocket = null;
        echoSocket=tryConnection(echoSocket,hostName,portNumber);
        PrintWriter out = null;
        out=tryPrintWriter(out,echoSocket);
        System.out.println("Host ready");
        BufferedReader in =null;
        in= tryBufferedReader(in,echoSocket);
        loopForReading(out, in);
    }

    private BufferedReader tryBufferedReader(BufferedReader in, Socket echoSocket){
        try {
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Errore nel bufferedreader");
            return null;
        }
        return in;
    }
    private PrintWriter tryPrintWriter(PrintWriter out, Socket echoSocket){
        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Errore nel printwriter");
            return null;
        }
        return out;
    }
    private Socket tryConnection(Socket echoSocket, String hostName, int portNumber){
        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("Server irraggiungibile! verificare sia acceso! ");
            return null;
        }
        return echoSocket;
    }
    private void loopForReading(PrintWriter out, BufferedReader in) {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        while (true) {
            try {
                if (!((userInput = stdIn.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
                return ;
            }
            out.println(userInput.toUpperCase(Locale.ROOT));
            out.flush();
            try {
                System.out.println("Server: " + in.readLine());
            } catch (IOException e) {
                System.out.println("Errore nella risposta dal server");
                return;
            }
        }
    }




}

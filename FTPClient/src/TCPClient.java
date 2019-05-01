//import java.io.*;
//import java.net.*;
//
//public class TCPClient {
//    public static void main(String[] args) {
//        String sentence;
//        String modifiedSentence;
//
//        try {
//            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//
//            Socket clientSocket = new Socket("ftp://speedtest.tele2.net/", 21);
//
//            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//
//            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//            sentence = inFromUser.readLine();
//
//            outToServer.writeBytes(sentence + '\n');
//
//            modifiedSentence = inFromServer.readLine();
//
//            System.out.println("FROM SERVER: " + modifiedSentence);
//
//            clientSocket.close();
//
//        } catch (IOException e){
//            System.out.println("DÆ VÆRKER ÆKK.");
//        }
//    }
//
//}

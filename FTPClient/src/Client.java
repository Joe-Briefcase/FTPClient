import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Client
{
    private Socket socket;
    private BufferedReader inputFromUser;
    private DataOutputStream out;
    private BufferedReader inputFromServer;
    private FileTransfer fileTransfer;
    private int delay = 1;

    public Client(String address, int port) throws InterruptedException {
        //Makes connection.
        try
        {
            inputFromUser = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(address, port);
            System.out.println("Connected");
            out = new DataOutputStream(socket.getOutputStream());
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(UnknownHostException u)
        {
            u.printStackTrace();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }

        String username = "USER anonymous";
        String password = "PASS s185143dtu@gmail.com";

        String line = "";
        StringBuilder response = new StringBuilder();

        try
        {
            // Start of file download.
            do {
                response.append(inputFromServer.readLine());
                System.out.println(response);
            } while (inputFromServer.ready());

            out.writeBytes(username + '\n');
            System.out.println(username);

            response.setLength(0);
            response.append(inputFromServer.readLine());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            out.writeBytes(password + '\n');
            System.out.println(password);

            response.setLength(0);
            response.append(inputFromServer.readLine());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            line = "PASV";
            out.writeBytes(line + '\n');
            System.out.println(line);

            response.setLength(0);
            do {
                response.append(inputFromServer.readLine());

                if (response.toString().indexOf("227") == 0) {
                    fileTransfer = new FileTransfer(response.toString());
                }
            } while (inputFromServer.ready());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            line = "RETR 1KB.zip";
            out.writeBytes(line + '\n');
            System.out.println(line);

            response.setLength(0);
            do {
                response.append(inputFromServer.readLine());

                if (response.toString().indexOf("150") == 0) {
                    fileTransfer.filePrint();
                }
            } while (inputFromServer.ready());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            line = "QUIT";
            out.writeBytes(line + '\n');
            System.out.println(line);

            response.setLength(0);
            do {
                response.append(inputFromServer.readLine());
                System.out.println(response);
            } while (inputFromServer.ready());

            System.out.println(response);
            // ---

            try
            {
                inputFromUser.close();
                out.close();
                socket.close();
                System.out.println("Connection closed");
            }
            catch(IOException i)
            {
                System.out.println(i);
            }

            // End of file download.
            System.out.println("End of file download. Wait for file upload.");
            TimeUnit.SECONDS.sleep(5);
            // ---



            // Start of file upload.
            System.out.println("Start of file upload.");

            try
            {
                inputFromUser = new BufferedReader(new InputStreamReader(System.in));
                socket = new Socket(address, port);
                System.out.println("Connected");
                out = new DataOutputStream(socket.getOutputStream());
                inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch(UnknownHostException u)
            {
                u.printStackTrace();
            }
            catch(IOException i)
            {
                i.printStackTrace();
            }

            do {
                response.append(inputFromServer.readLine());
                System.out.println(response);
            } while (inputFromServer.ready());

            out.writeBytes(username + '\n');
            System.out.println(username);

            response.setLength(0);
            response.append(inputFromServer.readLine());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            out.writeBytes(password + '\n');
            System.out.println(password);

            response.setLength(0);
            response.append(inputFromServer.readLine());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            line = "PASV";
            out.writeBytes(line + '\n');
            System.out.println(line);

            response.setLength(0);
            do {
                response.append(inputFromServer.readLine());

                if (response.toString().indexOf("227") == 0) {
                    fileTransfer = new FileTransfer(response.toString());
                }
            } while (inputFromServer.ready());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            line = "CWD /upload";
            out.writeBytes(line + '\n');
            System.out.println(line);

            response.setLength(0);
            response.append(inputFromServer.readLine());

            System.out.println(response);
            // ---
            TimeUnit.SECONDS.sleep(delay);
            // ---
            line = "STOR File.txt";

            out.writeBytes(line + '\n');
            System.out.println(line);

            TimeUnit.SECONDS.sleep(delay);

            response.setLength(0);
            do {
                response.append(inputFromServer.readLine());
                System.out.println(response);
            } while (inputFromServer.ready());


            if (response.toString().indexOf("150") == 0) {
                fileTransfer.uploadFile();
            }

            System.out.println("File upload complete.");

            try
            {
                inputFromUser.close();
                out.close();
                socket.close();
                System.out.println("Connection closed");
            }
            catch(IOException i)
            {
                System.out.println(i);
            }


        } catch(IOException i) {
            System.out.println(i);
        }
    }
}

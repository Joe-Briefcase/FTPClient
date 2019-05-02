import java.io.*;
import java.net.Socket;

public class FileTransfer {

    private Socket socket;
    private DataOutputStream out;

    public void uploadFile() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            File file = new File("File.txt");
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();

            do {
                sb.append(fileReader.readLine());
            } while (fileReader.ready());

            out.writeBytes(sb.toString() + '\n');
            System.out.println("File out: " + sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (NullPointerException k){
            k.printStackTrace();
        }
    }

    public void filePrint() throws IOException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            int total = 0;

            StringBuilder file = new StringBuilder();
            do {
                file.append(inputStreamReader.read());
                total++;
            } while (inputStreamReader.ready());

            inputStreamReader.close();
            System.out.println("Read " + total + " bytes");
            System.out.println(file.toString());
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + socket.getInputStream() + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + socket.getInputStream() + "'");
        }
    }

    public FileTransfer(String connection){
        String response = connection;
        String[] strings = response.split("(?<Alpha>[a-zA-Z]*)");

        StringBuilder sbIP = new StringBuilder();
        StringBuilder sbPort = new StringBuilder();
        int commaCount = 0;
        int port1 = 0;
        int port2 = 0;
        int portFinal = 0;

        for (int i = 10; i < strings.length; i++){
            if ((strings[i].charAt(0) >= '0') && (strings[i].charAt(0) <= '9') && commaCount < 4){
                sbIP.append(strings[i]);
            } else if (strings[i].charAt(0) == ',' && commaCount < 4){
                sbIP.append(".");
                commaCount++;
            } else if ((strings[i].charAt(0) >= '0') && (strings[i].charAt(0) <= '9') && commaCount > 3){
                sbPort.append(strings[i]);
            } else if (strings[i].charAt(0) == ',' && commaCount > 3){
                sbPort.append(".");
                commaCount++;
            }
        }

        sbIP.setLength(12);

        String[] strings2 = sbPort.toString().split("\\.");
        port1 = Integer.parseInt(strings2[0]);
        port2 = Integer.parseInt(strings2[1]);
        portFinal = port1 * 256 + port2;

        System.out.println(sbIP.toString());
        System.out.println(sbPort.toString());
        System.out.println(port1 + " " + port2 + " " + portFinal);

        try {
            socket = new Socket(sbIP.toString(), portFinal);
            System.out.println("Connected to File Transfer");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

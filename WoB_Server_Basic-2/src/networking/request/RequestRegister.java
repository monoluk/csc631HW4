package networking.request;

// Java Imports
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Other Imports
import core.GameClient;
import core.GameServer;
import metadata.Constants;
import model.Player;
import networking.response.ResponseLogin;
import networking.response.ResponseRegister;
import utility.DataReader;
import utility.Log;

/**
 * The RequestLogin class authenticates the user information to log in. Other
 * tasks as part of the login process lies here as well.
 */

public class RequestRegister extends GameRequest {

    // Data
    private String version;
    private String user_id;
    private String password;
    // Responses
    private ResponseRegister responseRegister;

    public RequestRegister() {
        responses.add(responseRegister = new ResponseRegister());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        user_id = DataReader.readString(dataInput).trim();
        password = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        Log.printf("User '%s' is registering...", user_id);

        // Checks if the connecting client meets the minimum version required
        if (version.compareTo(Constants.CLIENT_VERSION) >= 0) {
            if (!user_id.isEmpty()) {


                //write to "DB"
                try {
                    FileWriter writer = new FileWriter("MyFile.txt", true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);

                    String player_id = Integer.toString(countLines() + 100) ;
                    //System.out.println("player_id: " + player_id );
                    bufferedWriter.newLine();
                    bufferedWriter.write(player_id);
                    bufferedWriter.newLine();
                    bufferedWriter.write(user_id);
                    bufferedWriter.newLine();
                    bufferedWriter.write(password);

                    bufferedWriter.close();
                    System.out.println("Done writing to DB");
                    responseRegister.setStatus((short) 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            if (player == null) {
//                responseRegister.setStatus((short) 1); // User info is incorrect
//                Log.printf("User '%s' has failed to register.", user_id);
//            }


        } else {
            responseRegister.setStatus((short) 3); // Client version not compatible
            Log.printf("User '%s' has failed to register. (v%s)", user_id, version);
        }
    }

    int countLines(){
        int count = 0;

        try {
            File file = new File("./MyFile.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNext()){
                count ++;
                sc.next();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return count==0? 0 : count/3;
    }
}

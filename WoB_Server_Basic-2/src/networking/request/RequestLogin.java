package networking.request;

// Java Imports
import java.io.*;

// Other Imports
import core.GameClient;
import core.GameServer;
import metadata.Constants;
import model.Player;
import networking.response.ResponseLogin;
import utility.DataReader;
import utility.Log;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * The RequestLogin class authenticates the user information to log in. Other
 * tasks as part of the login process lies here as well.
 */

public class RequestLogin extends GameRequest {

    // Data
    private String version;
    private String user_id;
    private String password;
    // Responses
    private ResponseLogin responseLogin;

    public RequestLogin() {
        responses.add(responseLogin = new ResponseLogin());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        user_id = DataReader.readString(dataInput).trim();
        password = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        Log.printf("User '%s' is connecting...", user_id);
        Player player = null;
        // Checks if the connecting client meets the minimum version required
        if (version.compareTo(Constants.CLIENT_VERSION) >= 0) {
            if (!user_id.isEmpty()) {
                Random rand = new Random();
                int player_id = rand.nextInt(101)+100;

                // Verification Needed
                //player = UsersDAO.getUserFromDbIfCredentialsAreValid(user_id, password);
                // Let's make a fake user for showing a connection demo -- without proper DB set tup.
                Log.printf("User '%s' entered passwd '%s'", user_id, password);

//                if(user_id.equals("ilmi") && password.equals("1111"))
//                    player = new Player(100, "ilmi", "1111", (short) 1, 1000);

                try{
                    File file = new File("./MyFile.txt");
                    Scanner sc = new Scanner(file);

                    while (sc.hasNext()) {
                        String file_player_id = sc.next();
                        System.out.println(file_player_id);
                        String file_id = sc.next();
                        System.out.println(file_id);
                        String pwd = sc.next();
                        System.out.println(pwd);

                        if(file_id.equals(user_id)){
                            if(pwd.equals(password)){

                                player = new Player(Integer.parseInt(file_player_id), user_id, password, (short) 1, 1000);
                                continue;
                            }
                        }
                    }
                }catch(IOException e) {
                    e.printStackTrace();
                }
//                else
//                    player = null;

                //working code
//                if(user_id.equals("test1") && password.equals("1111"))
//                {player = new Player(100, "test1", "1111", (short) 1, 1000);}
//                else if(user_id.equals("test2") && password.equals("1111"))
//                {player = new Player(200, "test2", "1111", (short) 1, 1000);}
//                else{
//                    player = null;}

            }
            if (player == null) {
                responseLogin.setStatus((short) 1); // User info is incorrect
                Log.printf("User '%s' has failed to log in.", user_id);
            } else {
                player.setClient(client);
                if (client.getPlayer() == null || player.getID() != client.getPlayer().getID()) {
                    GameClient thread = GameServer.getInstance().getThreadByPlayerID(player.getID());
                    // If account is already in use, remove and disconnect the client
                    if (thread != null) {
                        responseLogin.setStatus((short) 2); // Account is in use
                        thread.removePlayerData();
                        thread.newSession();
                        Log.printf("User '%s' account is already in use.", user_id);
                    } else {
                        // Continue with the login process
                        GameServer.getInstance().setActivePlayer(player);
                        player.setClient(client);
                        // Pass Player reference into thread
                        client.setPlayer(player);
                        // Set response information
                        responseLogin.setStatus((short) 0); // Login is a success
                        responseLogin.setPlayer(player);
                        Log.printf("User '%s' has successfully logged in.", player.getUsername());
                    }
                }
            }
        } else {
            responseLogin.setStatus((short) 3); // Client version not compatible
            Log.printf("User '%s' has failed to log in. (v%s)", player.getUsername(), version);
        }
    }
}

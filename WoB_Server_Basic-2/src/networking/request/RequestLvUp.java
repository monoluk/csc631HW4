package networking.request;

// Java Imports
import java.io.IOException;

// Other Imports
import core.GameClient;
import core.GameServer;
import metadata.Constants;
import model.Player;
import networking.response.ResponseAddMoney;
import networking.response.ResponseLogin;
import networking.response.ResponseLvUp;
import utility.DataReader;
import utility.Log;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * The RequestLogin class authenticates the user information to log in. Other
 * tasks as part of the login process lies here as well.
 */

public class RequestLvUp extends GameRequest {

    // Data
    private String version;
    private String user_id;
    private String password;
    // Response

    private ResponseLvUp responseLvUp;
    // add new response to responses queue
    public RequestLvUp() {
        responses.add(responseLvUp = new ResponseLvUp());
    }

    @Override
    public void parse() throws IOException {
//        version = DataReader.readString(dataInput).trim();
//        user_id = DataReader.readString(dataInput).trim();
//        password = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        Log.printf("Level upgrading...");
        System.out.println(client.getUserID());
        Player player = client.getPlayer();

        // Checks if the connecting client meets the minimum version required
        //System.out.println(player);
                responseLvUp.setStatus((short) 0); // User info is incorrect
                responseLvUp.setPlayer(player);

                         //Continue with the login process
                        //player.setClient(client);
                         //Pass Player reference into thread
                        //client.setPlayer(player);
                         //Set response information

                        //responseAddMoney.setPlayer(player);
                        responseLvUp.LvUp();
                        Log.printf("Level upgraded for: " + player.getUsername());
                    }

    }


package networking.request;

// Java Imports
import java.io.IOException;

// Other Imports
import core.GameClient;
import core.GameServer;
import core.NetworkManager;
import metadata.Constants;
import model.Player;
import networking.response.ResponseAddMoney;
import networking.response.ResponseLogin;
import utility.DataReader;
import utility.Log;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * The RequestLogin class authenticates the user information to log in. Other
 * tasks as part of the login process lies here as well.
 */

public class RequestAddMoney extends GameRequest {

    // Data
    private String version;
    private String user_id;
    private String password;
    // Response

    private ResponseAddMoney responseAddMoney;
    // add new response to responses queue
    public RequestAddMoney() {
        responses.add(responseAddMoney = new ResponseAddMoney());
    }

    @Override
    public void parse() throws IOException {
//        version = DataReader.readString(dataInput).trim();
//        user_id = DataReader.readString(dataInput).trim();
//        password = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        Log.printf("Adding money...");
        System.out.println(client == null? "Client is  null!!!!" : "Client is not null");
        System.out.println(client.getUserID());
        Player player = client.getPlayer();
        System.out.println(player == null? "Player is  null!!!!" : "Player is not null");

        // Checks if the connecting client meets the minimum version required
        //System.out.println(player);
                responseAddMoney.setStatus((short) 0); // User info is incorrect
                responseAddMoney.setPlayer(player);

                         //Continue with the login process
                        //player.setClient(client);
                         //Pass Player reference into thread
                        //client.setPlayer(player);
                         //Set response information

                        //responseAddMoney.setPlayer(player);
                        responseAddMoney.addMoney();
                NetworkManager.addResponseForAllOnlinePlayers(player.getID(),responseAddMoney);
        //System.out.println("Quese size is: " + client.getUpdates().size());
                        Log.printf("Money has successfully added " + player.getUsername());
                    }

    }


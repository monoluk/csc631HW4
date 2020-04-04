package networking.request;

// Java Imports
import java.io.IOException;

// Other Imports
import networking.response.GameResponse;
import utility.Log;

/**
 * The RequestHeartbeat class is mainly used to release all pending responses
 * the client. Also used to keep the connection alive.
 */
public class RequestHeartbeat extends GameRequest {

    public RequestHeartbeat() {
    }

    @Override
    public void parse() throws IOException {
    }

    @Override
    public void doBusiness() throws Exception {
        System.out.println("client in doBusiness: "+ client.getUserID());
        //System.out.println("client.updates size in doBusinsess: " + client.getUpdatesSize());

        for (GameResponse response : client.getUpdates()) {
            System.out.println("INSIDE response in heartbeat line 29 RequestHeartbeat");
            //LinkedList<GameResponse>, so response is a GameResponse
            try {
                client.send(response); //outputstream.write byte[] to client side
            } catch (IOException ex) {
                Log.println_e(ex.getMessage());
            }
        }
    }
}

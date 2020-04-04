package networking.response;

// Other Imports
import metadata.Constants;
import model.Player;
import utility.GamePacket;

/**
 * The ResponseLogin class contains information about the authentication
 * process.
 */
public class ResponseRegister extends GameResponse {

    private short status;
    //private Player player;

    public ResponseRegister() {
        responseCode = Constants.SMSG_REGISTER;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if (status == 0) {
            packet.addString("Register successful");
        }
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }

//    public void setPlayer(Player player) {
//        this.player = player;
//    }
}
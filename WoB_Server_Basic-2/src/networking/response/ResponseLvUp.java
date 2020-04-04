package networking.response;

// Other Imports
import metadata.Constants;
import model.Player;
import utility.GamePacket;

/**
 * The ResponseLogin class contains information about the authentication
 * process.
 */
public class ResponseLvUp extends GameResponse {

    private short status;
    private Player player;

    public ResponseLvUp() {
        responseCode = Constants.SMSG_LV_UP;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if (status == 0) {
            packet.addInt32(player.getID());
            packet.addString(player.getUsername());
            packet.addInt32(player.getMoney());
            packet.addShort16(player.getLevel());
        }
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void LvUp() {
        int level = this.player.getLevel() + 1;
        this.player.setLevel((short)level);
        //System.out.println("Response.LvUp() line 45");
    }
}
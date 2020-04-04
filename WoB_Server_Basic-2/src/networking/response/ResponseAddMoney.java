package networking.response;

// Other Imports
import metadata.Constants;
import model.Player;
import utility.GamePacket;

/**
 * The ResponseLogin class contains information about the authentication
 * process.
 */
public class ResponseAddMoney extends GameResponse {

    private short status;
    private Player player;

    public ResponseAddMoney() {
        responseCode = Constants.SMSG_ADD_MONEY;
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

    public void addMoney() {
        int money = this.player.getMoney() + 1;
        this.player.setMoney(money);
        System.out.println("new money responseAddMoney line 45");
    }
}
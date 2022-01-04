
package client.interfaces;

import models.GameRequest;
import models.Message;
import models.OnlinePlayers;

/**
 *
 * @author Abdo
 */
public interface InGameInterface {

    void onGameRequestRecive(GameRequest req);

    void onOnlinePlayersRecive(OnlinePlayers players);

    void onMessageRecive(Message msg);
}

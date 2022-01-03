
package models;

/**
 *
 * @author Abdo
 */
public interface InGameInterface {

    void onGameRequestRecive(GameRequest req);

    void onOnlinePlayersRecive(OnlinePlayers players);

    void onMessageRecive(Message msg);
}

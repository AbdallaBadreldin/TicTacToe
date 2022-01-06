package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameSession extends Thread {

    
    ClientHandler playerOne;
    ClientHandler playerTwo;
    public boolean turn;
    Game game;

    public GameSession(ClientHandler playerOne, ClientHandler playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        game = new Game();
    }

    @Override
    public void run() {
        try {
            while (game.isGameEnd) {
                System.out.println("Game session Thread ");
                //player one have to play
                if (playerOne.objectInputStream.readObject() != null) {
                    Object move = playerOne.objectInputStream.readObject();
                    if (move instanceof PlayerMove) {
                        System.out.println(move.toString());
                        playerTwo.objectOutputStream.writeObject(move);
                        playerTwo.objectOutputStream.flush();
                        game.playersMoves[game.counter] = (PlayerMove) move;

                        game.pmfc[((PlayerMove) move).getX()][((PlayerMove) move).getY()] = (PlayerMove) move;
                        game.counter++;
                        game.isPlayerOneTurn = !game.isPlayerOneTurn;
                    }
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameSession.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    public void addMove(PlayerMove move) {
        game.playersMoves[game.counter] = move;
        game.counter++;
        game.isPlayerOneTurn = !game.isPlayerOneTurn;

    }

    public PlayerMove[] getPlayersMoves() {
        return game.playersMoves;
    }

    public void setPlayersMoves(PlayerMove[] playersMoves) {
       game.playersMoves = playersMoves;
    }

    public int getCounter() {
        return game.counter;
    }

    public void setCounter(int counter) {
        game.counter = counter;
    }

    public ClientHandler getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(ClientHandler playerOne) {
        this.playerOne = playerOne;
    }

    public ClientHandler getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(ClientHandler playerTwo) {
        this.playerTwo = playerTwo;
    }

    public boolean isIsPlayerOneTurn() {
        return game.isPlayerOneTurn;
    }

    public void setIsPlayerOneTurn(boolean isPlayerOneTurn) {
        game.isPlayerOneTurn = isPlayerOneTurn;
    }

    @Override
    public String toString() {
        return "GameSession{" + "counter=" + game.counter + ", playerOne=" + playerOne + ", playerTwo=" + playerTwo + ", playersMoves=" + game.playersMoves + ", isPlayerOneTurn=" + game.isPlayerOneTurn + ", isGamerunning=" + game.isGameEnd + '}';
    }

    private void checkState() {
        checkRows();
        checkColumns();
        checkDiagonal();
        if (game.firstPlayerWinner) {
            game.isGameEnd = true;
            //TODO end game 
            //TODO send player one win
            // TODO send player two lose
            System.out.println("Player One win");
        } else if (game.secondPlayerWinner) {
            //TODO end game 
            //TODO send player one lose
            // TODO send player two win
            game.isGameEnd = true;
            System.out.println("Player Two win");
        } else {
            if ((isFullGrid())) {
                game.isGameEnd = true;
                //TODO send draw
                //TODO send draw for both players 
                System.out.println("Draw");
            }
        }
    }

    private void checkRows() {

        if (game.pmfc[0][0] != null && game.pmfc[0][1] != null && game.pmfc[0][2] != null
                && game.pmfc[0][0].isIsX() == game.pmfc[0][1].isIsX()
                && game.pmfc[0][1].isIsX() == game.pmfc[0][2].isIsX()) {

            if (game.pmfc[0][0].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

        if (game.pmfc[1][0] != null && game.pmfc[1][1] != null && game.pmfc[1][2] != null
                && game.pmfc[1][0].isIsX() == game.pmfc[1][1].isIsX()
                && game.pmfc[1][1].isIsX() == game.pmfc[1][2].isIsX()) {

            if (game.pmfc[1][0].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

        if (game.pmfc[2][0] != null && game.pmfc[2][1] != null && game.pmfc[2][2] != null
                && game.pmfc[2][0].isIsX() == game.pmfc[2][1].isIsX()
                && game.pmfc[2][1].isIsX() == game.pmfc[2][2].isIsX()) {

            if (game.pmfc[2][0].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

    }

    private void checkColumns() {

        if (game.pmfc[0][0] != null && game.pmfc[1][0] != null && game.pmfc[2][0] != null
                && game.pmfc[0][0].isIsX() == game.pmfc[1][0].isIsX()
                && game.pmfc[1][0].isIsX() == game.pmfc[2][0].isIsX()) {

            if (game.pmfc[0][0].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

        if (game.pmfc[0][1] != null && game.pmfc[1][1] != null && game.pmfc[2][1] != null
                && game.pmfc[0][1].isIsX() == game.pmfc[1][1].isIsX()
                && game.pmfc[2][1].isIsX() == game.pmfc[2][1].isIsX()) {

            if (game.pmfc[0][1].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

        if (game.pmfc[0][2] != null && game.pmfc[1][2] != null && game.pmfc[2][2] != null
                && game.pmfc[0][2].isIsX() == game.pmfc[1][2].isIsX()
                && game.pmfc[1][2].isIsX() == game.pmfc[2][2].isIsX()) {

            if (game.pmfc[0][2].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

    }

    private void checkDiagonal() {
        if (game.pmfc[0][0] != null && game.pmfc[1][1] != null && game.pmfc[2][2] != null
                && game.pmfc[0][0].isIsX() == game.pmfc[1][1].isIsX()
                && game.pmfc[1][1].isIsX() == game.pmfc[2][2].isIsX()) {

            if (game.pmfc[0][0].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

        if (game.pmfc[0][2] != null && game.pmfc[1][1] != null && game.pmfc[2][0] != null
                && game.pmfc[0][2].isIsX() == game.pmfc[1][1].isIsX()
                && game.pmfc[1][1].isIsX() == game.pmfc[2][0].isIsX()) {

            if (game.pmfc[0][2].isIsX()) {
                game.firstPlayerWinner = true;
                game.secondPlayerWinner = false;
            } else {
                game.secondPlayerWinner = true;
                game.firstPlayerWinner = false;
            }
        }

    }

    private boolean isFullGrid() {
        /*
        if (!btn00.getText().equals("")
                && !btn01.getText().equals("")
                && !btn02.getText().equals("")
                && !btn10.getText().equals("")
                && !btn11.getText().equals("")
                && !btn12.getText().equals("")
                && !btn20.getText().equals("")
                && !btn21.getText().equals("")
                && !btn22.getText().equals("")) {
            return true;
        } else {
            return false;
        }*/

        return false;
    }

}

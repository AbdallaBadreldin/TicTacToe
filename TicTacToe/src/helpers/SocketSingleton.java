/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmoud
 */
public class SocketSingleton {

    private static Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    public static synchronized Socket getInstanceOf(String ip) {
        if (socket == null || socket.isClosed()) {

            try {
                socket = new Socket(ip, 5006);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                objectOutputStream = new ObjectOutputStream(outputStream);
            } catch (ConnectException ex) {
                System.out.println("problem in server");
            } catch (SocketException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return socket;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static void closeStreams() {
        try {
            objectInputStream.close();
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeSocket() {
        try {
            
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

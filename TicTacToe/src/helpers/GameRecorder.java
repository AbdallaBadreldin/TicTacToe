package helpers;

import com.google.gson.Gson;
import controller.RecordesScreenController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GameSession;
import models.ListOfRecorders;

/**
 *
 * @author Abdo
 */
public class GameRecorder {

    private final static String PATH = System.getProperty("user.home") + "\\Documents\\save.json";
    private Gson mGson = new Gson();
    private File f = new File(PATH);

    public void writer(GameSession session) {
        try {
            f = new File(PATH);
            ListOfRecorders listOfRecorders = reader();
            listOfRecorders.getGameSession().add(session);
            String strJson = mGson.toJson(listOfRecorders);
            FileWriter fileWriter = new FileWriter(PATH);
            fileWriter.write(strJson);
            fileWriter.close();
            System.out.println("writing done");
        } catch (IOException ex) {
            Logger.getLogger(RecordesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ListOfRecorders reader() {
        ListOfRecorders list = null;
        try {
            if (f.exists() && !f.isDirectory()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH));
                list = mGson.fromJson(bufferedReader, ListOfRecorders.class);
                System.out.println("reader no file");
            } else {

                try {
                    list = new ListOfRecorders();
                    f.createNewFile();
                    FileWriter fw = new FileWriter(f.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    String strJ = mGson.toJson(new ListOfRecorders());
                    bw.write(strJ);
                    bw.close();
                } catch (IOException ex) {
                    Logger.getLogger(GameRecorder.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("creating file");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecordesScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}

package sample;

import common.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javax.swing.text.html.ListView;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class Controller {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String nick;
    private ListView clientsList;
//    Socket socket = null;

    @FXML
    TextField loginfield;

    @FXML
    PasswordField passwordField;

    @FXML
    HBox AuthPanel;

    @FXML
    HBox cmdPanel;

    @FXML
    ListView<String> mainList;


    public void setNick(String nick) {
        if (nick == null) {
            // включаем панель авторизации
            // включаем панель авторизации

            authPanel.setVisible(true); // выключаем панель отправки сообщений
            authPanel.setManaged(true);
            cmdPanel.setVisible(false);
            cmdPanel.setManaged(false);
        } else {
            authPanel.setVisible(false); // выключаем панель отправки сообщений
            authPanel.setManaged(false);
            cmdPanel.setVisible(true);
            cmdPanel.setManaged(true);
        }
    }

    {

        try {

            {
                socket = new Socket("localhost", 8189);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {

//                    AuthMessage authMessage = new AuthMessage("login", "pass");
//                    out.writeObject(AuthMessage);
                    try {


                        while (true) {
                            AbstractMessage abstractMessage = (AbstractMessage) in.readObject();
                            if (abstractMessage instanceof CommandMessage) {
                                CommandMessage cm = (CommandMessage) abstractMessage;
                                if (cm.getCmd() == CommandMessage.AUTH_OK) {
                                    String mynick = (String) cm.getAttachment()[0];
                                    break;
                                }
                            }
                            FileDataMessage fdm = new FileDataMessage("client/ 123.txt");
                            while (true) {
                                AbstractMessage abstractMessage = (AbstractMessage) in.read(Object);
                                if (AbstractMessage instanceof FileDataMessage) ;
                                FileDataMessage fmd = (FileDataMessage) abstractMessage;
                                Files.write(Paths.get(nick + "/" + fmd.getFilename()), fmd.getdata()), StandartOpenOption.CREATE_NEW();
                                for (int i = 0; i < flm.getfiles().size(); i++) {
                                    System.out.println(flm.getfiles().get(i));

                                }
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
//                t.setDaemon(true);
///                t.start();
//            } catch(IOException e){
//                e.printStackTrace();
            }
        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendAuthMsg() {
        if (this.socket == null || this.socket.isClosed()) {
//            connect();
        }
        AuthMessage am = new AuthMessage(loginfield.getText(), passwordField.getText());
//        authLogin.clear();
//        authPass.clear();
        try {
            out.writeObject(am);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//        public void sendAuthMsg (String login, String passw)
//
//    }


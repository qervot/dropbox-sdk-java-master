package server;

import common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClientHandler {
    private String nick;
    private Server server;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectInputStream out;


    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        
            new Thread(new Runnable() {
                @Override

                public void run() {
                    try {
                        while (true) {// Запускаем цикл авторизации
                            AbstractMessage am = (abstractMessage) in.read.Object();
                            if (abstractMessage instanceof AuthMessage) {
                                AuthMessage am = (AuthMessage) abstractMessage;
                                String nickfromDB = SqlHandler.getNickByLoginPass(am.getLogin(), am.getPassword());// Запрашиваем у сервиса авторизации ник по логину паролю
                                if (nickfromDB != null) ;// если пришел не пустой ник из сервиса авторизации
                                nick = nickfromDB;// присваиваем обработчику клиента ник
                                sendMessage(new CommandMessage(CommandMessage.AUTH_OK, nick));
                                sendMessage(getFileStructureMessage());
                                break;// выходим из цикла авторизации
                            }

                        }

                        while (true) {
                            AbstractMessage abstractMessage = (AbstractMessage) in.read(Object);
                            if (AbstractMessage instanceof FileDataMessage) ;
                            FileDataMessage fmd = (FileDataMessage) abstractMessage;
                            Files.write(Paths.get(nick + "/" + fmd.getFilename()), fmd.getdata()), StandartOpenOption.CREATE_NEW();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public FileListMessage getFileStructureMessage() {
        return new FileListMessage(getFileList());
    }


    public List<String> getFileList() {
        List<String> files = new ArrayList<>();
        try {
            Files.newDirectoryStream(Paths.get("server/storage/" + nick)).forEach(path -> files.add(path.getFileName().toString()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }


    public void sendMessage(AbstractMessage abstractMessage) {
        try {
            out.writeObject(abstractMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
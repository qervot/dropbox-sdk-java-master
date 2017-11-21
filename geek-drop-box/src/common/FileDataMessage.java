package common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDataMessage extends AbstractMessage {
    private String filename;
    private long size;
    private byte[] data;


    public FileDataMessage(String filename) {
        try {


            this.filename = filename;
            this.size = Files.size(Paths.get(filename));
            this.data = Files.readAllBytes(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

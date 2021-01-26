import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStorage implements Initializable {

    final String IP_ADDRESS = "localhost";
    final int Port = 8189;
    private static final Logger LOG = LoggerFactory.getLogger(ClientStorage.class);
    public ListView <String> listViewClient;
    public ListView <String> listViewServer;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    public ListView<String> listView;


    public TextField text;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket(IP_ADDRESS, Port);
            GotoNet.getClientConnect().start();
            new Thread(() -> {
                while (true) {
                    try {
                        Path path = Paths.get("testFiles.testfile.txt");
                        QuerySender.uploadFile(path,GotoNet.getClientConnect().getCurrentChannel());
                        listViewServer.getItems().add(path.toString());
                    } catch (Exception e) {
                        LOG.error("e = ", e);
                        break;
                    }
                }
            }).start();
        } catch (Exception e) {
            LOG.error("e = ", e);
        }
    }


    public void upload(ActionEvent actionEvent) {

    }

    public void download(ActionEvent actionEvent) {
    }

    public void refreshClientStorage (String file){
        listViewClient.getItems().add(file);
    }
}

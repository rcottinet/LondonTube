package UIInterface;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import service.ServiceSqlRequest;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.*;

import static java.awt.Event.ENTER;


public class Controller implements Initializable{



    @FXML
    private TextField stationFrom;

    @FXML
    private TextField stationTo;

    @FXML
    Button submit;


    @FXML
    Scene scene;
    @FXML
    Window window;

    @FXML
    Stage stage = (Stage) window;

    @FXML
    Label station = new Label();



    Set<String> possibleWordSet = new HashSet<>();
    private AutoCompletionBinding<String> autoCompletionBinding;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServiceSqlRequest base = new ServiceSqlRequest();
        ArrayList<String> listStationPossible = base.getListNameAllStation();


        autoCompletionBinding = TextFields.bindAutoCompletion(stationFrom, listStationPossible);

        stationFrom.setOnKeyPressed((e) -> {
                switch(e.getCode()) {
                    case ENTER:
                        learnWorld(stationFrom.getText());
                        break;
                    default:
                        break;
                }
        });

        autoCompletionBinding = TextFields.bindAutoCompletion(stationTo, listStationPossible);


        stationTo.setOnKeyPressed((e) -> {
            switch(e.getCode()) {
                case ENTER:
                    learnWorld(stationTo.getText());
                    break;
                default:
                    break;
            }
        });
    }

    private void learnWorld(String text) {
        possibleWordSet.add(text);

        if(autoCompletionBinding != null){
            autoCompletionBinding.dispose();

        }

        autoCompletionBinding = TextFields.bindAutoCompletion(stationFrom, possibleWordSet);
    }

    @FXML
    public void buttonSubmitPressed() throws IOException
    {


         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("displayPath.fxml"));

        //Parent displayPathParent = FXMLLoader.load(getClass().getResource("displayPath.fxml"));

        stage = (Stage) submit.getScene().getWindow();
        scene = new Scene(fxmlLoader.getRoot());
        stage.setScene(scene);

        //station.setLabelFor();

        //System.out.println(stationFrom.getText());



    }







    /*public void buttonSubmitPressed(ActionEvent event) throws IOException{

        //window = primaryStage;
        //String stationFromText = stationFrom.getText();
        //String stationToText = stationTo.getText();



            Parent displayPathParent = FXMLLoader.load(getClass().getResource("displayPath.fxml"));
            Scene displayPath = new Scene(displayPathParent);

            //Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            setScene(displayPath);
            window.show();








        //System.out.printf("Station from : %s  Station To : %s ", stationFromText, stationToText);

    }*/



}

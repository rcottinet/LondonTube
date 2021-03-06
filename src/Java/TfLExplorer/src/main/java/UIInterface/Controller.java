package UIInterface;

import MainApp.Itinerary;
import MainApp.Node;
import com.jfoenix.controls.JFXListView;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import service.ServiceSqlRequest;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.*;

import static java.awt.Event.ENTER;

import MainApp.DijkstraAlgo;

import javax.swing.text.html.ImageView;



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
    Label station;

    @FXML
    ListProperty<String> listProperty = new SimpleListProperty<>();

    @FXML
    ListView stationsList;

    @FXML
    Label timeStation;

    @FXML
    Accordion listViews;

    @FXML
    TitledPane suggeredPath;

    @FXML
    Label noStation;




    Set<String> possibleWordSet = new HashSet<>();
    private AutoCompletionBinding<String> autoCompletionBinding;


    /**
     * Initialize the stage and the request for the autocomplete field
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        noStation.setVisible(false);

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


    /**
     * This function control the button clicked and launch the search method for the quickest path
     * @throws IOException
     */


    @FXML
    public void buttonSubmitPressed() throws IOException {


        stationsList.getItems().clear();

        if (!stationFrom.getText().equals(stationTo.getText())){

            noStation.setVisible(false);


            /**
             * Get an Itinary Object with dijkstra algorithm from the MainApp package
             *
             * It contains the time, the path, the departure station and the arrival station
             *
             */

            Itinerary itinerary = new Itinerary(stationFrom.getText(), stationTo.getText());

            String previousline = null;



            for (Node station : itinerary.path) {
                if (previousline == station.line) {
                    stationsList.getItems().add("|  |     " + station.value + " - " + station.line);

                } else if (previousline == null) {
                    stationsList.getItems().add("|o|     " + station.value + " - " + station.line);


                } else if (previousline != station.line && previousline != null) {
                    stationsList.getItems().add("      ! ----- Change at this station ----- !");
                    stationsList.getItems().add("|o|     " + station.value + " - " + station.line);
                }

                previousline = station.line;


            }


                double time = (int) itinerary.time + (itinerary.time - (int) itinerary.time) * 60 * 0.01;

                listViews.getPanes().set(0, suggeredPath);


                suggeredPath.setText("Suggered Path        " + (int) time + " min " + (int) ((time - (int) time) * 100) + " sec ");


        }else{

            /** In the case when the user write the same station for departure and arrival */

            System.out.println("Can't write the same station");
            noStation.setVisible(true);

        }


    }

}

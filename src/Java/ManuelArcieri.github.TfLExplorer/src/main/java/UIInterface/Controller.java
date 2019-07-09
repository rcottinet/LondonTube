package UIInterface;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import MainApp.DijkstraAlgo;

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

     ArrayList<String> stations = new ArrayList<>();







    @Override
    public void initialize(URL url, ResourceBundle rb) {


        stations.add("Marylebone");
        stations.add("Baker Street");
        stations.add("Oxford Circus");



        //stationsList.itemsProperty().bind(listProperty);

        //listProperty.set(FXCollections.observableArrayList(stations));


    }


    @FXML
    public void buttonSubmitPressed() throws IOException
    {


       // System.out.println("%s : %s" stationFrom.getText(), stationTo.getText());




        stationsList.getItems().addAll(DijkstraAlgo.finalDirection(stationFrom.getText(), stationTo.getText()));


      // listProperty.set(FXCollections.observableArrayList(stations));







    }




}

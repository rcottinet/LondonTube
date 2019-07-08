package UIInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Controller {


    @FXML
    private TextField stationFrom = new TextField();

    @FXML
    private TextField stationTo = new TextField();

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

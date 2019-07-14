package UIInterface;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {


    public void start(Stage primaryStage) throws Exception{




        Parent root = FXMLLoader.load(getClass().getResource("mainInterface.fxml"));
        primaryStage.setTitle("London Tube");
        primaryStage.setScene(new Scene(root, 755, 600));
        primaryStage.show();









    }



    public static  void main(String[] args){
        launch(args);
    }



}

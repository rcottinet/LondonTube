package UIInterface;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    Button buttonSubmit;


    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainInterface.fxml"));
        primaryStage.setTitle("London Tube");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


        //buttonSubmit = new Button("submitbutton");
        //buttonSubmit.setOnAction(e -> System.out.println(stationFrom.getText()));






    }



    public static  void main(String[] args){
        launch(args);
    }



}

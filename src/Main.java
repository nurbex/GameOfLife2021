import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String args[]){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/gui/GameOfLife2021.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("GameOfLife2021");
            primaryStage.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}

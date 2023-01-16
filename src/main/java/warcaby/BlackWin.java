package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BlackWin extends Application {

    static Stage classStage = new Stage();

    @Override
    public void start(Stage stage) {
        GridPane pane = new GridPane();
        Text text = new Text("Black wins!");
        text.setFont(Font.font("Times New Roman", 80));
        pane.setVgap(40);
        pane.add(text,1,1);
        Scene scene = new Scene(pane,400,250);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
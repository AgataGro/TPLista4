package warcaby;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LaunchingCheckers extends Application {

    @Override
    public void start(Stage stage) {
        GridPane pane = new GridPane();
        Font font = new Font("Times New Roman",25);

        Text text = new Text("Choose an option:\n");
        text.setFont(font);

        Button polishButton = new Button("Polish Checkers");
        polishButton.setFont(font);
        polishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PolishCheckers ctc = new PolishCheckers();
                ctc.start(PolishCheckers.classStage);
                stage.hide();
            }
        });

        Button englishButton = new Button("Engish Checkers");
        englishButton.setFont(font);
        englishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EnglishCheckers ctc = new EnglishCheckers();    
                ctc.start(EnglishCheckers.classStage);
                stage.hide();
            }
        });

        Button turkishButton = new Button("Turkish Checkers");
        turkishButton.setFont(font);
        turkishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TurkishCheckers ctc = new TurkishCheckers();    
                ctc.start(TurkishCheckers.classStage);
                stage.hide();
            }
        });

        pane.add(text,1,1);
        pane.add(polishButton,1,2);
        pane.add(englishButton,1,3);
        pane.add(turkishButton,1,4);
        Scene scene = new Scene(pane,230,220);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args array with given parameters
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
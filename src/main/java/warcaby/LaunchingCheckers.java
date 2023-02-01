package warcaby;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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

        CheckBox withBot = new CheckBox("vs CPU");
        withBot.setFont(font);
        withBot.setSelected(false);
        Label l = new Label();
        l.setDisable(false);
        withBot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                l.setDisable(withBot.isSelected());
            }
        });

        Button polishButton = new Button("Polish Checkers");
        polishButton.setFont(font);
        polishButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                PolishCheckers ctc;
                if(l.isDisable()){
                    ctc = new PolishCheckers(true);
                    System.out.println("Polish bot");
                }
                else{
                    ctc = new PolishCheckers(false);
                    System.out.println("Polish player");
                }

                ctc.start(PolishCheckers.classStage);
                stage.hide();
            }
        });

        Button englishButton = new Button("English Checkers");
        englishButton.setFont(font);
        englishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EnglishCheckers ctc;
                if(l.isDisable()){
                    ctc = new EnglishCheckers(true);
                    System.out.println("English bot");
                }
                else{
                    ctc = new EnglishCheckers(false);
                    System.out.println("English player");
                }
                ctc.start(EnglishCheckers.classStage);
                stage.hide();
            }
        });

        Button turkishButton = new Button("Turkish Checkers");
        turkishButton.setFont(font);
        turkishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TurkishCheckers ctc;
                if(l.isDisable()){
                    ctc = new TurkishCheckers(true);
                    System.out.println("Turkish bot");
                }
                else{
                    ctc = new TurkishCheckers(false);
                    System.out.println("Turkish player");
                }
                ctc.start(TurkishCheckers.classStage);
                stage.hide();
            }
        });

        pane.add(text,1,1);
        pane.add(polishButton,1,2);
        pane.add(englishButton,1,3);
        pane.add(turkishButton,1,4);
        pane.add(withBot,1,5);
        Scene scene = new Scene(pane,230,250);
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
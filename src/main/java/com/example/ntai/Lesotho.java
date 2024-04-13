package com.example.ntai;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.InputStream;

public class Lesotho extends Application {

    private String[] questions = {
            "What is the capital of Lesotho?",
            "What is the highest peak in Lesotho?",
            "What is the official language of Lesotho?",
            "Which river forms part of Lesotho's northern border?",
            "What is the traditional attire of Lesotho called?"
    };

    private String[] imageFilenames = {
            "LETSIE2.jpeg",
            "letsie_iii.jpg",
            "Marena.jpg",
            "MOSHOESHOE1.jpg",
            "seshoe.jpg",
            "SANI.jpg"
    };

    private String[][] options = {
            {"Maseru", "Manzini", "Gaborone"},
            {"Thabana Ntlenyana", "Mount Afadja", "Kilimanjaro"},
            {"Sesotho", "Zulu", "English"},
            {"Orange River", "Nile River", "Congo River"},
            {"Basotho", "Dashiki", "Lesotho Seshoeshoe"}
    };

    private String[] correctAnswers = {
            "Maseru",
            "Thabana Ntlenyana",
            "Sesotho",
            "Orange River",
            "Lesotho Seshoeshoe"
    };

    private int currentQuestionIndex = 0;
    private int score = 0;
    private int questionsRemaining = questions.length;

    @Override
    public void start(Stage primaryStage)
    {
        VBox startupMenu = new VBox(20);
        startupMenu.setAlignment(Pos.CENTER);
        startupMenu.setPadding(new Insets(20));

        Image startupImage = loadImage("Marena.jpg");
        ImageView startupImageView = new ImageView(startupImage);
        startupImageView.setFitWidth(300);
        startupImageView.setFitHeight(200);
        startupImageView.setPreserveRatio(true);

        Button startGameButton = new Button("Start Game");
        startGameButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        startGameButton.setOnAction(event ->
        {
            primaryStage.setScene(createGameScene());
        });

        startupMenu.getChildren().addAll(startupImageView, startGameButton);

        Scene startupScene = new Scene(startupMenu, 600, 400);
        startupScene.setFill(Color.LIGHTBLUE);

        primaryStage.setScene(startupScene);
        primaryStage.setTitle("Lesotho");
        primaryStage.show();
    }

    private Scene createGameScene()
    {
        Label questionLabel = new Label();
        Label scoreLabel = new Label("Score: 0");
        Label questionsRemainingLabel = new Label("Questions remaining: " + questionsRemaining);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        RadioButton[] optionButtons = new RadioButton[options[0].length];
        ToggleGroup group = new ToggleGroup();

        for (int i = 0; i < optionButtons.length; i++)
        {
            optionButtons[i] = new RadioButton();
            optionButtons[i].setToggleGroup(group);
        }

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        submitButton.setOnAction(event ->
        {
            if (group.getSelectedToggle() != null)
            {
                RadioButton selectedButton = (RadioButton) group.getSelectedToggle();
                String selectedAnswer = selectedButton.getText();
                if (selectedAnswer.equals(correctAnswers[currentQuestionIndex]))
                {
                    score++;
                    showAlert("O nepile", "You got it right!");
                } else {
                    showAlert("O fositse", "The correct answer is: " + correctAnswers[currentQuestionIndex]);
                }
                if (currentQuestionIndex < questions.length - 1)
                {
                    currentQuestionIndex++;
                    questionsRemaining--;
                    displayNextQuestion(questionLabel, imageView, optionButtons);
                    scoreLabel.setText("Score: " + score);
                    questionsRemainingLabel.setText("Questions remaining: " + questionsRemaining);
                } else
                {
                    displayScore();
                }
            } else
            {
                showAlert("Error!", "Please select an answer.");
            }
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(questionLabel, imageView);
        root.getChildren().addAll(optionButtons);
        root.getChildren().add(submitButton);
        root.getChildren().addAll(scoreLabel, questionsRemainingLabel);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY))); // Set background color

        displayNextQuestion(questionLabel, imageView, optionButtons);

        return new Scene(root, 400, 400);
    }

    private Image loadImage(String filename) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/images/" + filename);
            if (inputStream != null)
            {
                return new Image(inputStream);
            } else {
                throw new RuntimeException("Image file not found: " + filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading image: " + filename);
        }
    }

    private void displayNextQuestion(Label questionLabel, ImageView imageView, RadioButton[] optionButtons) {
        try
        {
            if (currentQuestionIndex == questions.length - 1)
            {
                questionLabel.setText(questions[currentQuestionIndex]);
                InputStream inputStream = getClass().getResourceAsStream("/images/seshoe.jpg");
                if (inputStream != null) {

                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } else
                {
                    displayErrorMessage("Image file not found.");
                }
            } else
            {
                questionLabel.setText(questions[currentQuestionIndex]);
                InputStream inputStream = getClass().getResourceAsStream("/images/" + imageFilenames[currentQuestionIndex]);
                if (inputStream != null)
                {
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } else
                {
                    displayErrorMessage("Image file not found.");
                }
            }

            for (int i = 0; i < optionButtons.length; i++)
            {
                optionButtons[i].setText(options[currentQuestionIndex][i]);
                optionButtons[i].setSelected(false);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            displayErrorMessage("Error loading image.");
        }
    }

    private void displayErrorMessage(String message)
    {
        System.err.println(message);
    }

    private void displayScore()
    {
        System.out.println("Quiz completed! Score: " + score + "/" + questions.length);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

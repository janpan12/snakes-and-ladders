import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class Game extends Application {
    
    private Alert alert;
    private ButtonType yes;
    private ButtonType no;
    
    private int gridX = 10;
    private int gridY = 10;
    
    private Scene scene;
    private Pane pane;
    private Button[] grid;
    
    private int diceValue = 0;
    
    private ImageView view = new ImageView(new Image("player.png"));
    private ImageView snake = new ImageView(new Image("snake.png"));
    private ImageView ladder = new ImageView(new Image("ladder.png"));
    
    private int[] snakeIndexes = {12, 33, 47, 63, 88, 95};
    private int[] chuteIndexes = {5, 24, 34, 54, 77, 86};
    
    public Game() {
        // pane = place where to store different elements
        pane = new Pane();
        
        // scene = window of the program
        scene = new Scene(pane, 700, 700);
        grid = new Button[100];
        
        view.setId("Player");
        view.setFitHeight(30);
        view.setFitWidth(30);
        
        snake.setFitHeight(10);
        snake.setFitWidth(10);
        
        ladder.setFitHeight(30);
        ladder.setFitWidth(30);
        
        alert = new Alert(AlertType.CONFIRMATION);
        yes = new ButtonType("Yes", ButtonData.YES);
        no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        double spacingX = scene.getWidth() / gridX;
        double spacingY = scene.getHeight() / gridY;
        int index = 0;
        
        // add buttons
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                // initialize button
                grid[index] = new Button();
                
                // set style
                grid[index].setStyle("-fx-background-color:#FFF;"
                        + "-fx-border-color:#000;" + "-fx-border-width:0.5px;");
                
                // set button coordinates
                grid[index].setLayoutX(spacingX * i);
                grid[index].setLayoutY(spacingY * j);
                
                // set button height/width
                grid[index].setMinHeight(spacingX);
                grid[index].setMinWidth(spacingY);
                
                // add to frame
                pane.getChildren().add(grid[index]);
                
                // increment index
                index++;
            }
        }
        
        // update particular button
        try {
            for (int i = 0; i < grid.length; i++) {
                if (grid[i].getGraphic() == null) {
                    grid[i].setText(""+(i+1));
                }
            }
            grid[0].setGraphic(view);
        } catch (Exception e) { System.out.println(e.getMessage()); };
        
        // TODO: time the dice is rolled, move the player that many spaces
        // to the right of the grid and eventually up
        
        // TODO: draw the chutes and ladders with X and Y positions for each
        // button - put it in the constructor? or some method
        
        // TODO: if the player lands on the start of the ladder, then move the
        // player to the end of the ladder
        for (int i : snakeIndexes) {
            ImageView img = new ImageView(new Image("snake.png"));
            img.setId("Snake");
            img.setFitHeight(30);
            img.setFitWidth(30);
            grid[i].setGraphic(img);
        }

        // TODO: if the player lands on the start of a chute, then move the player
        // to the end of the chute
        for (int i : chuteIndexes) {
            ImageView img = new ImageView(new Image("ladder.png"));
            img.setId("Ladder");
            img.setFitHeight(30);
            img.setFitWidth(30);
            grid[i].setGraphic(img);
        }
        
        // TODO: if the player reaches the 100 tile, then have a dialog box that
        // says the player has won and exit the game when press exit
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        javafx.application.Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                GridPane pane = new GridPane();
                Scene scene = new Scene(pane, 100, 60);
                
                String text = "You rolled: ";
                Button b = new Button("Roll dice");
                Text t = new Text(text);
                
                b.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // set text
                        
                        
                        diceValue = (int) (Math.random() * 5) + 1;
                        t.setText(text + diceValue);
                        
                        // determine index of move
                        int n = move(getIndex(), diceValue);
                        System.out.println("You rolled " + diceValue);
                        
                        // remove image in old
                        grid[getIndex()].setGraphic(null);
                        
                        // set image in new
                        if (n < 0) {
                            grid[99].setGraphic(view);
                            endGame();
                            System.out.println("Congrats! You won!!!\n");
                        } else {
                            if (grid[n].getGraphic() != null) {
                                if (grid[n].getGraphic().getId().equals("Ladder")) {
                                    System.out.println("Hit a ladder: moved up to " + (n+5));
                                    grid[n+5].setGraphic(view);
                                } else {
                                    System.out.println("Hit a snake: moved down to " + (n-5));
                                    grid[n-5].setGraphic(view);
                                }
                            } else {
                                grid[n].setGraphic(view);
                            }
                        }
                    }
                });
                
                // set constraints
                GridPane.setRowIndex(b, 1);
                GridPane.setColumnIndex(b, 1);
                GridPane.setRowIndex(t, 2);
                GridPane.setColumnIndex(t, 1);
                
                pane.getChildren().addAll(b, t);
                stage.setScene(scene);
                stage.show();
            }
        });
    }
    
    private void endGame() {
        alert.setTitle("Snakes and Ladders");
        alert.setHeaderText("Game Over");
        alert.setContentText("Would you like to play again?");
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes){
            grid[0].setGraphic(view);
        } else {
            System.exit(0);
        }
    }
    
    private int move(int start, int value) {
        int temp = start + value;
        return temp <= 99 ? temp : -1;
    }
    
    private int getIndex() {
        int index = 0;
        for (Button b : grid) {
            //System.out.println(b.getId());
            if (b.getGraphic() != null && b.getGraphic().getId().equals("Player")) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
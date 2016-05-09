import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class Game extends Application {
    
    private int gridX = 10;
    private int gridY = 10;
    
    private Scene scene;
    private Pane pane;
    private Button[] grid;
    
    private int diceValue = 0;
    
    private ImageView view = new ImageView(new Image("player.png"));
    
    public Game() {
        // pane = place where to store different elements
        pane = new Pane();
        
        // scene = window of the program
        scene = new Scene(pane, 700, 700);
        grid = new Button[100];
        
        view.setFitHeight(30);
        view.setFitWidth(30);
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
            grid[0].setGraphic(view);
        } catch (Exception e) { System.out.println(e.getMessage()); };
        
        // TODO: time the dice is rolled, move the player that many spaces
        // to the right of the grid and eventually up
        
        // TODO: draw the chutes and ladders with X and Y positions for each
        // button - put it in the constructor? or some method
        
        // TODO: if the player lands on the start of the ladder, then move the
        // player to the end of the ladder
        
        // TODO: if the player lands on the start of a chute, then move the player
        // to the end of the chute
        
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
                        diceValue = (int) (Math.random() * 10) + 1;
                        t.setText(text + diceValue);
                        
                        // determine index of move
                        int n = move(getIndex(), diceValue);
                        
                        // remove image in old
                        grid[getIndex()].setGraphic(null);
                        
                        // set image in new
                        grid[n].setGraphic(view);
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
    
    public int move(int start, int value) {
        int beg = start;
        
        for (int i = 0; i < value; i++) {
            start += 10;
        }
        
        return start;
    }
    
    public int getIndex() {
        int index = 0;
        for (Button b : grid) {
            if (!(b.getGraphic() == null)) return index;
            index++;
        }
        return -1;
    }
}

package gui;

import java.io.File;

import exceptions.CellTakenException;
import game.Game;
import game.Map;
import game.Warrior;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Class to handle all things related to the General User Interface
 * @author zeke0816
 *
 */
public class MainInterface extends Application {
	
	protected Stage appStage;
	protected Scene appScene;
	protected BorderPane appLayout;
	protected Game game;
	protected Map map;
	protected Button[][] arena;
	protected FlowPane statusLayout;
	protected GridPane arenaLayout;
	protected GridPane dockLayout;
	protected MediaPlayer mediaPlayer;
	protected MediaPlayer backgroundPlayer;
	protected WarriorInterface selectedWarrior;
	protected static final Background darkBackground = new Background(new BackgroundFill(Paint.valueOf("#dddddd"), null, null));
	//protected static final Background background = new Background(new BackgroundFill(Paint.valueOf("#ffffff"), new CornerRadii(4), new Insets(2)));
	protected static final Paint gray = Paint.valueOf("#8e8e8e");
	
	/**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		initialize(stage);
	}
	
	/**
	 * Initializes the General User Interface with an empty game and arena
	 */
	private void initialize(Stage stage) {
		appStage = stage;
		appStage.setTitle("Cartoon Defense");
		appStage.centerOnScreen();
		appStage.setMaximized(true);
		appStage.setFullScreen(true);
		appStage.setResizable(false);
		
		selectedWarrior = null;
		
		backgroundPlayer = new MediaPlayer(new Media(getMediaFromPath("src/assets/background.mp3")));
		backgroundPlayer.play();
		backgroundPlayer.setOnEndOfMedia(new Runnable() {
			
	        @Override
	        public void run() {
	        	backgroundPlayer.seek(Duration.ZERO);
	        	backgroundPlayer.play();
	        }
	        
	    });
		
		appLayout = new BorderPane();
        appLayout.setBackground(new Background(new BackgroundImage(new Image(getMediaFromPath("src/assets/background.png")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1300, 482, false, false, false, true))));
        
        appScene = new Scene(appLayout);
        appScene.getStylesheets().add(getMediaFromPath("src/assets/min.style.css"));
		
        statusLayout = new FlowPane();
        Label gameTitle = new Label("Cartoon Defense");
        gameTitle.setAlignment(Pos.CENTER);
        gameTitle.setFont(new Font("Cambria", 50));
        statusLayout.setAlignment(Pos.CENTER);
        statusLayout.getChildren().add(gameTitle);
        
        arenaLayout = new GridPane();
        
		game = new Game();
		map = game.getMap();
		arena = new Button[map.getRows()][map.getColumns()+1];
		int size = 60;
		for(int i = 0; i < map.getRows(); i++) {
			for(int j = 0; j <= map.getColumns(); j++) {
				arena[i][j] = new Button();
				arena[i][j].setVisible(true);
				arena[i][j].setMinSize(size, size);
				if(j < map.getColumns()) {
					arena[i][j].setBackground(darkBackground);
					arena[i][j].setOpacity(.6);
					arena[i][j].setBorder(new Border(new BorderStroke(gray, BorderStrokeStyle.SOLID, null, new BorderWidths(1, 1, 1, 1))));
					arena[i][j].setUserData(new Pair<Integer, Integer>(i, j));
					arena[i][j].setOnAction(cellListener);
				} else {
					arena[i][j].setBackground(createBackground("src/assets/Portal.gif", size, size, false, true));
				}
				arenaLayout.add(arena[i][j], j, i);
			}
		}
        
		dockLayout = new GridPane();
        dockLayout.setAlignment(Pos.CENTER);
        
        WarriorInterface agentP = new WarriorInterface("AgentP", "Agent P", true);
        agentP.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(agentP.getButton(), 0, 0);
        dockLayout.add(agentP.getLabel(), 0, 1);
        GridPane.setHalignment(agentP.getLabel(), HPos.CENTER);
        
        WarriorInterface flea = new WarriorInterface("TheFlea", "The Flea", true);
        flea.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(flea.getButton(), 1, 0);
        dockLayout.add(flea.getLabel(), 1, 1);
        GridPane.setHalignment(flea.getLabel(), HPos.CENTER);

        WarriorInterface cyborg = new WarriorInterface("Cyborg", "Cyborg", true);
        cyborg.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(cyborg.getButton(), 2, 0);
        dockLayout.add(cyborg.getLabel(), 2, 1);
        GridPane.setHalignment(cyborg.getLabel(), HPos.CENTER);
        
        WarriorInterface bb8 = new WarriorInterface("BB8", "BB8", false);
        bb8.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(bb8.getButton(), 3, 0);
        dockLayout.add(bb8.getLabel(), 3, 1);
        GridPane.setHalignment(bb8.getLabel(), HPos.CENTER);
        
        WarriorInterface gary = new WarriorInterface("Gary", "Gary", true);
        gary.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(gary.getButton(), 4, 0);
        dockLayout.add(gary.getLabel(), 4, 1);
        GridPane.setHalignment(gary.getLabel(), HPos.CENTER);
        
        WarriorInterface turret = new WarriorInterface("Turret", "Turret", false);
        turret.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(turret.getButton(), 5, 0);
        dockLayout.add(turret.getLabel(), 5, 1);
        GridPane.setHalignment(turret.getLabel(), HPos.CENTER);
        
        WarriorInterface toph = new WarriorInterface("Toph", "Toph", false);
        toph.getButton().setOnAction(selectWarriorListener);
        dockLayout.add(toph.getButton(), 6, 0);
        dockLayout.add(toph.getLabel(), 6, 1);
        GridPane.setHalignment(toph.getLabel(), HPos.CENTER);
        
		arenaLayout.setAlignment(Pos.CENTER);
		
		appLayout.setTop(statusLayout);
		appLayout.setCenter(arenaLayout);
		appLayout.setBottom(dockLayout);
		
		appStage.setScene(appScene);
		appStage.show();
	}
	
	/**
	 * Creates a background from an image, given its path, and returns it
	 * @param path the path to the background image
	 * @param width the width of the background
	 * @param height the height of the background
	 * @param cover whether the background will take the shape of the object
	 * @param contain whether the background will be adjusted to fit the shape of the object
	 * @return the background ready for use
	 */
	private Background createBackground(String path, double width, double height, boolean cover, boolean contain) {
		return new Background(new BackgroundImage(new Image(getMediaFromPath(path)), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(width, height, false, false, contain, cover)));
	}
	
	/**
	 * Gets media information as a String from a given path
	 * @param path the path to the media file
	 * @return the media information
	 */
	private String getMediaFromPath(String path) {
		return (new File(path)).toURI().toString();
	}
	
	/**
	 * Resets the cursor to its original default state
	 */
	private void resetCursorImage() {
		appScene.setCursor(Cursor.DEFAULT);
	}
	
	/**
	 * Sets a custom image as the cursor
	 * @param img the image
	 */
	private void setCursorImage(Image img) {
		appScene.setCursor(new ImageCursor(img, 30, 30));
	}
	
	/**
	 * Plays music, if necessary, given a path to the file
	 * @param path the path to the media file
	 */
	private void playMusic(String path) {
		backgroundPlayer.pause();
		if(mediaPlayer != null && mediaPlayer.getStatus().equals(Status.PLAYING)) {
			mediaPlayer.stop();
		}
		Media sound = new Media(getMediaFromPath(path));
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			
	        @Override
	        public void run() {
	        	backgroundPlayer.play();
	        }
	        
	    });
	}
	
	/**
	 * Listener for warrior selection from the dock
	 */
	EventHandler<ActionEvent> selectWarriorListener = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			try {
				Button warrior = (Button) event.getSource();
				Image img = warrior.getBackground().getImages().get(0).getImage();
				// TODO: try to resize it (for macOS)
				setCursorImage(img);
				selectedWarrior = (WarriorInterface) warrior.getUserData();
			} catch(ClassCastException e) {
				System.out.println("Invalid cast while selecting the warrior.");
			}
		}
		
	};
	
	/**
	 * Listener for warrior placement on a cell
	 */
	EventHandler<ActionEvent> cellListener = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			try {
				Button cell = (Button) event.getSource();
				Pair<Integer, Integer> coordinates = (Pair<Integer, Integer>) cell.getUserData();
				Integer row = coordinates.getKey();
				Integer col = coordinates.getValue();
				Warrior warrior = game.getFactory().createWarrior(selectedWarrior.getID());
				map.takeCell(row, col, warrior);
				resetCursorImage();
				if(selectedWarrior != null && selectedWarrior.playsMusic()) {
					playMusic("src/assets/"+selectedWarrior.getID()+".mp3");
				}
				cell.setBackground(createBackground("src/assets/"+selectedWarrior.getID()+".png", cell.getWidth(), cell.getHeight(), false, true));
				cell.setOpacity(1);
			} catch(ClassCastException e) {
				System.out.println("Invalid cast while placing the warrior.");
			} catch(CellTakenException e) {
				System.out.println(e.getMessage());
			}
		}
		
	};
	
}
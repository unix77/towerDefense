package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import game.Game;
import game.Map;
import game.objects.GameObject;
import game.objects.items.charm.temporary.TemporaryCharm;
import game.visitors.BattleVisitor;
import gui.layouts.MovementLayout;
import gui.layouts.PlacementLayout;
import gui.layouts.StatusLayout;
import gui.scenes.MainScene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Class to handle all things related to the General User Interface
 * @author zeke0816
 *
 */
public class MainInterface extends Application {
	
	protected BattleVisitor battle;
	
	/**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
    	launch(args);
    }
    
    /**
     * Starts the application
     */
	@Override
	public void start(Stage stage) {
		stage.setTitle("Cartoon Defense");
		stage.centerOnScreen();
		stage.setWidth(1136);
		stage.setHeight(720);
		stage.setScene(MainScene.getInstance());
		stage.show();
		
		Map map = Game.getInstance().getMap();
		battle = new BattleVisitor();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Game game = Game.getInstance();
				if(!game.isOver() && !game.paused()) {
					ArrayList<TemporaryCharm> wornOffCharms = game.checkTemporaryCharms();
					for(TemporaryCharm charm: wornOffCharms) {
						GameObject object = charm.getObject();
						if(object.isAlive()) {
							MovementLayout.getInstance().wearCharmOff(object);
						}
					}
					PlacementLayout.getInstance().spawnItem();
					PlacementLayout.getInstance().spawnEnemy();
					HashMap<GameObject, Pair<Integer, Integer>> positions = map.getPositions();
					for(Entry<GameObject, Pair<Integer, Integer>> position: positions.entrySet()) {
						GameObject object = position.getKey();
						if(object.isAlive()) {
							object.accept(battle);
						}
						StatusLayout.getInstance().updateBudget();
					}
				}
			}
        	
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
	}
	
	/**
	 * Handles unexpected exits (user clicks on the close button)
	 */
	@Override
	public void stop() {
		Game.getInstance().end();
	}
	
}

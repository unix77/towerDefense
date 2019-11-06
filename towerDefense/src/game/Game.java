package game;

import java.util.ArrayList;
import java.util.HashMap;

import game.objects.items.Item;
import game.objects.items.charm.temporary.TemporaryCharm;
import javafx.util.Pair;

/**
 * Class that handles all things related to the game status and defaults
 * @author zeke0816
 *
 */
public class Game {
	
	protected Map map;
	protected Level level;
	protected boolean over;
	protected double score;
	protected ArrayList<TemporaryCharm> temporaryCharms;
	protected HashMap<Item, Pair<Integer, Integer>> explosions;
	private static final Game instance = new Game();
	private int budget;
	
	/**
	 * Initializes the game creating a map, level, and factory, plus marking the game as not over.
	 */
	protected Game() {
		map = new Map();
		level = new Level();
		over = false;
		score = 0;
		budget = 2500;
		
		temporaryCharms = new ArrayList<TemporaryCharm>();
		explosions = new HashMap<Item, Pair<Integer, Integer>>();
	}
	
	/**
	 * Gets the instance of this class
	 * @return the instance of this class
	 */
	public static Game getInstance() {
		return instance;
	}
	
	/**
	 * Gets the explosions of the Game
	 * @return the explosions
	 */
	public HashMap<Item, Pair<Integer, Integer>> getExplosions() {
		return explosions;
	}
	
	public void addExplosion(Item it, int row, int col) {
		explosions.put(it, new Pair<Integer, Integer>(row, col));
	}
	
	/**
	 * Gets the map of the Game
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Gets the current score 
	 * @return score
	 */
	public double getScore() {
		return score;
	}
	
	/**
	 * Increases the game score 
	 * @param x points to be added to the score
	 */
	public void updateScore(int x) {
		score += x;
	}
	
	/**
	 * Updates the life of every active temporary charm and removes expired ones.
	 * @return a list of worn off charms.
	 */
	public ArrayList<TemporaryCharm> checkTemporaryCharms() {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		ArrayList<TemporaryCharm> charms = new ArrayList<TemporaryCharm>();
		for(int i = 0; i < temporaryCharms.size(); i++) {
			TemporaryCharm charm = temporaryCharms.get(i);
			if(charm.decreaseLife()) {
				indexes.add(i);
			}
		}
		for(int index: indexes) {
			charms.add(temporaryCharms.remove(index));
		}
		return charms;
	}
	
	/**
	 * End the game (Game Over)
	 */
	public void end() {
		over = true;
	}
	
	/**
	 * Tells whether the game is over or not
	 * @return true if the game is over, false if not.
	 */
	public boolean isOver() {
		return over == true;
	}

}
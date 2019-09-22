package gui.factories.warriors;

import characters.Warrior;
import gui.controls.WarriorButton;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Class to represent and save information about a Warrior in the GUI
 * @author zeke0816
 *
 */
public abstract class WarriorPrototype {
	
	protected String id;
	protected String name;
	protected boolean playsSound;
	protected Label label;
	protected WarriorButton button;
	protected Warrior warrior;
	protected static final double size = 100;
	
	/**
	 * Creates an empty Warrior Interface
	 */
	protected WarriorPrototype() {
		label = new Label();
		label.setVisible(true);
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font("Cambria", 20));
        GridPane.setHalignment(label, HPos.CENTER);
        
		button = new WarriorButton();
		button.setVisible(true);
        button.setPrefSize(size, size);
	}
	
	/**
	 * Clones and gets the Warrior to be 
	 * @return the Warrior
	 */
	public Warrior getWarrior() {
		return warrior.clone();
	}
	
	/**
	 * Gets the ID string for media identification
	 * @return the ID of the warrior interface
	 */
	public String getID() {
		return id;
	}

	/**
	 * Gets the display name of the warrior
	 * @return the display name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Tells whether this warrior plays sounds or not
	 * @return true if it plays sounds, false if it does not
	 */
	public boolean playsSound() {
		return playsSound;
	}

	/**
	 * Gets the label to show on screen
	 * @return the label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * Gets the button to show on screen
	 * @return the button
	 */
	public WarriorButton getButton() {
		return button;
	}

}
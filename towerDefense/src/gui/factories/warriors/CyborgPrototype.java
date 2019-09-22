package gui.factories.warriors;

import characters.warriors.Cyborg;
import exceptions.DatabaseException;
import media.databases.MediaDatabase;

public class CyborgPrototype extends WarriorPrototype {

	public CyborgPrototype() {
		super();
		
		id = "cyborg";
		name = "Cyborg";
		playsSound = true;
		
		label.setText(name);
        try {
			button.setBackground(MediaDatabase.getInstance().getImageBackgroundMedia(id, size, size, true, false));
		} catch (DatabaseException e) {
			System.out.println("The Warrior's graphics could not be loaded.");
		}
        button.setWarrior(this);
        warrior = new Cyborg();
	}

}

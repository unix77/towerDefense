package characters.warriors;

import characters.Warrior;

public class Turret extends Warrior {
	
	public Turret() {
		life = 2500;
		strength = 1000;
		attackSpeed = 400;
	}
	
	public Turret(Turret target) {
        super(target);
	}
	
	@Override
	public Warrior clone() {
		return new Turret(this);
	}

}

package characters.enemies;

import characters.Enemy;

public class Skeleton extends Enemy{
	public Skeleton() {
		points = 700;
		movementSpeed = 1;
	}
	
	protected Skeleton(Skeleton target) {
		super(target);
	}
	
	@Override
	public Enemy clone() {
		return new Skeleton(this);
	}
}

package gui.factories;

import characters.Warrior;
import gui.factories.warriors.AgentPPrototype;
import gui.factories.warriors.BB8Prototype;
import gui.factories.warriors.CyborgPrototype;
import gui.factories.warriors.GaryPrototype;
import gui.factories.warriors.TheFleaPrototype;
import gui.factories.warriors.TophPrototype;
import gui.factories.warriors.TurretPrototype;

/**
 * Class that handles the creation of characters and objects
 * @author zeke0816
 *
 */
public class WarriorFactory {
	
	protected AgentPPrototype agentP;
	protected TheFleaPrototype  theFlea;
	protected CyborgPrototype cyborg;
	protected BB8Prototype  bb8;
	protected GaryPrototype  gary;
	protected TurretPrototype  turret;
	protected TophPrototype  toph;
	
	
	private static final WarriorFactory instance = new WarriorFactory();
	
	/**
	 * Creates an instance of each warrior.
	 */
	private WarriorFactory() {
		agentP = new AgentPPrototype();
		theFlea = new TheFleaPrototype();
		cyborg = new CyborgPrototype();
		bb8 = new BB8Prototype();
		gary = new GaryPrototype();
		turret = new TurretPrototype();
		toph = new TophPrototype();
				
		
	}
	
	public static WarriorFactory getInstance() {
		return instance;
	}
	
	/**
	 * Creates a Warrior based on the given ID
	 * @param id the ID of the Warrior
	 * @return the newly created Warrior
	 */
	public Warrior createWarrior(String id) {
		switch(id) {
			case "agentP":
				return agentP.getWarrior();
			case "theFlea":
				return theFlea.getWarrior();
			case "cyborg":
				return cyborg.getWarrior();
			case "bb8":
				return bb8.getWarrior();
			case "gary":
				return gary.getWarrior();
			case "turret":
				return turret.getWarrior();
			case "toph":
				return toph.getWarrior();
		}
		return null;
	}

}

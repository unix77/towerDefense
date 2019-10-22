package game.factories;

import game.factories.items.AcquirableItemPrototype;
import game.factories.items.PreciousItemPrototype;
import game.factories.items.RandomItemPrototype;

/**
 * TODO: agregar una jerarquia de herencia mas. Transformar 
 * las clases de tipo de Item, en abstactas, y agregar 
 * la clase concreta de cada item.
 * 
 * 
 * 
 * Class that handles the creation of items of the game
 * @author dimax
 *
 */
public class ItemFactory {
	
	protected AcquirableItemPrototype acquirable;
	protected PreciousItemPrototype precious;
	protected RandomItemPrototype random;
	
	private static final ItemFactory instance = new ItemFactory();
	
	private ItemFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static ItemFactory getInstance() {
		return instance;
	}

}
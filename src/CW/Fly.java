/*
 * package CW;
 * 
 * public class Fly implements MovementStrategy {
 * 
 * @Override public void move(Hero hero, Point destination) {
 * System.out.println(hero.getName() + " is flying to " + destination); } }
 */
package CW;

public class Fly implements MovementStrategy {
	@Override
	public void move(Hero hero, Point destination) {
		System.out.println(hero.getName() + " is flying to " + destination);
	}
}


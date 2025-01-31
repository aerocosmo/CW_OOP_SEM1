/*
 * package CW;
 * 
 * public class Walk implements MovementStrategy {
 * 
 * @Override public void move(Hero hero, Point destination) {
 * System.out.println(hero.getName() + " ���� � ����� " + destination); } }
 */
package CW;

public class Walk implements MovementStrategy {
	@Override
	public void move(Hero hero, Point destination) {
		System.out.println(hero.getName() + " is walking to " + destination);
	}
}

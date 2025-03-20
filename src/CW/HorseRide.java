package CW;

public class HorseRide implements MovementStrategy {
	@Override
	public void move(Hero hero, Point destination) {
		System.out.println(hero.getName() + " is riding to " + destination);
	}
}


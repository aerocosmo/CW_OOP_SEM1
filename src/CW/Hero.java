/*
 * package CW;
 * 
 * public class Hero { private String name; private MovementStrategy strategy;
 * 
 * public Hero(String name) { this.name = name; }
 * 
 * public String getName() { return name; }
 * 
 * public void setStrategy(MovementStrategy strategy) { this.strategy =
 * strategy; }
 * 
 * public void moveTo(Point destination) { if (strategy != null) {
 * strategy.move(this, destination); } else { System.out.println(name +
 * " не имеет стратегии передвижения."); } } }
 */
package CW;

public class Hero {
	private String name;
	private MovementStrategy strategy;
	private Point position;

	public Hero(String name) {
		this.name = name;
		this.position = new Point(0, 0); // начальная позиция героя (например, (0, 0))
	}

	public String getName() {
		return name;
	}

	public void setStrategy(MovementStrategy strategy) {
		this.strategy = strategy;
	}

	public void moveTo(Point destination) {
		if (strategy != null) {
			// Рассчитываем новое положение после перемещения
			position = new Point(position.getX() + destination.getX(), position.getY() + destination.getY());
			strategy.move(this, position);
		} else {
			System.out.println(name + " doesn't know how to move.");
		}
	}

	public Point getPosition() {
		return position;
	}
}

package CW;

public class Hero {
	private String name;
	private MovementStrategy strategy;

	public Hero(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStrategy(MovementStrategy strategy) {
		this.strategy = strategy;
	}

	public void moveTo(Point destination) {
		if (strategy != null) {
			strategy.move(this, destination);
		} else {
			System.out.println(name + " не имеет стратегии передвижения.");
		}
	}
}

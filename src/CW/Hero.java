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
	 public static void main(String[] args) {
	        Hero hero = new Hero("Arthur");
	        
	        // Попытка перемещения без стратегии
	        hero.moveTo(new Point(1, 1)); // Вывод: Arthur doesn't know how to move.
	        
	        // Стратегия: пешая прогулка
	        hero.setStrategy(new Walk());
	        hero.moveTo(new Point(3, 4)); // Позиция становится (3,4)
	        
	        // Стратегия: полёт
	        hero.setStrategy(new Fly());
	        hero.moveTo(new Point(5, 1)); // Позиция становится (8,5)
	        
	        // Стратегия: верховая езда
	        hero.setStrategy(new HorseRide());
	        hero.moveTo(new Point(2, 2)); // Позиция становится (10,7)
	        
	        // Проверка финальной позиции
	        System.out.println("Final position: " + hero.getPosition());
	    }
}

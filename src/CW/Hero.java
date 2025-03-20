package CW;

public class Hero {
	private String name;
	private MovementStrategy strategy;
	private Point position;

	public Hero(String name) {
		this.name = name;
		this.position = new Point(0, 0); // ��������� ������� ����� (��������, (0, 0))
	}

	public String getName() {
		return name;
	}

	public void setStrategy(MovementStrategy strategy) {
		this.strategy = strategy;
	}

	public void moveTo(Point destination) {
		if (strategy != null) {
			// ������������ ����� ��������� ����� �����������
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
	        
	        // ������� ����������� ��� ���������
	        hero.moveTo(new Point(1, 1)); // �����: Arthur doesn't know how to move.
	        
	        // ���������: ����� ��������
	        hero.setStrategy(new Walk());
	        hero.moveTo(new Point(3, 4)); // ������� ���������� (3,4)
	        
	        // ���������: ����
	        hero.setStrategy(new Fly());
	        hero.moveTo(new Point(5, 1)); // ������� ���������� (8,5)
	        
	        // ���������: �������� ����
	        hero.setStrategy(new HorseRide());
	        hero.moveTo(new Point(2, 2)); // ������� ���������� (10,7)
	        
	        // �������� ��������� �������
	        System.out.println("Final position: " + hero.getPosition());
	    }
}

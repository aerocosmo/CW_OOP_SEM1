package CW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.*;

import java.util.stream.Collectors;

public class MainApp {
	private JTextArea outputAreaTask1;
	private JTextArea outputAreaTask2;
	private JTextArea outputAreaTask3;
	private JTextArea outputAreaTask4;
	private Translator translator = new Translator(); // Создаем экземпляр Translator
	private boolean dictionaryLoaded = false;
    private Hero hero;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new MainApp().createAndShowGUI();
		});
	}
    public MainApp() {
        this.hero = new Hero("Aragorn"); // Создаем героя один раз
    }

	private void createAndShowGUI() {
		JFrame frame = new JFrame("Main Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Task 1", createTask1Panel());
		tabbedPane.addTab("Task 2", createTask2Panel());
		tabbedPane.addTab("Task 3", createTask3Panel());
		tabbedPane.addTab("Task 4", createTask4Panel());

		frame.add(tabbedPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	// Task 1 Panel (Strategy Pattern)
    private JPanel createTask1Panel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();

        JButton walkButton = new JButton("Walk");
        JButton rideButton = new JButton("Ride");
        JButton flyButton = new JButton("Fly");

        walkButton.addActionListener(e -> moveHero(new Walk(), new Point(10, 20)));
        rideButton.addActionListener(e -> moveHero(new HorseRide(), new Point(30, 40)));
        flyButton.addActionListener(e -> moveHero(new Fly(), new Point(50, 60)));

        inputPanel.add(walkButton);
        inputPanel.add(rideButton);
        inputPanel.add(flyButton);

        outputAreaTask1 = new JTextArea();
        outputAreaTask1.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputAreaTask1), BorderLayout.CENTER);
        return panel;
    }

    // Универсальный метод для движения героя
    private void moveHero(MovementStrategy strategy, Point destination) {
        hero.setStrategy(strategy);
        hero.moveTo(destination);
        outputAreaTask1.setText(hero.getName() + " moved to " + hero.getPosition());
    }


	// Task 2 Panel (Reflection and Annotations)
	private JPanel createTask2Panel() {
		JPanel panel = new JPanel(new BorderLayout());

		JButton annotationButton = new JButton("Invoke Annotated Methods");
		annotationButton.addActionListener(e -> {
			try {
				outputAreaTask2.setText(MyClass.invokeAnnotatedMethods(new MyClass()));
			} catch (IllegalAccessException | InvocationTargetException ex) {
				outputAreaTask2.setText("Error: " + ex.getMessage());
				ex.printStackTrace();
			}
		});

		outputAreaTask2 = new JTextArea();
		outputAreaTask2.setEditable(false);

		panel.add(annotationButton, BorderLayout.NORTH);
		panel.add(new JScrollPane(outputAreaTask2), BorderLayout.CENTER);
		return panel;
	}

    // Task 3 Panel (Translator)
    private JPanel createTask3Panel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField inputTextField = new JTextField();
        JButton loadButton = new JButton("Load Dictionary");
        JButton translateButton = new JButton("Translate");

        loadButton.addActionListener(e -> {
            try {
                translator.loadDictionary("D:\\eclipse-workspace\\CourseWork_OOP_Java\\src\\CW\\dictionary.txt");
                dictionaryLoaded = true;
                outputAreaTask3.setText("Dictionary loaded successfully!");
            } catch (InvalidFileFormatException | FileReadException ex) {
                outputAreaTask3.setText("Error loading dictionary: " + ex.getMessage());
            }
        });

        translateButton.addActionListener(e -> {
            if (!dictionaryLoaded) {
                outputAreaTask3.setText("Dictionary not loaded!");
            } else {
                outputAreaTask3.setText(translator.translate(inputTextField.getText()));
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputTextField, BorderLayout.CENTER);
        inputPanel.add(loadButton, BorderLayout.WEST);
        inputPanel.add(translateButton, BorderLayout.EAST);

        outputAreaTask3 = new JTextArea();
        outputAreaTask3.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputAreaTask3), BorderLayout.CENTER);
        return panel;
    }

	// Task 4 Panel (Stream Operations)
 // Task 4 Panel (Stream Operations)

    private JPanel createTask4Panel() { 
        JPanel panel = new JPanel(new BorderLayout());

        JTextField inputTextField = new JTextField();
        JButton avgButton = new JButton("Calculate Average");
        JButton squaresUniqueButton = new JButton("Unique Squares");
        JButton sumEvenButton = new JButton("Sum of Even Numbers");
        JButton stringToMapButton = new JButton("String to Map");
        JButton getLastElementButton = new JButton("Get Last Element");
        JButton prefixStringsButton = new JButton("Prefix Strings");

        avgButton.addActionListener(e -> {
            List<Integer> numbers = StreamOperations.parseNumbers(inputTextField.getText());
            double avg = StreamOperations.average(numbers);
            outputAreaTask4.setText("Average: " + avg);
        });

        squaresUniqueButton.addActionListener(e -> {
            List<Integer> numbers = StreamOperations.parseNumbers(inputTextField.getText());
            List<Integer> squares = StreamOperations.squaresOfUnique(numbers);
            outputAreaTask4.setText("Squares of unique numbers: " + squares);
        });

        sumEvenButton.addActionListener(e -> {
            int[] numbers = StreamOperations.parseNumbers(inputTextField.getText()).stream().mapToInt(i -> i).toArray();
            int sum = StreamOperations.sumOfEven(numbers);
            outputAreaTask4.setText("Sum of even numbers: " + sum);
        });

        stringToMapButton.addActionListener(e -> {
            List<String> strings = Arrays.asList(inputTextField.getText().split(","));
            Map<Character, String> result = StreamOperations.stringToMap(strings);
            outputAreaTask4.setText("String to Map: " + result);
        });

        getLastElementButton.addActionListener(e -> {
            List<String> strings = Arrays.asList(inputTextField.getText().split(" "));
            String lastElement = StreamOperations.getLastElement(strings);
            outputAreaTask4.setText("Last Element: " + lastElement);
        });

        prefixStringsButton.addActionListener(e -> {
            List<String> strings = Arrays.asList(inputTextField.getText().split(","));
            List<String> prefixedStrings = StreamOperations.prefixStrings(strings);
            outputAreaTask4.setText("Prefixed Strings: " + prefixedStrings);
        });

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));  // Updated to accommodate 6 buttons
        inputPanel.add(new JLabel("Input:"));
        inputPanel.add(inputTextField);
        inputPanel.add(avgButton);
        inputPanel.add(squaresUniqueButton);
        inputPanel.add(sumEvenButton);
        inputPanel.add(stringToMapButton);
        inputPanel.add(getLastElementButton);
        inputPanel.add(prefixStringsButton);

        outputAreaTask4 = new JTextArea();
        outputAreaTask4.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputAreaTask4), BorderLayout.CENTER);

        return panel;
    }
}

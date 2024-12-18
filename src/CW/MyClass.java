package CW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
	int value();
}

public class MyClass {
	public void publicMethod(int a) {
		System.out.println("Public method called with: " + a);
	}

	@Repeat(value = 3)
	protected void protectedMethod(String s) {
		System.out.println("Protected method called with: " + s);
	}

	@Repeat(value = 2)
	private void privateMethod(double d) {
		System.out.println("Private method called with: " + d);
	}

	public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
		MyClass obj = new MyClass();
		Method[] methods = MyClass.class.getDeclaredMethods();

		for (Method m : methods) {
			if (m.isAnnotationPresent(Repeat.class)) {
				Repeat repeatAnnotation = m.getAnnotation(Repeat.class);
				int repeatCount = repeatAnnotation.value();
				m.setAccessible(true);
				for (int i = 0; i < repeatCount; i++) {
					if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == String.class)
						m.invoke(obj, "testString");
					else if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == double.class)
						m.invoke(obj, 3.14);
				}
			}
		}
	}
}

package CW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Определение аннотации Repeat с целочисленным параметром value
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
    int value();
}

// Класс с различными методами
public class MyClass {
    // Публичный метод
    public void publicMethod(int a) {
        System.out.println("Public method called with: " + a);
    }

    // Защищенный метод с аннотацией Repeat
    @Repeat(value = 3)
    protected void protectedMethod(String s) {
        System.out.println("Protected method called with: " + s);
    }

    // Приватный метод с аннотацией Repeat
    @Repeat(value = 2)
    private void privateMethod(double d) {
        System.out.println("Private method called with: " + d);
    }

    // Метод для вызова аннотированных методов
    public void invokeAnnotatedMethods() throws InvocationTargetException, IllegalAccessException {
        Method[] methods = MyClass.class.getDeclaredMethods(); // Получаем все методы класса

        for (Method m : methods) {
            // Проверяем наличие аннотации Repeat
            if (m.isAnnotationPresent(Repeat.class)) {
                Repeat repeatAnnotation = m.getAnnotation(Repeat.class);
                int repeatCount = repeatAnnotation.value(); // Получаем количество повторений
                m.setAccessible(true); // Делаем метод доступным (для приватных и защищенных)

                // Создаем аргументы для метода
                Object[] args = createArguments(m);

                // Вызов метода столько раз, сколько указано в аннотации
                for (int i = 0; i < repeatCount; i++) {
                    m.invoke(this, args);
                }
            }
        }
    }

    // Метод для создания аргументов в зависимости от параметров метода
    private Object[] createArguments(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] args = new Object[paramTypes.length];

        for (int parameterId = 0; parameterId < paramTypes.length; parameterId++) {
            Class<?> parameterType = paramTypes[parameterId];
            if (parameterType == int.class) {
                args[parameterId] = 5; // Пример значения для типа int
            } else if (parameterType == double.class) {
                args[parameterId] = 7.4; // Пример значения для типа double
            } else if (parameterType == float.class) {
                args[parameterId] = 8.3f; // Пример значения для типа float
            } else if (parameterType == boolean.class) {
                args[parameterId] = true; // Пример значения для типа boolean
            } else if (parameterType == String.class) {
                args[parameterId] = "Lalala"; // Пример значения для типа String
            } else {
                throw new RuntimeException("Parameter type " + parameterType.getName() + " not supported!");
            }
        }
        
        return args;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        MyClass obj = new MyClass();
        obj.invokeAnnotatedMethods(); // Вызов метода для выполнения аннотированных методов
    }
}

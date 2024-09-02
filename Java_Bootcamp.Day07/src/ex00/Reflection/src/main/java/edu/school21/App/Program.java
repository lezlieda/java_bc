package edu.school21.App;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, IOException, NoSuchMethodException {
        System.out.println("Classes:");
        List<String> ls = getClassesNamesList();
        for (String l : ls) {
            System.out.print(" - ");
            System.out.println(l);
        }
        printLine();
        System.out.println("Enter class name:");
        String className = scanner.nextLine();
        Class<?> cls = Class.forName("edu.school21.classes." + className);
        System.out.println("Fields:");
        printFields(cls);
        System.out.println("Methods:");
        printMethods(cls);
        printLine();
        try {
            Object o = createAnObject(cls);
            System.out.println("Object created: " + o);
            printLine();
            changeField(o);
            System.out.println("Object updated: " + o);
            printLine();
            invokeMethod(o);
        } catch (InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeMethod(Object obj) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Enter name of the method for call:");
        String meth = scanner.nextLine();
        meth = meth.replaceAll("^([\\w\\-]+)\\ ", "").replaceAll("\\([^()]*\\)","");
        for (Method m : obj.getClass().getDeclaredMethods()) {
            if (m.getName().matches(meth)) {
                List<Object> values = new ArrayList<>();
                Class<?>[] params = m.getParameterTypes();
                for (Class<?> p : params) {
                    System.out.println("Enter " + p.getSimpleName() + " value:");
                    values.add(getArgumentFromIO(p));
                }
                if (m.getReturnType().equals(void.class)) {
                    m.invoke(obj, values.toArray());
                } else {
                    System.out.println("Method returned:\n" + m.invoke(obj, values.toArray()).toString());
                }
            }
        }
    }

    private static void changeField(Object obj) {
        System.out.println("Enter name of the field for changing:");
        try {
            Field field = obj.getClass().getDeclaredField(scanner.nextLine());
            field.setAccessible(true);
            System.out.println("Enter " + field.getType().toString().replaceAll("class java\\.lang\\.", "") + " value:");
            field.set(obj, getArgumentFromIO(field.getType()));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object createAnObject(Class<?> cls) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("Let’s create an object.");
        Class<?>[] params = getParametersType(cls);
        Field[] fields = cls.getDeclaredFields();
        Constructor<?> constructor = cls.getDeclaredConstructor(params);
        List<Object> values = new ArrayList<>();
        for (Field field : fields) {
            System.out.println(field.getName() + ":");
            values.add(getArgumentFromIO(field.getType()));
        }
        return constructor.newInstance(values.toArray());
    }

    private static Object getArgumentFromIO(Class<?> type) {
        String input = scanner.nextLine();
        if (type == Integer.class || type == int.class) {
            return Integer.parseInt(input);
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(input);
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(input);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(input);
        }
        return input;
    }

    private static List<String> getClassesNamesList() throws IOException {
        Path path;
        List<String> ret = new ArrayList<>();
        path = Paths.get("target/classes/edu/school21/classes");
        for (Path p : Files.newDirectoryStream(path)) {
            if (p.getFileName().toString().endsWith(".class")) {
                ret.add(p.getFileName().toString().replaceAll("\\.class", ""));
            }
        }
        return ret;
    }

    private static Class<?>[] getParametersType(Class<?> cls) {
        List<Field> fs = new ArrayList<>();
        Collections.addAll(fs, cls.getDeclaredFields());
        return fs.stream().map(Field::getType).toArray(Class[]::new);
    }


    private static void printFields(Class<?> cls) {
        Field[] f = cls.getDeclaredFields();
        for (Field field : f) {
            String s = field.toString();
            s = getString(s, cls.getName());
            System.out.println("\t\t" + s);
        }
    }

    private static String getString(String s, String cls) {
        s = s.replaceAll("java\\.lang\\.", "");
        s = s.replaceAll(cls + "\\.", "");
        s = s.replaceAll("private", "");
        s = s.replaceAll("public", "");
        s = s.replaceAll("protected", "");
        return s;
    }

    private static void printMethods(Class<?> cls) {
        Method[] m = cls.getDeclaredMethods();
        for (Method method : m) {
            String s = method.toString();
            s = getString(s, cls.getName());
            System.out.println("\t\t" + s);
        }
    }

    private static void printLine() {
        System.out.println("---------------------");
    }
}

package sonntag.properties;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ObjectPCreator {

    private ObjectPCreator() {
    }

    public static ObjectP create(Property... properties) {
        ObjectP objectP = new ObjectP();
        addProperties(objectP, properties);

        objectP.init();
        return objectP;
    }

    public static <T extends ObjectP> T create(Class<T> objectPClass, Property... properties) {
        T objectP = instantiate(objectPClass);

        addProperties(objectP, properties);

        Properties propertiesAnnotation = objectPClass.getAnnotation(Properties.class);
        if (propertiesAnnotation == null) {
            objectP.init();
            return objectP;
        }

        for (Class<? extends Property> propertyClass : propertiesAnnotation.properties()) {

            boolean isInterface = propertyClass.isInterface();
            Class<? extends Property> clazz = isInterface ? findClassToInterface(propertyClass) : propertyClass;
            Property property = clazz != null ? instantiate(clazz) : instantiate(propertyClass);

            if (!isInterface)
                propertyClass = findInterfaceToClass(clazz);

            objectP.addProperty((Class<Property>) propertyClass, property);
        }

        objectP.init();
        return objectP;
    }

    public static<T extends ObjectP> void addProperty(T objectP, Property property) {
        // to be implemented
    }

    public static <T extends ObjectP> void addProperties(T objectP, Property... properties) {
        for (Property property : properties)
            objectP.addProperty(property);
    }

    private static <T> Class<? extends T> findClassToInterface(Class<T> clazz) {
        String packageName = clazz.getPackage().getName();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(new SubTypesScanner()));

        List<Class<? extends T>> implementingClasses = reflections.getSubTypesOf(clazz).stream().collect(Collectors.toList());

        int numClasses = implementingClasses.size();
        if (numClasses > 1) {
            throw new PropertyException(String.format("Could not create instance for interface %s. %d interface implementations were found!", clazz.getName(), numClasses));
        } else if (numClasses == 1) {
            return implementingClasses.get(0);
        }

        return null;
    }

    private static <T extends Property> Class<T> findInterfaceToClass(Class<T> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        List<Class<?>> propertyInterfaces = Arrays.stream(interfaces).filter(in -> isPropertyInterface(in)).collect(Collectors.toList());

        int numPropertyInterfaces = propertyInterfaces.size();
        if(numPropertyInterfaces > 1)
            throw new PropertyException(String.format("Class %s implements %d property interfaces (%s). That is strictly forbidden!", clazz.getName(), numPropertyInterfaces, propertyInterfaces.toString()));

        if(numPropertyInterfaces == 1)
            return (Class<T>) propertyInterfaces.get(0);

        throw new PropertyException(String.format("Could not find interface to class %s!", clazz.getName()));
    }

    private static boolean isPropertyInterface(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for(Class<?> in : interfaces) {
            if(in.equals(Property.class) || isPropertyInterface(in))
                return true;
        }

        return false;
    }

    private static <T> Property instantiateInterface(Class<T> clazz) {
        return (Property) java.lang.reflect.Proxy.newProxyInstance(
                clazz.getClassLoader(), new java.lang.Class[]{clazz}, (Object proxy, Method method, Object[] args) -> null);
    }

    private static <T> T instantiate(Class<T> clazz) {
        try {
            T object;
            object = clazz.isInterface() ? (T) instantiateInterface(clazz) : clazz.getConstructor().newInstance();
            return object;
        } catch (InstantiationException e) {
            throw new PropertyException(String.format("Instance of %s cannot be created. Class is abstract!", clazz.getName()), e);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new PropertyException(String.format("Instance of %s cannot be created. Default Constructor is not accessible!", clazz.getName()), e);
        } catch (InvocationTargetException e) {
            throw new PropertyException(String.format("Instance of %s cannot be created. Constructor of class threw an exception during creation!", clazz.getName()), e);
        }
    }
}

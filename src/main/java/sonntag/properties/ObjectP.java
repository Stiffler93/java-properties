package sonntag.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * ObjectP is the dynamic container for properties. It is the base class
 * that needs to be extended to make an object enhancable with properties.
 * <p>
 * Properties can be added in two ways. Either with the @Properties
 * annotation, or directly after the object creation with its
 * addProperty() method. The first approach is an aggregation meaning
 * that the object cannot exist without the specified properties.
 * The latter approach is a composition where Properties can be freely added
 * during the lifetime of an instance.
 */
public class ObjectP {

    protected Map<Class<? extends Property>, Property> properties = new HashMap<>();

    /**
     * Note that every subclass needs to provide a default constructor! Overloaded
     * constructors won't be called by the library.
     */
    protected ObjectP() {
    }

    /**
     * If an ObjectP child class needs to initialize data, variables or whatsoever that
     * needs to access certain properties, the init() method is the right place for it.
     * <p>
     * This method is called right after the object creation and property binding.
     */
    public void init() {
    }

    public <T extends Property> void addProperty(T property) {
        // to be implemented - check class blub blub
        properties.put(property.getClass(), property);
    }

    public <T extends Property> void addProperty(Class<T> propertyClass, T property) {
        // to be implemented - check class is Interface, if not use it
        properties.put(propertyClass, property);
    }

    /**
     * Checks if the object holds a specific property.
     *
     * @param property
     * @return true if object has such property.
     */
    public boolean is(Class<? extends Property> property) {
        return properties.containsKey(property);
    }

    /**
     * Returns property of object. Must be used in conjunction with the is(Property)
     * method to prevent PropertyExceptions to be thrown.
     *
     * @param property
     * @param <T>
     * @return
     * @throws PropertyException if Property does not exist.
     */
    public <T extends Property> T as(Class<T> property) {
        if (!is(property))
            throw new PropertyException(String.format("ObjectP %s does not hold the property %s!", getClass().getName(), property.getName()));

        return (T) properties.get(property);
    }

    /**
     * Creates an instance of the ObjectP property container. Optional properties
     * can be added that the instance is enhanced with.
     *
     * @param properties
     * @return ObjectP instance
     */
    public static ObjectP create(Property... properties) {
        return ObjectPCreator.create(properties);
    }

    /**
     * Creates an instance of an ObjectP subclass property container. Optional
     * properties can be added that the isntance is enhanced with.
     *
     * @param objectPClass
     * @param properties
     * @param <T>
     * @return ObjectP subclass instance
     */
    public static <T extends ObjectP> T create(Class<T> objectPClass, Property... properties) {
        return ObjectPCreator.create(objectPClass, properties);
    }
}

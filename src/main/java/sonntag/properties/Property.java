package sonntag.properties;

/**
 * The property interface is a marker interface to mark
 * special classes as properties with that a property container
 * like ObjectP can be enhanced with.
 * <p>
 * A property can have several different implementations but only
 * exactly one implementation per Property can be held by an
 * object.
 * <p>
 * Implementations of Property are automatically searched within
 * the same package and in packages underneath the package, where
 * the Property interface is located.
 */
public interface Property {
}

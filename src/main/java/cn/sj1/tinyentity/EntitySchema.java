package cn.sj1.tinyentity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class EntitySchema {
	String entityName;
	LinkedHashMap<String, Property> properties;

	public boolean isChanged() {
		return changed;
	}

	public String getEntityName() {
		return entityName;
	}

	boolean changed = true;

	public EntitySchema(String entityName) {
		super();
		this.entityName = entityName;
		this.properties = new LinkedHashMap<>();
	}

	public String getName() {
		return entityName;
	}

	public Map<String, Property> getProperties() {
		return properties;
	}

	public Property insert(int index, String name, Class<?> cls) {
		System.out.println(this.entityName + "." + name + " : " + cls);
		Property property = new Property(name, cls);
//		properties.
		this.properties.put(name, property);
		this.changed = true;
		return property;
	}

	public Property add(String name, Class<?> cls) {
		System.out.println(this.entityName + "." + name + " : " + cls);
		Property property = new Property(name, cls);
		this.properties.put(name, property);
		this.changed = true;
		return property;
	}

	public Optional<Property> get(String name) {
		return Optional.ofNullable(properties.get(name));
	}

	@Override
	public String toString() {
		return "" + entityName + ":" + properties.values() + "\n";
	}

	public void synced() {
		this.changed = false;
	}

}
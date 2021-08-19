package cn.sj1.tinyentity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class Entity {
	String name;
	private Map<String, Object> properties;
	Map<String, Entity> childEntities;
	Map<String, List<Entity>> childEntityLists;

	final EntitySchema entitySchema;

	public EntitySchema getEntitySchema() {
		return entitySchema;
	}

	public Entity(String name) {
		super();
		this.name = name;
		Map<String, EntitySchema> schema = getSchema();

		if (schema.containsKey(name)) {
			entitySchema = schema.get(name);
		} else {
			entitySchema = new EntitySchema(name);
			schema.put(name, entitySchema);
		}

		properties = new LinkedHashMap<>();
		childEntities = new LinkedHashMap<>();
		childEntityLists = new LinkedHashMap<String, List<Entity>>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public Map<String, Entity> getChildEntities() {
		return childEntities;
	}

//	public void setChildEntities(Map<String, Entity> childEntities) {
//		this.childEntities = childEntities;
//	}

	public Map<String, List<Entity>> getChildEntityLists() {
		return childEntityLists;
	}

//	public void setChildEntityLists(Map<String, List<Entity>> childEntityLists) {
//		this.childEntityLists = childEntityLists;
//	}

	public Object getProperty(String key) {
		return properties.get(key);
	}

	public Property putAttribute(String key, String attributeName, Object attributeValue) {
		Optional<Property> optionalProperty = entitySchema.get(key);

		if (optionalProperty.isPresent()) {
			entitySchema.changed |= optionalProperty.get().putAttribute(attributeName, attributeValue);

			return optionalProperty.get();
		} else {
			Property property = entitySchema.add(key, null);
			property.putAttribute(attributeName, attributeValue);
			return property;
		}
	}

	public Property putProperty(String key, Object value) {
		Optional<Property> optionalProperty = entitySchema.get(key);
		if (value == null) {
			if (optionalProperty.isPresent()) {
				return optionalProperty.get();
			} else {
				return entitySchema.add(key, null);
			}
		}

		if (optionalProperty.isPresent()) {
			if (optionalProperty.get().getCls() == null) {
				optionalProperty.get().setCls(value.getClass());
				entitySchema.changed = true;
			}
			assert optionalProperty.get().getCls() == value.getClass() : "同名字段值应该相同";

			if (value instanceof String) {
				int len = ((String) value).length();
				if (len > optionalProperty.get().size) {
					optionalProperty.get().size = len + len / 5;
					entitySchema.changed = true;
				}
			}
			properties.put(key, value);
			return optionalProperty.get();
		} else {
			Property property = entitySchema.add(key, value.getClass());
			if (value instanceof String) {
				int len = ((String) value).length();
				property.size = len;
			}
			properties.put(key, value);
			return property;
		}
	}

	public void putKey(String name, Object value) {
		Property property = this.putProperty(name, value);
		property.key = true;
	}

	public void putId(String name, Object value) {
		Property property = this.putProperty(name, value);
		property.id = true;
	}

	public Property getIdProperty() {
		for (Property id : entitySchema.getProperties().values()) {
			if (id.id) {
				return id;
			}
		}
		return null;
	}

	public void putChildEntity(String key, Entity value) {
		childEntities.put(key, (Entity) value);
	}

	public void putChildEntityList(String key, List<Entity> value) {
		childEntityLists.put(key, (List<Entity>) value);
	}

	@SuppressWarnings("unchecked")
	public void put(String key, Object value) {
		if (value == null) {
			putProperty(key, null);
			return;
		}
		if (value instanceof Entity) {
			childEntities.put(key, (Entity) value);
		} else if (value instanceof List && ((List<?>) value).size() > 0 && ((List<?>) value).get(0) instanceof Entity) {
			childEntityLists.put(key, (List<Entity>) value);
		} else {
			Optional<Property> en = entitySchema.get(key);

			if (en.isPresent()) {
				assert en.get().getCls() == value.getClass() : "同名字段值应该相同";
				properties.put(key, value);
			} else {
				entitySchema.add(key, value.getClass());
				properties.put(key, value);
			}
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (Entry<String, Object> entry : properties.entrySet()) {
			sb.append(entry.getKey());
			sb.append(":");
			Object value = entry.getValue();
			if (value instanceof String) {
				sb.append("\"").append(((String) value).replaceAll("[\n]", "\\n")).append("\"");
			} else
				sb.append(value);
			sb.append(",");
		}
		if (sb.charAt(sb.length() - 1) == ',') {
			sb.setCharAt(sb.length() - 1, '}');
		} else {
			sb.append("}");
		}

		return sb.toString();
	}

////	static ThreadLocal<Map<String, EntitySchema>> schemaThreadLocal = new ThreadLocal<>();
//	public static Map<String, EntitySchema> getSchema() {
//		Map<String, EntitySchema> schema = schemaThreadLocal.get();
//		if (schema == null) {
//			schema = new HashMap<String, EntitySchema>();
//			schemaThreadLocal.set(schema);
//		}
//		return schema;
//	}
	static Map<String, EntitySchema> schema = new HashMap<String, EntitySchema>();

	public static Map<String, EntitySchema> getSchema() {
		return schema;
	}

	public static void printSchema() {
		for (EntitySchema entitySchema : getSchema().values()) {
			System.out.println(entitySchema);
		}
	}
}

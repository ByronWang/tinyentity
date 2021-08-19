package cn.sj1.tinyentity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Property {
	private String name;
	int size = 0;
	private Class<?> cls;
	boolean key;// 不是主Key，是业务主Key，数据库上建立索引，添加唯一限制，但是不作为primarykey
	boolean id;
	Map<String, Object> attributes = new LinkedHashMap<>();

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public boolean isKey() {
		return key;
	}

	public boolean isId() {
		return id;
	}

	public Class<?> getCls() {
		return cls;
	}

	public Property(String name, Class<?> cls) {
		super();
		this.name = name;
		this.cls = cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}

	public boolean putAttribute(String name, Object value) {
		if (attributes.containsKey(name) && attributes.get(name).equals(value)) {
			return false;
		}
		attributes.put(name, value);
		return true;
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}
	@Override
	public String toString() {
		if (size > 0) {
			return name + ":" + (cls != null ? cls.getName() : "null") + "(" + size + ")";
		} else {
			return name + ":" + (cls != null ? cls.getName() : "null") + "";
		}
	}

}
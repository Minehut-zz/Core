package com.minehut.core.util.common.multimap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MultiMap<A, B, C> implements Cloneable, Iterable<A>, Serializable {

	private static final long serialVersionUID = -4128351615302325785L;
	
	private String name = null;
	private HashMap<A, ArrayList<Object>> map = null;
	
	
	public MultiMap() { 
		this(null, -1);
	}
	
	public MultiMap(String name) {
		this(name, -1);
	}
	
	public MultiMap(int size) {
		this(null, size);
	}
	
	public MultiMap(String name, int size) {
		this.map = (size == -1 ? new HashMap<A, ArrayList<Object>>() : new HashMap<A, ArrayList<Object>>(size));
		this.name = (name != null ? name : "MultiMap");
	}
	
	public void put(A key, final B firstValue, final C secondValue) {
		map.put(key, new ArrayList<Object>(2)
				{
					private static final long serialVersionUID = -8841921592042474055L;
					{
						add(firstValue);
						add(secondValue);
					}
				});
	}

	public Set<A> keySet() {
		return map.keySet();
	}
	
	public Collection<ArrayList<Object>> valueSet()  {
		return map.values();
	}
	
	public void remove(A key) {
		map.remove(key);
	}
	
	public void remove(int index) {
		remove(getKey(index));
	}
	
	public void clear() {
		map.clear();
	}
	
	public int size() {
		return map.size();
	}
	
	@Override
	public int hashCode() {
		return (map.hashCode() * map.values().hashCode() + map.keySet().hashCode() * valueSet().hashCode());
	}
	
	public int getIndex(A key) {
		return new ArrayList<A>(this.keySet()).indexOf(key);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public boolean containsKey(A key) {
		return map.containsKey(key);
	}
	
	public boolean containsValue(A key, Object value) {
		return map.get(key).contains(value);
	}
	
	public boolean containsValue(Object value) {
		for(A key : keySet()) {
			if(containsValue(key, value)) {
				return true;
			}
		}
		return false;
	}
	
	public A getKey(int index) {
		return new ArrayList<A>(this.keySet()).get(index);
	}
	
	@SuppressWarnings("unchecked")
	public B getFirstValue(A key) {
		return (B) map.get(key).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public C getSecondValue(A key) {
		return (C) map.get(key).get(1);
	}
	
	public MultiMapResource<A, B, C> getResource(A key) {
		return new MultiMapResource<A, B, C>(key, this);
	}

	public MultiMapResource<A, B, C> getResource(int index) {
		return getResource(getKey(index));
	}

	public void setFirstValue(A key, B value) {
		map.get(key).set(0, value);
	}

	public void setSecondValue(A key, C value) {
		map.get(key).set(1, value);
	}

	@Override
	public String toString() {
		return String.format("(MultiMap[%s]{hashCode=%s, size=%s, keys=%s)})",
					String.valueOf(this.name),
					String.valueOf(this.hashCode()),
					String.valueOf(this.size()),
					String.valueOf(this.keySet().toString()));
	}

	@Override
	public MultiMap<A, B, C> clone() {
		MultiMap<A, B, C> cloneMap = new MultiMap<A, B, C>(this.size());

		cloneMap.c(c());
		cloneMap.a(a());

		return cloneMap;
	}

	@Override
	public MultiMapIterator<A, B, C> iterator() {
		return new MultiMapIterator<A, B, C>(this);
	}
	
	@Override
	public boolean equals(Object o) {
		return o.equals(this);
	}
	
	
	String a() {
		return this.name;
	}

	void a(String a) {
		this.name = a;
	}

	HashMap<A, ArrayList<Object>> c() {
		return this.map;
	}

	@SuppressWarnings("unchecked")
	void c(HashMap<A, ArrayList<Object>> c) {
		for(A cc : c.keySet()) {
			this.put(cc, (B)c.get(cc).get(0), (C) c.get(cc).get(1));
		}
	}
	
}
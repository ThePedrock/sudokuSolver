package src.sudokuSolver;

import java.util.HashSet;
import java.util.Set;

public class Box {
	HashSet<Integer> value;
	
	Box() {
		value = new HashSet<Integer>() {{
		    add(1); add(2); add(3); add(4); add(5); add(6); add(7); add(8); add(9);
		}};
	}
	Box(HashSet<Integer> aSet) {
		setValue(aSet);
	}
	Box(Integer aNumber) {
		setValue(aNumber);
	}
	public void filter(Set<Integer> aSet) {
		value.removeAll(aSet);
	}
	public void filter (Integer aNumber) {
		value.remove(aNumber);
	}
	public boolean isClosed() {
		return (value.size()==1);
	}
	public void setValue(HashSet<Integer> aSet) {
		value = aSet;
	}
	public void setValue(Integer aNumber) {
		value = new HashSet<Integer>() {{
		    add(aNumber);
		}};
	}
	public HashSet<Integer> getValue() {
		return value;
	}
	public Integer getFirstValue() {
		return (Integer) value.toArray()[0];
	}
	public String getPrintValue() {
		if (isClosed()) { return getFirstValue().toString(); } else { return " "; }
	}
	public Box clone() {
		return new Box((HashSet<Integer>) value.clone());
	}
}

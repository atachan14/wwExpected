package model.role.child;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.role.person.Role;

public class Nothing extends Role {
	public static void main(String[] args) {

		//Q1.
		List<Integer> numbers = Arrays.asList(4, 9, 2, 8, 7);
		int min = numbers.stream()
				.reduce(Integer.MAX_VALUE, (a, b) -> Integer.min(a, b));
		System.out.println(min);

		//Q2.
		List<String> names = Arrays.asList("Tom", "Alice", "Bob", "Zoe", "Anna");
		//		List<String> hasA = names.stream()
		//				.filter(a -> );

		//Q03.
		List<Integer> numbers2 = Arrays.asList(5, 12, 7, 3, 20, 1);
		List<Integer> bai = numbers2.stream()
				.map(a -> a * 2)
				.collect(Collectors.toList());
		System.out.println(bai);
	}

}

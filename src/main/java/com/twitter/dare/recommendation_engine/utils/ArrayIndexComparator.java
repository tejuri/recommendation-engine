package com.twitter.dare.recommendation_engine.utils;

import java.util.ArrayList;
import java.util.Comparator;

public class ArrayIndexComparator implements Comparator<Integer> {

	private final ArrayList<Double> arrayList;

	public ArrayIndexComparator(ArrayList<Double> arrayList) {
		this.arrayList = arrayList;
	}

	public ArrayList<Integer> createIndexArray() {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < arrayList.size(); i++) {
			indexes.add(i);
		}
		return indexes;
	}

	public int compare(Integer index1, Integer index2) {
		return arrayList.get(index2).compareTo(arrayList.get(index1));
	}

	@Override
	public String toString() {
		return "ArrayIndexComparator [arrayList=" + arrayList + "]";
	}

}

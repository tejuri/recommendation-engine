package com.twitter.dare.recommendation_engine.CUR;

import java.util.ArrayList;
import com.twitter.dare.recommendation_engine.utils.ArrayIndexComparator;
import com.twitter.dare.recommendation_engine.utils.CreateMatrix;
import com.twitter.dare.recommendation_engine.utils.SelectSampleSize;

import Jama.Matrix;

public class ComputeR {

	private static int r;

	ArrayList<Integer> indexes;

	public ArrayList<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(ArrayList<Integer> indexes) {
		this.indexes = indexes;
	}

	public Matrix generateMatrixR(Matrix ratings) {
		long sumSquareElements = CreateMatrix.sumSquareElements(ratings);
		ArrayList<Double> probability = sumSquareGivenColumn(ratings, sumSquareElements);
		this.setIndexes(c_ColoumSelection(probability));
		r = SelectSampleSize.getR();
		Matrix matrixC = new Matrix(r, ratings.getColumnDimension());
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < ratings.getColumnDimension(); j++) {
				matrixC.set(i, j, ratings.get(indexes.get(i), j) / Math.sqrt(r * probability.get(indexes.get(i))));
			}
		}
		return matrixC;
	}

	private ArrayList<Integer> c_ColoumSelection(ArrayList<Double> probability) {

		ArrayIndexComparator comparator = new ArrayIndexComparator(probability);
		ArrayList<Integer> indexes = comparator.createIndexArray();
		indexes.sort(comparator);
		return indexes;
	}

	private ArrayList<Double> sumSquareGivenColumn(Matrix ratings, long sumSquareElements) {

		ArrayList<Double> localProbability = new ArrayList<Double>();

		double sum = 0.0;
		for (int i = 0; i < ratings.getRowDimension(); i++) {
			sum = 0.0;
			for (int j = 0; j < ratings.getColumnDimension(); j++) {
				sum += (ratings.get(i, j) * ratings.get(i, j));
			}
			localProbability.add(sum / sumSquareElements);
		}
		return localProbability;
	}
}

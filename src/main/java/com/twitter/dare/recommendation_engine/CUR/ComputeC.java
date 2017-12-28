package com.twitter.dare.recommendation_engine.CUR;

import java.util.ArrayList;
import com.twitter.dare.recommendation_engine.utils.CreateMatrix;
import com.twitter.dare.recommendation_engine.utils.SelectSampleSize;

import Jama.Matrix;

import com.twitter.dare.recommendation_engine.utils.ArrayIndexComparator;

public class ComputeC {

	private static int r ;

	public static void setR(int r) {
		ComputeC.r = SelectSampleSize.getR();
	}

	ArrayList<Integer> indexes;

	public ArrayList<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(ArrayList<Integer> indexes) {
		this.indexes = indexes;
	}

	public Matrix generateMatrixC(Matrix ratings) {

		long sumSquareElements = CreateMatrix.sumSquareElements(ratings);
		ArrayList<Double> probability = sumSquareGivenColumn(ratings, sumSquareElements);
		this.setIndexes(c_ColoumSelection(probability));
		r = SelectSampleSize.getR();
		Matrix matrixC = new Matrix(r, ratings.getRowDimension());

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < ratings.getRowDimension(); j++) {
				matrixC.set(i, j, ratings.get(j, indexes.get(i)) / Math.sqrt(r * probability.get(indexes.get(i))));
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
		for (int i = 0; i < ratings.getColumnDimension(); i++) {
			sum = 0.0;
			for (int j = 0; j < ratings.getRowDimension(); j++) {
				sum += (ratings.get(j, i) * ratings.get(j, i));
			}
			localProbability.add(sum / sumSquareElements);
		}
		return localProbability;
	}

}

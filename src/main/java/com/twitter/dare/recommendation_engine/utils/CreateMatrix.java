package com.twitter.dare.recommendation_engine.utils;

import Jama.Matrix;

/**
 * Util class to generate Matrix which will be removed in future.
 * @author gsonkar
 *
 */

public class CreateMatrix {
	
	private static double globalBaseLine;
	
	public static double getGlobalBaseLine() {
		return globalBaseLine;
	}

	public static void setGlobalBaseLine(double globalBaseLine) {
		CreateMatrix.globalBaseLine = globalBaseLine;
	}

	public static Matrix generateMatrix() {
		double[][] ratingArray = { { 1, 1, 1, 0, 0 }, { 3, 3, 3, 0, 0 }, { 4, 4, 4, 0, 0 }, { 5, 5, 5, 0, 0 },
				{ 0, 0, 0, 4, 4 }, { 0, 0, 0, 5, 5 }, { 0, 0, 0, 2, 2 } };
		for (int i = 0; i < ratingArray.length; i++) {
			for (int j = 0; j < ratingArray[i].length; j++) {
				System.out.print(ratingArray[i][j] + "\t");
			}
			System.out.println();
		}
		Matrix ratings = new Matrix(ratingArray);
		return ratings;
	}
	
	/*
	 * find sum of squares of all the elements in the matrix 
	 * to find the probability of each row/colum
	*/
	
	public static long sumSquareElements(Matrix ratings) {

		long sum = 0L;
		double globalBaseLine = 0.0d;
		for (int i = 0; i < ratings.getRowDimension(); i++) {
			for (int j = 0; j < ratings.getColumnDimension(); j++) {
				globalBaseLine += ratings.get(i, j);
				sum += (ratings.get(i, j) * ratings.get(i, j));
			}
		}
		setGlobalBaseLine(globalBaseLine / (ratings.getRowDimension() * ratings.getColumnDimension()));
		return sum;
	}
}

package com.twitter.dare.recommendation_engine;

import java.util.ArrayList;

import com.twitter.dare.recommendation_engine.utils.CreateMatrix;

import Jama.Matrix;

public class Predict {

	public Matrix predictMovies(Matrix userRating, Matrix R) {

		double globalBaseLine = CreateMatrix.getGlobalBaseLine();
		double userRatingMean = 0.0d;
		for (int i = 0; i < userRating.getColumnDimension(); i++) {
			userRatingMean += userRating.get(0, i);
		}
		userRatingMean = userRatingMean / userRating.getColumnDimension() - globalBaseLine;
		ArrayList<Double> movieRatingMean = new ArrayList<Double>();
		double temp = 0.0d;
		for (int i = 0; i < R.getColumnDimension(); i++) {
			temp = 0.0d;
			for (int j = 0; j < R.getRowDimension(); j++) {
				temp += R.get(j, i);
			}
			movieRatingMean.add(temp - globalBaseLine);
		}
		Matrix globalEffects = new Matrix(1, R.getColumnDimension());
		for (int i = 0; i < R.getColumnDimension(); i++) {
			globalEffects.set(0, i, globalBaseLine + userRatingMean + movieRatingMean.get(i));
		}
		
		
		return userRating.times(R.transpose()).times(R).plus(globalEffects);
	}

	public double predictUser(Matrix user1_Rating, Matrix user2_Rating, Matrix R) {
		Matrix predictMoviesUser1 = predictMovies(user1_Rating, R);
		Matrix predictMoviesUser2 = predictMovies(user2_Rating, R);
		double sum = 0.0d;
		for (int i = 0; i < predictMoviesUser1.getRowDimension(); i++) {
			for (int j = 0; j < predictMoviesUser1.getColumnDimension(); j++) {
				sum += predictMoviesUser1.get(i, j) * predictMoviesUser2.get(i, j);
			}

		}
		return Math.PI - Math.acos(sum);
	}

}

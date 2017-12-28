package com.twitter.dare.recommendation_engine.utils;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class SelectSampleSize {

	private static int r;

	public static int getR() {
		return r;
	}

	public static void setR(int r) {
		SelectSampleSize.r = r;
	}

	public static void sampleSize(Matrix ratings) {

		SingularValueDecomposition s = ratings.svd();
		double[] d = s.getSingularValues();
		double sum = 0.0d;
		for (int i = 0; i < d.length; i++) {
			sum += d[i];
		}
		double remainingSum = sum;
		int finalR = -1;
		for (int i = d.length - 1; i >= 0; i--) {
			remainingSum -= d[i];
			if ((remainingSum / sum) < 0.9) {
				finalR = i;
				break;
			}
		}

		setR(finalR + 1);
	}

}

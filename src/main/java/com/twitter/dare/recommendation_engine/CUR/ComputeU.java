package com.twitter.dare.recommendation_engine.CUR;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.twitter.dare.recommendation_engine.utils.SelectSampleSize;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class ComputeU {

	private static int r;

	public static void setR(int r) {
		ComputeU.r = SelectSampleSize.getR();
	}

	public Matrix generateMatrixU(Matrix matrixM, ArrayList<Integer> MatrixCIndexes,
			ArrayList<Integer> MatrixRIndexes) {

		SingularValueDecomposition s = generateMatrixW(matrixM, MatrixCIndexes, MatrixRIndexes).svd();
		Matrix X = s.getU();
		// X.print(9, 4);

		Matrix S = s.getS();
		// S.print(9, 4);

		Matrix V = s.getV();
		// V.print(9, 4);

		r = SelectSampleSize.getR();

		for (int i = 0; i < r; i++) {

			DecimalFormat df = new DecimalFormat("###.###");
			double singularvalue = S.get(i, i);
			df.format(singularvalue);
			if ((int) singularvalue != 0) {
				S.set(i, i, 1 / singularvalue);
			}

		}

		// S.print(9, 4);

		Matrix U = V.times(S).times(S).times(X.transpose());
		// U.print(9, 4);

		return U;
	}

	private Matrix generateMatrixW(Matrix matrixM, ArrayList<Integer> MatrixCIndexes,
			ArrayList<Integer> MatrixRIndexes) {

		// double[][] A = { { 0, 5 }, { 5, 0 } };
		// Matrix matrixW = new Matrix(A);

		r = SelectSampleSize.getR();
		Matrix matrixW = new Matrix(r, r);

		for (int f = 0; f < r; f++) {
			for (int i = 0; i < r; i++) {
				matrixW.set(f, i, matrixM.get(MatrixRIndexes.get(f), MatrixCIndexes.get(i)));
			}
		}
		return matrixW;
	}
}

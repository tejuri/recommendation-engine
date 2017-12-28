package com.twitter.dare.recommendation_engine;

import com.twitter.dare.recommendation_engine.CUR.ComputeC;
import com.twitter.dare.recommendation_engine.CUR.ComputeR;
import com.twitter.dare.recommendation_engine.CUR.ComputeU;
import com.twitter.dare.recommendation_engine.utils.CreateMatrix;
import com.twitter.dare.recommendation_engine.utils.SelectSampleSize;

import Jama.Matrix;

/**
 * Recommdation Engine System based on CUR Decomposition
 * 
 * @author gsonkar
 *
 */

public class App {
	public static void main(String[] args) {

		/*
		 * Generating Matrix for POC. it will be feteched from Mongodb
		 */
		Matrix ratings = CreateMatrix.generateMatrix();
		
		
		/*
		 * No of Significant User and Movies/dares
		 * RightNow, we assuming significant User = Significant movies/dares
		 */
		SelectSampleSize.sampleSize(ratings);
		
		
		
		/*
		 * Compute C of CUR
		 */
		ComputeC computeC = new ComputeC();
		Matrix C = computeC.generateMatrixC(ratings);

		// C.transpose().print(9, 4);
		
		
		/*
		 * Compute R of CUR
		 */
		ComputeR computeR = new ComputeR();
		Matrix R = computeR.generateMatrixR(ratings);

		// R.print(9, 4);
		
		
		/*
		 * Compute U of CUR
		 */
		ComputeU matrixU = new ComputeU();
		Matrix U = matrixU.generateMatrixU(ratings, computeC.getIndexes(), computeR.getIndexes());

		// U.print(9, 4);

		Matrix CUR = C.transpose().times(U).times(R);

		// CUR.print(9, 4);
		
		
		/*
		 * Example showing prediction
		 * This user prefer first three movies/dares
		 */
		double[][] userRating = { { 4, 0, 0, 0, 0 } };
		new Predict().predictMovies(new Matrix(userRating), R).print(9, 4);
	}
}

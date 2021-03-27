package com.secres.vaadinrisingtemp.ext;

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------
 * Regression.java
 * ---------------
 * (C) Copyright 2002-2021, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Peter Kolb (patch 2795746);
 *
 */

/**
 * A utility class for fitting regression curves to data.
 */
public abstract class Regression {

    /**
     * Returns the parameters 'a' and 'b' for an equation y = a + bx, fitted to
     * the data using ordinary least squares regression.  The result is
     * returned as a double[], where result[0] --&gt; a, and result[1] --&gt; b.
     *
     * @param data  the data.
     *
     * @return The parameters.
     */
    public static double[] getOLSRegression(double[][] data) {

        int n = data.length;
        if (n < 2) {
            throw new IllegalArgumentException("Not enough data.");
        }

        double sumX = 0;
        double sumY = 0;
        double sumXX = 0;
        double sumXY = 0;
        for (int i = 0; i < n; i++) {
            double x = data[i][0];
            double y = data[i][1];
            sumX += x;
            sumY += y;
            double xx = x * x;
            sumXX += xx;
            double xy = x * y;
            sumXY += xy;
        }
        double sxx = sumXX - (sumX * sumX) / n;
        double sxy = sumXY - (sumX * sumY) / n;
        double xbar = sumX / n;
        double ybar = sumY / n;

        double[] result = new double[2];
        result[1] = sxy / sxx;
        result[0] = ybar - result[1] * xbar;

        return result;

    }

    /**
     * Returns the parameters 'a' and 'b' for an equation y = ax^b, fitted to
     * the data using a power regression equation.  The result is returned as
     * an array, where double[0] --&gt; a, and double[1] --&gt; b.
     *
     * @param data  the data.
     *
     * @return The parameters.
     */
    public static double[] getPowerRegression(double[][] data) {

        int n = data.length;
        if (n < 2) {
            throw new IllegalArgumentException("Not enough data.");
        }

        double sumX = 0;
        double sumY = 0;
        double sumXX = 0;
        double sumXY = 0;
        for (int i = 0; i < n; i++) {
            double x = Math.log(data[i][0]);
            double y = Math.log(data[i][1]);
            sumX += x;
            sumY += y;
            double xx = x * x;
            sumXX += xx;
            double xy = x * y;
            sumXY += xy;
        }
        double sxx = sumXX - (sumX * sumX) / n;
        double sxy = sumXY - (sumX * sumY) / n;
        double xbar = sumX / n;
        double ybar = sumY / n;

        double[] result = new double[2];
        result[1] = sxy / sxx;
        result[0] = Math.pow(Math.exp(1.0), ybar - result[1] * xbar);

        return result;

    }

    /**
     * Returns a matrix with the following features: (1) the number of rows
     * and columns is 1 less than that of the original matrix; (2)the matrix
     * is triangular, i.e. all elements a (row, column) with column &gt; row are
     * zero.  This method is used for calculating a polynomial regression.
     *
     * @param matrix  the start matrix.
     *
     * @return The new matrix.
     */
    private static double[][] calculateSubMatrix(double[][] matrix){
        int equations = matrix.length;
        int coefficients = matrix[0].length;
        double[][] result = new double[equations - 1][coefficients - 1];
        for (int eq = 1; eq < equations; eq++) {
            double factor = matrix[0][0] / matrix[eq][0];
            for (int coe = 1; coe < coefficients; coe++) {
                result[eq - 1][coe -1] = matrix[0][coe] - matrix[eq][coe]
                        * factor;
            }
        }
        if (equations == 1) {
            return result;
        }
        // check for zero pivot element
        if (result[0][0] == 0) {
            boolean found = false;
            for (int i = 0; i < result.length; i ++) {
                if (result[i][0] != 0) {
                    found = true;
                    double[] temp = result[0];
                    System.arraycopy(result[i], 0, result[0], 0,
                            result[i].length);
                    System.arraycopy(temp, 0, result[i], 0, temp.length);
                    break;
                }
            }
            if (!found) {
                //System.out.println("Equation has no solution!");
                return new double[equations - 1][coefficients - 1];
            }
        }
        double[][] subMatrix = calculateSubMatrix(result);
        for (int eq = 1; eq < equations -  1; eq++) {
            result[eq][0] = 0;
            for (int coe = 1; coe < coefficients - 1; coe++) {
                result[eq][coe] = subMatrix[eq - 1][coe - 1];
            }
        }
        return result;
    }

}

public class Matrix {

    private double[][] matrix;

    public Matrix(double[][] matrix) {
        try {
            this.matrix = matrix;
            if (!isValid()) {
                throw new InvalidMatrixException("Not equal rows");
            }
        } catch (InvalidMatrixException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public double[][] multiply(double[][] matrix2) {
        try {
            if(matrix.length != matrix2[0].length || matrix[0].length != matrix2.length)
                throw new InvalidMatrixException("Invalid multiply matrix");

            double[][] result = new double[matrix.length][matrix2[0].length];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix2[0].length; j++) {
                    result[i][j] = 0;
                    for (int k = 0; k < matrix[0].length; k++) {
                        result[i][j] += matrix[i][k] * matrix2[k][j];
                    }
                }
            }

            return result;
        } catch (InvalidMatrixException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null; // ?
    }

    public double[][] add(double[][] matrix2) {
        try {
            if(matrix.length != matrix2.length || matrix[0].length != matrix2[0].length)
                throw new InvalidMatrixException("Not equal matrices sizes (adding)");

            double[][] result = new double[matrix.length][matrix[0].length];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix2[0].length; j++) {
                    result[i][j] = matrix[i][j] + matrix2[i][j];
                }
            }

            return result;
        } catch (InvalidMatrixException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null; // ?
    }

    public double getDeterminant(double[][] matrix) {
        try {
            if(matrix.length != matrix[0].length)
                throw new InvalidMatrixException("Not square sized matrix (determinant)");

            int n = matrix.length;

            if(n == 1)
                return matrix[0][0];

            if(n == 2)
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

            double det = 0;
            for (int row=0; row<n; row++) {
                double[][] subMatrix = getSubMatrix(row, 0);
                det += Math.pow(-1, row) * matrix[row][0] * getDeterminant(subMatrix);
            }

            return det;
        } catch (InvalidMatrixException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return 0.0; // ?
    }

    private double[][] getSubMatrix(int excludingRow, int excludingCol) {
        int n = matrix.length;
        double[][] subMatrix = new double[n - 1][n - 1];

        int row = 0;
        for (int i = 0; i < n; i++) {
            if (i == excludingRow) {
                continue;
            }
            int col = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludingCol) {
                    continue;
                }
                subMatrix[row][col++] = matrix[i][j];
            }
            row++;
        }

        return subMatrix;
    }

    public void transpone() {
        double[][] result = new double[matrix[0].length][matrix.length];
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        matrix = result;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void printMatrix() {
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isValid() {
        if(matrix.length == 0)
            return false;

        int numberOfColumns = matrix[0].length;
        if(numberOfColumns == 0)
            return false;

        for(int i=1; i<matrix.length; i++) {
            if(matrix[i].length != numberOfColumns)
                return false;
        }

        return true;
    }
}

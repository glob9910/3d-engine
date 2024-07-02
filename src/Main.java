public class Main {

    public static void main(String[] args) {

        Matrix matrix = new Matrix(
                new double[][] {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                }
        );

        Matrix matrix2 = new Matrix(
                new double[][] {
                        {1, 0, 2},
                        {2, 1, 0},
                        {0, 3, 1}
                }
        );

        System.out.println("Wyznacznik macierzy 1: " + matrix.getDeterminant(matrix.getMatrix())); // o zgrozo
        System.out.println("Wyznacznik macierzy 2: " + matrix2.getDeterminant(matrix2.getMatrix()));
        Matrix result = new Matrix(matrix.add(matrix2.getMatrix()));
        result = new Matrix(matrix.add(matrix2.getMatrix()));
        result.printMatrix();
        result.transpone();
        result.printMatrix();
    }
}

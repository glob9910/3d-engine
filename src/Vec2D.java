public class Vec2D extends Matrix {

    public Vec2D(double x, double y) {
        super(new double[][] {{x, y}});
    }

    public double getX() {
        return matrix[0][0];
    }

    public double getY() {
        return matrix[0][1];
    }

    public static Vec2D fromMatrix(Matrix matrix) {
        double[][] m = matrix.getMatrix();
        return new Vec2D(m[0][0], m[0][1]);
    }
}

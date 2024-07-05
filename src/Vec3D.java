public class Vec3D extends Matrix {

    public Vec3D(double x, double y, double z) {
        super(new double[][] {{x, y, z}});
    }

    public double getX() {
        return matrix[0][0];
    }

    public double getY() {
        return matrix[0][1];
    }

    public double getZ() {
        return matrix[0][2];
    }

    public static Vec3D fromMatrix(Matrix matrix) {
        double[][] m = matrix.getMatrix();
        return new Vec3D(m[0][0], m[0][1], m[0][2]);
    }
}

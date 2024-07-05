public class Vec4D extends Matrix {

    public Vec4D(double x, double y, double z, double w) {
        super(new double[][] {{x, y, z, w}});
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

    public double getW() {
        return matrix[0][3];
    }

    public static Vec4D fromMatrix(Matrix matrix) {
        double[][] m = matrix.getMatrix();
        return new Vec4D(m[0][0], m[0][1], m[0][2], m[0][3]);
    }

    public Vec4D normalize() {
        double length = Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
        return new Vec4D(getX() / length, getY() / length, getZ() / length, 1.0);
    }

    public Vec4D cross(Vec4D other) {
        return new Vec4D(
            getY() * other.getZ() - getZ() * other.getY(),
            getZ() * other.getX() - getX() * other.getZ(),
            getX() * other.getY() - getY() * other.getX(),
            1.0
        );
    }

    public double dot(Vec4D other) {
        return getX() * other.getX() + getY() * other.getY() + getZ() * other.getZ();
    }
}

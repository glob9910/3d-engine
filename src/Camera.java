public class Camera {

    public Vec4D position = new Vec4D(0, 0, 5, 0);

    public Vec4D direction = new Vec4D(0, 0, 1, 0);

    public final Vec4D up = new Vec4D(0, 1, 0, 0);

    public Matrix getViewMatrix() {
        Vec4D zaxis = direction.normalize();
        Vec4D xaxis = up.cross(zaxis).normalize();
        Vec4D yaxis = zaxis.cross(xaxis);

        Matrix orientation = new Matrix(new double[][] {
                {xaxis.getX(), xaxis.getY(), xaxis.getZ(), 0},
                {yaxis.getX(), yaxis.getY(), yaxis.getZ(), 0},
                {zaxis.getX(), zaxis.getY(), zaxis.getZ(), 0},
                {0, 0, 0, 1}
        });

        Matrix translation = new Matrix(new double[][] {
                {1, 0, 0, -position.getX()},
                {0, 1, 0, -position.getY()},
                {0, 0, 1, -position.getZ()},
                {0, 0, 0, 1}
        });

        return orientation.multiply(translation);
    }
}

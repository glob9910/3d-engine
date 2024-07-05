import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Vec4D[] cube = new Vec4D[] {
            new Vec4D(1,-1, -1, 1),
            new Vec4D(1,-1, 1, 1),
            new Vec4D(-1,-1, 1, 1),
            new Vec4D(-1,-1, -1, 1),
            new Vec4D(1,1, -1, 1),
            new Vec4D(1,1, 1, 1),
            new Vec4D(-1,1, 1, 1),
            new Vec4D(-1,1, -1, 1)
        };

        Vec3D[] cubeIndices = new Vec3D[] {
            new Vec3D(1, 2, 3),
            new Vec3D(7, 6, 5),
            new Vec3D(4, 5, 1),
            new Vec3D(5, 6, 2),
            new Vec3D(2, 6, 7),
            new Vec3D(0, 3, 7),
            new Vec3D(0, 1, 3),
            new Vec3D(4, 7, 5),
            new Vec3D(0, 4, 1),
            new Vec3D(1, 5, 2),
            new Vec3D(3, 2, 7),
            new Vec3D(4, 0, 7)
        };

        double phiY = Math.PI / 100;
        Matrix rotationY = new Matrix(new double[][] {
                {Math.cos(phiY), 0, Math.sin(phiY), 0},
                {0, 1, 0, 0},
                {-Math.sin(phiY), 0, Math.cos(phiY), 0},
                {0, 0, 0, 1}
        });

        double phiX = Math.PI / 150;
        Matrix rotationX = new Matrix(new double[][] {
                {1, 0, 0, 0},
                {0, Math.cos(phiX), -Math.sin(phiX), 0},
                {0, Math.sin(phiX), Math.cos(phiX), 0},
                {0, 0, 0, 1}
        });

        Matrix orthogonalProjection = new Matrix(new double[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0}
        });

        double aspectRatio = 1;
        double fov = 90;
        double far = 100;
        double near = 1;
        double tanHalfFov = Math.tan(Math.toRadians(fov) / 2.0);
        Matrix perspectiveMatrix = new Matrix(new double[][] {
                {1.0 / (aspectRatio * tanHalfFov), 0, 0, 0},
                {0, 1.0 / tanHalfFov, 0, 0},
                {0, 0, -(far + near) / (far - near), -2.0 * far * near / (far - near)},
                {0, 0, -1, 0}
        });

        Camera camera = new Camera();

        Screen panel = new Screen(
            g -> {
                g.clearRect(0, 0, 800, 800);

                Vec4D[] transformedCube = Arrays.copyOf(cube, cube.length);

                // obrócenie punktów względem Y a potem względem X
//                for(int i = 0 ; i < transformedCube.length; i++) {
//                    transformedCube[i] = Vec4D.fromMatrix(rotationY.multiply(transformedCube[i].transpose()).transpose());
//                    transformedCube[i] = Vec4D.fromMatrix(rotationX.multiply(transformedCube[i].transpose()).transpose());
//                }

                // przesunięcie punktów względem kamery
                for(int i = 0 ; i < transformedCube.length; i++) {
                    transformedCube[i] = Vec4D.fromMatrix(camera.getViewMatrix().multiply(transformedCube[i].transpose()).transpose());
                }

                // Projekcja ortogonalna kostki
                Vec2D[] projectedCube = new Vec2D[transformedCube.length];
                for(int i = 0 ; i < projectedCube.length; i++) {
                    projectedCube[i] = Vec2D.fromMatrix(perspectiveMatrix.multiply(transformedCube[i].transpose()).transpose());
                }

                for(Vec3D index : cubeIndices) {
                    Vec2D p1 = projectedCube[(int)index.getX()];
                    Vec2D p2 = projectedCube[(int)index.getY()];
                    Vec2D p3 = projectedCube[(int)index.getZ()];

                    p1 = Vec2D.fromMatrix(p1.multiplyByScalar(100).addScalar(400));
                    p2 = Vec2D.fromMatrix(p2.multiplyByScalar(100).addScalar(400));
                    p3 = Vec2D.fromMatrix(p3.multiplyByScalar(100).addScalar(400));

                    g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
                    g.drawLine((int) p2.getX(), (int) p2.getY(), (int) p3.getX(), (int) p3.getY());
                    g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p3.getX(), (int) p3.getY());
                }
            }
        );

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        camera.position.matrix[0][2] -= 0.1;
                        break;
                    case KeyEvent.VK_A:
                        camera.position.matrix[0][0] -= 0.1;
                        break;
                    case KeyEvent.VK_S:
                        camera.position.matrix[0][2] += 0.1;
                        break;
                    case KeyEvent.VK_D:
                        camera.position.matrix[0][0] += 0.1;
                        break;
                    case KeyEvent.VK_SHIFT:
                        camera.position.matrix[0][1] += 0.1;
                        break;
                    case KeyEvent.VK_CONTROL:
                        camera.position.matrix[0][1] -= 0.1;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            private double lastX, lastY;
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                double x = e.getXOnScreen();
                double y = e.getYOnScreen();
                double dx = lastX - x;
                double dy = lastY - y;
                lastX = x;
                lastY = y;

                if(dy != 0) {
                    double phiX = Math.PI / (1000 / dy);
                    Matrix rotationX = new Matrix(new double[][] {
                            {1, 0, 0, 0},
                            {0, Math.cos(phiX), -Math.sin(phiX), 0},
                            {0, Math.sin(phiX), Math.cos(phiX), 0},
                            {0, 0, 0, 1}
                    });

                    camera.direction = Vec4D.fromMatrix(camera.direction.multiply(rotationX));
                }

                if(dx != 0) {
                    double phiY = Math.PI / (1000 / dx);
                    Matrix rotationY = new Matrix(new double[][] {
                            {Math.cos(phiY), 0, Math.sin(phiY), 0},
                            {0, 1, 0, 0},
                            {-Math.sin(phiY), 0, Math.cos(phiY), 0},
                            {0, 0, 0, 1}
                    });

                    camera.direction = Vec4D.fromMatrix(camera.direction.multiply(rotationY));
                }

            }
        });

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.setTitle("makapaka");
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

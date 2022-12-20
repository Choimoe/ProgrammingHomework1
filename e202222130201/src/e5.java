import java.util.Random;

import static java.util.Arrays.sort;

abstract class Shape {
    public abstract double getArea();
    public abstract double getPerimeter();
}

class Rectangle extends Shape {
    private final double length, width;
    public Rectangle(double length, double width) { this.length = length; this.width = width; }
    @Override public double getArea() { return length * width; }
    @Override public double getPerimeter() { return 2 * (length + width); }
}

class Square extends Shape {
    private final double sideLength;
    public Square(double sideLength) { this.sideLength = sideLength; }
    @Override public double getArea() { return sideLength * sideLength; }
    @Override public double getPerimeter() { return 4 * sideLength; }
}

class Diamond extends Shape {
    private final double diagonal1, diagonal2;
    public Diamond(double diagonal1, double diagonal2) { this.diagonal1 = diagonal1; this.diagonal2 = diagonal2; }
    @Override public double getArea() { return diagonal1 * diagonal2 / 2; }
    @Override public double getPerimeter() { return 4 * Math.sqrt((diagonal1 / 2) * (diagonal1 / 2) + (diagonal2 / 2) * (diagonal2 / 2)); }
}

class Circle extends Shape {
    private final double radius;
    public Circle(double radius) { this.radius = radius; }
    @Override public double getArea() { return Math.PI * radius * radius; }
    @Override public double getPerimeter() { return 2 * Math.PI * radius; }
}

public class e5 {
    static Shape[] shapes = new Shape[20];

    public static void generateShapeByRandom() {
        Random random = new Random();
        for (int i = 0; i < shapes.length; i++) {
            switch (random.nextInt(4)) {
                case 0 -> shapes[i] = new Rectangle(random.nextDouble(10) * 10, random.nextDouble() * 10);
                case 1 -> shapes[i] = new Square(random.nextDouble(10) * 10);
                case 2 -> shapes[i] = new Diamond(random.nextDouble(10) * 10, random.nextDouble() * 10);
                case 3 -> shapes[i] = new Circle(random.nextDouble(10) * 10);
            }
        }
    }

    public static void main(String[] args) {
        generateShapeByRandom();

        System.out.println("=================");
        sort(shapes, (o1, o2) -> - (int) (o1.getPerimeter() - o2.getPerimeter()));
        System.out.println("按照周长从大到小排序：");
        printShapes(shapes);

        System.out.println("=================");
        sort(shapes, (o1, o2) -> (int) (o1.getPerimeter() - o2.getPerimeter()));
        System.out.println("按照周长从小到大排序：");
        printShapes(shapes);

        System.out.println("=================");
        sort(shapes, (o1, o2) -> - (int) (o1.getArea() - o2.getArea()));
        System.out.println("按照面积从大到小排序：");
        printShapes(shapes);

        System.out.println("=================");
        sort(shapes, (o1, o2) -> (int) (o1.getArea() - o2.getArea()));
        System.out.println("按照面积从小到大排序：");
        printShapes(shapes);

        System.out.println("=================");
        sort(shapes, (o1, o2) -> {
            double value1 = (o1 instanceof Circle ? -1 : 1) * o1.getArea() + (o1 instanceof Circle ? 1000000 : 0);
            double value2 = (o2 instanceof Circle ? -1 : 1) * o2.getArea() + (o2 instanceof Circle ? 1000000 : 0);
            return - (int) (value1 - value2);
        });
        System.out.println("按照面积，所有圆形从小到大排列，后面是所有其他图形从大到小排列：");
        printShapes(shapes);
    }

    public static void printShapes(Shape[] shapes) {
        for (Shape shape : shapes) {
            if (shape instanceof Rectangle)
                System.out.println("矩形：面积 = " + shape.getArea() + "，周长 = " + shape.getPerimeter());
            else if (shape instanceof Square)
                System.out.println("正方形：面积 = " + shape.getArea() + "，周长 = " + shape.getPerimeter());
            else if (shape instanceof Diamond)
                System.out.println("菱形：面积 = " + shape.getArea() + "，周长 = " + shape.getPerimeter());
            else if (shape instanceof Circle)
                System.out.println("圆形：面积 = " + shape.getArea() + "，周长 = " + shape.getPerimeter());
        }
    }
}
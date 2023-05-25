import java.io.*;
import java.util.*;

interface Body {
    double getSurfaceArea();
    double getVolume();
}

class Parallelepiped implements Body, Comparable<Parallelepiped>{
    private double length, width, height;

    public Parallelepiped(double length, double width, double height) {
        if(length <= 0) {
            System.out.println("Can't create Parallelepiped with length <= 0! Length is 1 now.");
            length = 1;
        }
        if(width <= 0) {
            System.out.println("Can't create Parallelepiped with width <= 0! Width is 1 now.");
            width = 1;
        }
        if(height <= 0) {
            System.out.println("Can't create Parallelepiped with height <= 0! Height is 1 now.");
            height = 1;
        }
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public double getSurfaceArea() {
        return 2 * (length * width + length * height + width * height);
    }

    public double getVolume() {
        return length * width * height;
    }

    @Override
    public String toString() {
        return "Parallelepiped with length " + length + ", width " + width + ", and height " + height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Parallelepiped) {
            Parallelepiped other = (Parallelepiped) obj;
            return length == other.length && width == other.width && height == other.height;
        }
        return false;
    }

    @Override
    public int compareTo(Parallelepiped o) {
//        if(getVolume() == o.getVolume()) {
//            return 0;
//        } else if(getVolume() < o.getVolume()) {
//            return -1;
//        } else return 1;
        // same as
        return Double.compare(getVolume(), o.getVolume());
    }
}

class Ball implements Body, Comparable<Ball>{
    private double radius;

    public Ball(double radius) {
        if(radius <= 0) {
            System.out.println("Can't create ball with radius <= 0! Radius is 1 now.");
            radius = 1;
        }
        this.radius = radius;
    }

    public double getSurfaceArea() {
        return 4 * Math.PI * radius * radius;
    }

    public double getVolume() {
        return 4.0 / 3.0 * Math.PI * radius * radius * radius;
    }

    @Override
    public String toString() {
        return "Ball with radius " + radius;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ball) {
            Ball other = (Ball) obj;
            return radius == other.radius;
        }
        return false;
    }

    @Override
    public int compareTo(Ball o) {
        return Double.compare(getVolume(), o.getVolume());
    }
}

class FigureComparator implements Comparator<Body> {

    @Override
    public int compare(Body o1, Body o2) {
        return Double.compare(o1.getSurfaceArea(), o2.getSurfaceArea());
    }

}


public class Main {
    public static void main(String[] args) {


        ArrayList<Parallelepiped> paralelepipeds = new ArrayList<>();
        System.out.println("Reading paralelepipeds...");
        String filename2 = "/Users/admin/IdeaProjects/lab5/src/First/parals.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename2))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                // check if there are exactly three tokens
                if (tokens.length != 3) {
                    System.out.println("Error: invalid number of tokens in line \"" + line + "\"");
                    continue; // skip to next line
                }
                // check if each token contains only numbers and optional minus sign
                boolean isFormatGood = true;
                for (String token : tokens) {
                    if (!token.matches("-?\\d+")) {
                        System.out.println("Error: invalid number format in line \"" + line + "\"");
                        isFormatGood = false;
                    }
                }
                if (!isFormatGood) continue;
                int length = Integer.parseInt(tokens[0]);
                int width = Integer.parseInt(tokens[1]);
                int height = Integer.parseInt(tokens[2]);

                System.out.println("Length: " + length + ", width: " + width + ", height: " + height);
                paralelepipeds.add(new Parallelepiped(length, width, height));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Ball> balls = new ArrayList<>();
        System.out.println("Reading balls...");
        String filename = "/Users/admin/IdeaProjects/lab5/src/First/balls.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // check if the line contains only numbers and optional minus sign
                if (!line.matches("-?\\d+")) {
                    System.out.println("Error: invalid number format in line \"" + line + "\"");
                    continue; // skip to next line
                }

                int radius = Integer.parseInt(line);

                // do something with the number
                System.out.println("Read radius: " + radius);
                balls.add(new Ball(radius));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(paralelepipeds);
        Collections.sort(balls);

        System.out.println("Printing sorted by default (volume)");
        System.out.println("All paralelepipeds:");
        for (Parallelepiped paral: paralelepipeds) {
            System.out.println(paral);
        }

        System.out.println("All balls:");
        for (Ball ball: balls) {
            System.out.println(ball);
        }

        System.out.println("Enter the dimensions of the new parallelepiped:");
        double length=0;
        double width=0;
        double height=0;
        do {
            Scanner input = new Scanner(System.in);
            try {
                length = input.nextDouble();
                width = input.nextDouble();
                height = input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }while(length==0 || width==0 || height==0);
        paralelepipeds.add(new Parallelepiped(length, width, height));

        System.out.println("Enter the radius of the ball:");
        double radius=0;
        do {
            Scanner input = new Scanner(System.in);
            try{
                radius = input.nextDouble();
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
            }
        }while(radius==0);
        balls.add(new Ball(radius));

        Collections.sort(paralelepipeds);
        Collections.sort(balls);
        System.out.println("Printing sorted again by default (volume)");
        System.out.println("All paralelepipeds:");
        for (Parallelepiped paral: paralelepipeds) {
            System.out.println(paral);
        }

        System.out.println("All balls:");
        for (Ball ball: balls) {
            System.out.println(ball);
        }

        ArrayList<Body> bodies = new ArrayList<Body>();
        bodies.addAll(paralelepipeds);
        bodies.addAll(balls);
        FigureComparator compareBySurfaceArea = new FigureComparator();
        bodies.sort(compareBySurfaceArea);
        String outputFile = "/Users/admin/IdeaProjects/lab5/src/First/figures.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            for (Body body : bodies) {
                bw.write(body.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

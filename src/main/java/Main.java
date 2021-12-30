import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int[] sequence;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Reader reader = Reader.getInstance();
        ArrayList<City> cities = reader.readByKeabord();
        Solver solver = new Solver();

         //double C[][] = {{Double.MAX_VALUE,20,18,12,8}, {5,Double.MAX_VALUE,14,7,11}, {12,18,Double.MAX_VALUE,6,11}, {11,17,11,Double.MAX_VALUE,12}, {5,5,5,5,Double.MAX_VALUE}};
        // double C[][] = {{Double.MAX_VALUE, 55, 39, 28}, {23, Double.MAX_VALUE, 31, 37}, {53, 49, Double.MAX_VALUE, 44}, {45, 65, 71, Double.MAX_VALUE}};
        //    double C[][] = {{Double.MAX_VALUE,90,80,40,100}, {60,Double.MAX_VALUE,40,50,70}, {50,30,Double.MAX_VALUE,60,20}, {10,70,20,Double.MAX_VALUE,50}, {20,40,50,20,Double.MAX_VALUE}};

        //System.out.println(solver.calculateMinLength(C));
        System.out.println(solver.calculateMinLength(cities));

    }
}

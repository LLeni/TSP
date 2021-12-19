import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static int[] sequence;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите количество городов: ");
        int numberCities = sc.nextInt();

        City[] cities = new City[numberCities];
        City city;
        int upcomingX, upcomingY;
        for (int i = 0; i < numberCities; i++) {

            System.out.print("Введите через пробел координаты x и y города под номером " + (i+1) + ": ");
            upcomingX = sc.nextInt();
            upcomingY = sc.nextInt();

            city = new City(upcomingX, upcomingY);
            cities[i] = city;
            //TODO: проверка на различие координат у всех городов
        }

//        BruteForceAlgorithm algorithm = new BruteForceAlgorithm(cities);
//        algorithm.applySimulatedAnnealing();

         BranchAndBoundAlgorithm branchAndBoundAlgorithm = new BranchAndBoundAlgorithm();
         branchAndBoundAlgorithm.calculateMinLength(cities);

         //branchAndBoundAlgorithm.reduceMatrix(branchAndBoundAlgorithm.createMatrixLengths(cities),1,1);

//        double edges [][] = new double[cities.size()][cities.size()];
//        ArrayList<Double> edgesList = new ArrayList<Double>();
//        HashMap<City, Double> citiesWithMinEdges = new HashMap<City, Double>();
//        double edge;
//        for (int i = 0; i < cities.size(); i++) {
//            for (int j = 0; j < cities.size(); j++) {
//                edge = Math.sqrt(Math.pow(cities.get(i).getX() - cities.get(j).getX(), 2) + Math.pow(cities.get(i).getY() - cities.get(j).getY(), 2));
//                edges[i][j] = edge;
//
//                if(i == j) continue;
//                edgesList.add(edge);
//                citiesWithMinEdges.put(cities.get(j), edge);
//            }
//        }
//
//        Collections.sort(edgesList);
//        cities.

//        for (double e :
//                edgesList) {
//            System.out.println(e + " ");
//        }
//
//        for (int i = 0; i < edges.length ; i++) {
//            for (int j = 0; j < edges[i].length; j++) {
//                if(i == j) continue;
//
//            }
//        }
//
//        for (int i = 0; i < cities.size(); i++) {
//            for (int j = 0; j < cities.size(); j++) {
//                System.out.printf("%10.3f",edges[i][j]);
//            }
//            System.out.println();
//        }

    }
}

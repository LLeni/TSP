import java.util.Arrays;

public class BruteForceAlgorithm {
    private City[] cities;
    private int[] sequence;
    private double shortestDistance = Integer.MAX_VALUE;

    BruteForceAlgorithm(City[] cities){
        this.cities = cities;
        sequence = new int[cities.length];
        for (int i = 0; i < cities.length; i++) {
            sequence[i] = i;
        }
    }

    private void createState(){

    }

    public void applySimulatedAnnealing(){
        for (int i = 0; i < 1000; i++) { // Ограничиваем сверху количество повторений этого алгоритма

            int firstCity = (int)Math.floor(Math.random() * cities.length);
            int secondCity = (int)Math.floor(Math.random() * cities.length);
            System.out.println(firstCity + " " + secondCity);
            swap(firstCity, secondCity);

            double lastDistance = calculateDistance();
            if(lastDistance < shortestDistance){
                shortestDistance = lastDistance;
                System.out.println("Номер итерации: " + i);
                System.out.println("Последовательность городов: " + Arrays.toString(sequence));
                System.out.println("Длина пути: " + shortestDistance + "\n");
            }
        }
    }

    private void swap(int c1, int c2){
        int temp = sequence[c1];
        sequence[c1] = sequence[c2];
        sequence[c2] = temp;
    }

    private double calculateDistance(){
        double currentSum = 0;
        for (int i = 0; i < cities.length - 1; i++) {
            currentSum+= Math.sqrt(Math.pow(cities[sequence[i+1]].getX() - cities[sequence[i]].getX(),2) + Math.pow(cities[sequence[i+1]].getY() - cities[sequence[i]].getY(),2));
        }
        return currentSum;
    }
}

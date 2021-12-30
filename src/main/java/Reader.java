import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reader {
    private Scanner sc;

    private Reader(){
    }

    public ArrayList<City> readByKeabord() {
        sc = new Scanner(System.in);
        System.out.print("Введите количество городов: ");
        int numberCities = sc.nextInt();

        City cities[] = new City[numberCities];
        int upcomingX, upcomingY;
        for (int i = 0; i < numberCities; i++) {

            System.out.print("Введите через пробел координаты x и y города под номером " + (i + 1) + ": ");
            upcomingX = sc.nextInt();
            upcomingY = sc.nextInt();

            cities[i] = new City(upcomingX, upcomingY);;
            //TODO: проверка на различие координат у всех городов
        }
        sc.close();
        return new ArrayList<>(Arrays.asList(cities));
    }

    public ArrayList<City> readFromFile(String path){
        ArrayList<City> cities = new ArrayList<>();
        City city;
        int upcomingX, upcomingY;
        try {
            sc = new Scanner(new File(path));
            while(sc.hasNextLine()){
                if(sc.hasNextInt()){
                    upcomingX = sc.nextInt();
                } else {
                    return null;
                }

                if(sc.hasNextInt()){
                    upcomingY = sc.nextInt();
                } else {
                    return null;
                }

                cities.add(new City(upcomingX, upcomingY));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sc.close();
        return  cities;
    }
    public  static Reader getInstance(){
        return new Reader();
    }
}

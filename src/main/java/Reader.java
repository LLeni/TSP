import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reader {
    private Scanner sc;
    private ClassLoader cl;

    private Reader(){
        cl = this.getClass().getClassLoader();
    }

    public City[] readByKeabord() {
        sc = new Scanner(System.in);
        System.out.print("Введите количество городов: ");
        int numberCities = sc.nextInt();

        City cities[] = new City[numberCities];
        int upcomingX, upcomingY;
        for (int i = 0; i < numberCities; i++) {

            System.out.print("Введите через пробел координаты x и y города под номером " + (i + 1) + ": ");
            upcomingX = sc.nextInt();
            upcomingY = sc.nextInt();

            cities[i] = new City(upcomingX, upcomingY);
            //TODO: проверка на различие координат у всех городов
        }
        sc.close();
        return cities;
    }

    public City[] readFromFile(String path){
        ArrayList<City> cities = new ArrayList<>();
        int upcomingX, upcomingY;
            sc = new Scanner(cl.getResourceAsStream(path));
            while(sc.hasNextLine()){
                if(sc.hasNextInt()){
                    upcomingX = sc.nextInt();
                } else {
                   break;
                }

                if(sc.hasNextInt()){
                    upcomingY = sc.nextInt();
                } else {
                    break;
                }

                cities.add(new City(upcomingX, upcomingY));
            }

        sc.close();
        return (City[])cities.toArray();
    }
    public  static Reader getInstance(){
        return new Reader();
    }
}

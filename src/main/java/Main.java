public class Main {
    public static void main(String[] args) {
        Reader reader = Reader.getInstance();
        City cities[] = reader.readByKeabord();

        Solver solver = new Solver();
        System.out.println(solver.calculateMinLength(cities));
    }
}

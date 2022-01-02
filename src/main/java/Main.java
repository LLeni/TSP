public class Main {
    public static void main(String[] args) {
        Reader reader = Reader.getInstance();
        City cities[] = reader.readByKeabord();
        Solver solver = new Solver();

        for (City c:
                cities) {
            System.out.println(c.getX() + " " + c.getY());
        }

        System.out.println(solver.calculateMinLength(cities));
    }
}

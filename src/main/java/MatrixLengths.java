public class MatrixLengths implements Cloneable {

    private double C[][];
    private int rowIds[];
    private int columnIds[];

    //Максимальное значение Double будет отображать бессконечность в ячейке матрицы
    private static final double INFINITY = Double.MAX_VALUE;

    MatrixLengths(City cities[]){
        this.C = createC(cities);

        rowIds = new int[C.length];
        columnIds = new int[C.length];

        for (int i = 0; i < C.length; i++) {
            rowIds[i] = i;
            columnIds[i] = i;
        }
    }

    MatrixLengths(double C[][]){
        this.C = C;

        rowIds = new int[C.length];
        columnIds = new int[C.length];

        for (int i = 0; i < C.length; i++) {
            rowIds[i] = i;
            columnIds[i] = i;
        }
    }

    //Построение матрицы расстояний с исходными данными
    public static double[][] createC(City cities[]){
        double C[][] = new double[cities.length][cities.length];
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                if(i == j){
                    C[i][j] = MatrixLengths.INFINITY;
                } else{
                    C[i][j] = Math.sqrt(Math.pow(cities[i].getX() - cities[j].getX(),2) + Math.pow(cities[i].getY() - cities[j].getY(),2));
                }
            }
        }
        return C;
    }



    /**
     * Исключает путь b -> a
     *
     * @param r строка
     * @param c столбец
     */
    public void excludePath(int r, int c){
        System.out.println(r +  "  " + c);
        int nameCityRow = rowIds[r];
        int nameCityColumn = columnIds[c];

        int newColumnId = -1;
        for (int i = 0; i < columnIds.length; i++) {
            if(nameCityRow == columnIds[i]){
                newColumnId = i;
                break;
            }
        }

        int newRowId = -1;
        for (int i = 0; i < rowIds.length; i++) {
            if(nameCityColumn == rowIds[i]){
                newRowId = i;
                break;
            }
        }

        if(newRowId != -1 && newColumnId != -1){
            C[newRowId][newColumnId] = MatrixLengths.INFINITY;
        }


        boolean isRowSkipped = false ;
        int newRowIds[] = new int[rowIds.length - 1];
        for (int i = 0; i < rowIds.length; i++) {
            if(i == r) {
                isRowSkipped = true;
                continue;
            }

            newRowIds[i - Boolean.compare(isRowSkipped, false)] = rowIds[i];
        }

        rowIds = newRowIds;

        boolean isColumnSkipped = false ;
        int newColumnIds[] = new int[columnIds.length - 1];
        for (int i = 0; i < columnIds.length; i++) {
            if(i == c){
                isColumnSkipped = true;
                continue;
            }

            newColumnIds[i - Boolean.compare(isColumnSkipped, false)] = columnIds[i];
        }


        columnIds = newColumnIds;


    }

    public void changeCell(int i, int j, double value){
        C[i][j] = value;
    }

    //Для удобства во время разработки кода
    public void showMatrix(){
        System.out.print("Матрица: \n\n ");

        for (int i = 0; i < columnIds.length; i++) {
            System.out.printf("%4d ",columnIds[i]);
        }
        System.out.println();
        for (int i = 0; i < C.length; i++) {
            System.out.print(rowIds[i] + " | ");
            for (int j = 0; j < C.length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }

    public MatrixLengths clone () throws CloneNotSupportedException
    {
        return (MatrixLengths) super.clone();
    }

    public double[][] getC() {
        return C;
    }

    public void setC(double[][] c) {
        C = c;
    }

}

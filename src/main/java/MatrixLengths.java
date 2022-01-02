import java.util.ArrayList;

public class MatrixLengths implements Cloneable {

    private double C[][];
    private int rowIds[];
    private int columnIds[];

    private double minsRow[], minsColumn[];

    //Максимальное значение Double будет отображать бессконечность в ячейке матрицы
    public static final double INFINITY = Double.MAX_VALUE;

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

    public void reduceLines(){
        minsRow = calculateMinsRow();
        reduceRows(minsRow);

        minsColumn = calculateMinsColumn();
        reduceColumns(minsColumn);
    }


    //Нахождение минимальных значений в строках
    private double[] calculateMinsRow(){
        double minsRow[] = new double[C.length];
        double currentMin;

        for (int i = 0; i < minsRow.length; i++) {
            currentMin = Double.MAX_VALUE;
            for (int j = 0; j < minsRow.length; j++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsRow[i] = currentMin;
        }
        return minsRow;
    }

    //Редукция строк
    private void reduceRows(double minsRow[]){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsRow[i];
            }
        }
    }

    //Нахождение минимальных значений в колонках
    private double[] calculateMinsColumn(){
        double currentMin;
        double minsColumn[] = new double[C.length];

        for (int j = 0; j < minsColumn.length; j++) {
            currentMin = Double.MAX_VALUE;
            for (int i = 0; i < minsColumn.length; i++) {
                if(C[i][j] < currentMin)
                    currentMin = C[i][j];
            }
            minsColumn[j] = currentMin;

        }

        return minsColumn;
    }

    //Редукция столбцов
    private void  reduceColumns(double minsColumn[]){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                if(i != j) //избегаем главную диагональ
                    C[i][j] -= minsColumn[j];
            }
        }
    }

    //Редукция матрицы расстояний
    public void reduceMatrix(int r, int c){
        excludePath(r, c);
        double newC[][] = new double[C.length-1][C.length-1];
        int row = r;
        int column = c;
        boolean isRowSkipped = false;
        boolean isColumnSkipped;

        for (int i = 0; i < C.length; i++) {
            if(i == row){
                isRowSkipped = true;
                continue;
            }

            isColumnSkipped = false;
            for (int j = 0; j < C.length; j++) {
                if(j == column){
                    isColumnSkipped = true;
                    continue;
                }
                newC[i - Boolean.compare(isRowSkipped, false)][j - Boolean.compare(isColumnSkipped, false)] = C[i][j];
            }
        }
        C = newC;
    }

    /**
     * Исключает путь c -> r
     *
     * @param r строка
     * @param c столбец
     */
    private void excludePath(int r, int c){
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

    //Изменить конкретную ячейку
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

    public double[] getMinsRow() {
        return minsRow;
    }

    public double[] getMinsColumn() {
        return minsColumn;
    }
}

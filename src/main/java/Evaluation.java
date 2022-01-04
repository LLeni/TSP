//Оценка
//Содержит оценку и местоположение, где она проводилась
public class Evaluation {
    private double value;
    private int rowId;
    private int columnId;

    Evaluation(){
        this(Double.MIN_VALUE, -1, -1);
    }

    Evaluation(double value,int rowId, int columnId){
        this.value = value;
        this.rowId = rowId;
        this.columnId = columnId;
    }

    /**
     * Позволяет изменить оценку и местоположение где она проводилась
     * @param value значение оценки
     * @param rowId строка местоположения
     * @param columnId столбец местоположения
     */
    public void setFields(double value,int rowId, int columnId){
        this.value = value;
        this.rowId = rowId;
        this.columnId = columnId;
    }

    public double getValue() {
        return value;
    }

    public int getRowId() {
        return rowId;
    }

    public int getColumnId() {
        return columnId;
    }

}

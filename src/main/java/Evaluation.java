//Оценка
//Содержит оценку и местоположение где она проводилась
public class Evaluation {
    private double valueEvaluation;
    private int rowId;
    private int columnId;


    Evaluation(){
        //Значение по умолчанию подразумевает под собой не найденную оценку
        valueEvaluation = Double.MIN_VALUE;
    }

    /**
     * Позволяет изменить оценку и местоположение где она проводилась
     * @param valueEvaluation значение оценку
     * @param rowId строка местоположения
     * @param columnId столбец местоположения
     */
    public void setEvaluation(double valueEvaluation,int rowId, int columnId){
        this.valueEvaluation = valueEvaluation;
        this.rowId = rowId;
        this.columnId = columnId;
    }

    public double getValueEvaluation() {
        return valueEvaluation;
    }

    public int getRowId() {
        return rowId;
    }


    public int getColumnId() {
        return columnId;
    }

}

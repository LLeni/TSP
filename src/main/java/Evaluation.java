public class Evaluation {
    private double valueEvaluation;
    private int rowId;
    private int columnId;

    //Значение по умолчанию подразумевает под собой не найденную оценку
    Evaluation(){
        valueEvaluation = Double.MIN_VALUE;
    }

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

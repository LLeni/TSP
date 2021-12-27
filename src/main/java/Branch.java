public class Branch {
    private double C[][];
    public int rowIds[];
    public int columnIds[];

    private double lowBorder;
    private boolean isEliminatePath;
    private double maxEvaluation[];


    Branch(double C[][], double lowBorder, boolean isEliminatePath){
        this.C = C;
        this.lowBorder = lowBorder;

        rowIds = new int[C.length];
        columnIds = new int[C.length];
        for (int i = 0; i < C.length; i++) {
            rowIds[i] = i;
            columnIds[i] = i;
        }
    }

    public double[][] getC() {
        return C;
    }


    public void setC(double[][] c) {
        C = c;
    }

    public double getLowBorder() {
        return lowBorder;
    }

    public void setLowBorder(double lowBorder) {
        this.lowBorder = lowBorder;
    }

    public boolean isEliminatePath() {
        return isEliminatePath;
    }

    public void setEliminatePath(boolean eliminatePath) {
        isEliminatePath = eliminatePath;
    }

    public double[] getMaxEvaluation() {
        return maxEvaluation;
    }

    public void setMaxEvaluation(double[] maxEvaluation) {
        this.maxEvaluation = maxEvaluation;
    }

    public int[] getRowIds() {
        return rowIds;
    }

    public void setRowIds(int[] rowIds) {
        this.rowIds = rowIds;
    }

    public int[] getColumnIds() {
        return columnIds;
    }

    public void setColumnIds(int[] columnIds) {
        this.columnIds = columnIds;
    }
}

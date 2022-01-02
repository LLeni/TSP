//Соотносится к одному конкретному решению
public class Solution implements Cloneable {

    private MatrixLengths matrixLengths;
    private double lowBorder;
    private boolean isEliminatePath;

    Solution(MatrixLengths matrixLengths){
        this.matrixLengths = matrixLengths;
    }

    /**
     * Расчитывает нижнюю границу
     */
    public void calculateLowBorder(){
        double minsRow[] = matrixLengths.getMinsRow();
        double minsColumn[] = matrixLengths.getMinsColumn();

        for (int i = 0; i < matrixLengths.getC().length; i++) {
            lowBorder += minsRow[i] + minsColumn[i];
        }
    }

    /**
     * Клонирует текущий экземпляр
     * @return склонированный текущий экземпляр MatrixLengths
     * @throws CloneNotSupportedException
     */
    public Solution clone () throws CloneNotSupportedException
    {
        Solution solution = (Solution) super.clone();
        MatrixLengths matrixLengths = this.getMatrixLengths().clone();
        solution.setMatrixLengths(matrixLengths);
        return solution;
    }


    public MatrixLengths getMatrixLengths() {
        return matrixLengths;
    }

    public void setMatrixLengths(MatrixLengths matrixLengths) {
        this.matrixLengths = matrixLengths;
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

}

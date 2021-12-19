public class City {
    private int x;
    private int y;
    private boolean hasInRoute;

    City(int x, int y){
         this.x = x;
         this.y = y;
         hasInRoute = false;
    }

    City(){
        x = 0;
        y = 0;
        hasInRoute = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

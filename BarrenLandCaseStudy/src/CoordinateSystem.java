public class CoordinateSystem {
    
    public Integer x;
    public Integer y;
    public boolean isBarren;
    public boolean visited = false; //default state

    public CoordinateSystem(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
    
    public Integer getXCoord() { return x; }

    public Integer getYCoord() { return y; }

    public void setCoordAsVisited(boolean visited) { this.visited = visited; }

    public boolean isCoordVisited() { return visited; }

    public void setCoordAsBarren(boolean isBarren) { this.isBarren = isBarren; }
    
}
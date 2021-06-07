import java.util.*;

public class BarrenLandCaseStudy {

    public static int mainLandGridWidth = 400;
    public static int mainLandGridHeight = 600;
    public static CoordinateSystem[][] mainLandGrid = new CoordinateSystem[mainLandGridWidth][mainLandGridHeight];
    
    public static boolean visitIfUnvisited(CoordinateSystem c) {
        if (c.getXCoord() < 0 || c.getXCoord() >= mainLandGridWidth) { return false; }
        if (c.getYCoord() < 0 || c.getYCoord() >= mainLandGridHeight) { return false; }
        CoordinateSystem mainC = mainLandGrid[c.getXCoord()][c.getYCoord()];
        if (mainC.isCoordVisited()) { return false; }
        mainC.setCoordAsVisited(true);
        return true;
    }

    public static List<Integer> findAreasOfAllFertilePlotsBFS(List<Integer> fertPlots, int xVal, int yVal) {
        for (int y = yVal; y < mainLandGridHeight; y++) {
            for (int x = xVal; x < mainLandGridWidth; x++) {
                CoordinateSystem indvCoord = mainLandGrid[x][y];
                if (!indvCoord.isCoordVisited()) {
                    int xCoordVal, yCoordVal, totalFertileArea = 0;
                    Queue<CoordinateSystem> queue = new LinkedList<>();
                    queue.add(new CoordinateSystem(x, y));
                    while (!queue.isEmpty()) {
                        CoordinateSystem c = queue.remove();
                        if(visitIfUnvisited(c)) {     //only barren regions have been visited up until now
                        totalFertileArea = totalFertileArea + 1;
                        xCoordVal = c.getXCoord();
                        yCoordVal = c.getYCoord();
                        //check coords above, right, left, below as applicable
                        if (yCoordVal+1 < mainLandGridHeight && !mainLandGrid[c.getXCoord()][yCoordVal+1].isCoordVisited()) { queue.add(new CoordinateSystem(xCoordVal, yCoordVal + 1)); }
                        if (xCoordVal+1 < mainLandGridWidth && !mainLandGrid[c.getXCoord()+1][yCoordVal].isCoordVisited()) { queue.add(new CoordinateSystem(xCoordVal + 1, yCoordVal)); }
                        if (xCoordVal-1 >= 0 && !mainLandGrid[c.getXCoord()-1][yCoordVal].isCoordVisited()) { queue.add(new CoordinateSystem(xCoordVal, yCoordVal)); }
                        if (yCoordVal-1 >= 0 && !mainLandGrid[xCoordVal][yCoordVal-1].isCoordVisited() ) { queue.add(new CoordinateSystem(xCoordVal, yCoordVal - 1)); }
                        }
                    }

                    //totalFertileArea has number of coordinates in fertile region i.e. area    
                    fertPlots.add(totalFertileArea); //list of areas of fertile land
                    findAreasOfAllFertilePlotsBFS(fertPlots, x, y);
                }
            }
        }

        Collections.sort(fertPlots); //sort areas
        return fertPlots;

    }
    
    public static String fertileLandAreaCalculator(String[] stdInputArray) {             
        List<Integer[]> arrListOfPointsPerRect = new ArrayList<>(); //initializing list of arrays of rects, each with coordinates stored

        for (int i = 0; i < stdInputArray.length; i++) { //take each set of coordinates
            String[] strCoords = stdInputArray[i].split(" "); //split into array of strings, later we convert strings to ints
            if (strCoords.length > 0 && strCoords.length % 4 != 0) {return "Input array does not have bottem left and top right coordinates for one or more sets.";}   
            Integer[] intCoords = new Integer[strCoords.length];
            for (int j = 0; j < strCoords.length; j++) {
                intCoords[j] = Integer.parseInt(strCoords[j]); //get each individual number in input string
            }
            arrListOfPointsPerRect.add(intCoords); //list of arrays of rectangles, each with coordinates stored
        }
        
        List<CoordinateSystem> barrenLand = new ArrayList<>();

        for (Integer[] barrenLandRect : arrListOfPointsPerRect) {
        List<CoordinateSystem> allBarrenLandCoords = new ArrayList<>();
        int x1 = barrenLandRect[0]; //x1, y1 is bottom left
        int y1 = barrenLandRect[1];
        int x3 = barrenLandRect[2]; //x3, y3 is top right
        int y3 = barrenLandRect[3];
        //check if coordinates are within grid
        if(x1<0 || x1> mainLandGridWidth-1 || y1<0 || y1>mainLandGridHeight-1 || x3<0 || x3>mainLandGridWidth-1 || y3<0 || y3>mainLandGridHeight-1){
            return "Input coordinates are not within mainland grid dimensions.";
        }
         
        //taking endpoints generate all coordinates within that space
        for (int i = x1; i <= x3; i++) { //xCoord
            for (int j = y1; j <= y3; j++) { //yCoord
                CoordinateSystem coords = new CoordinateSystem(i, j);    
                allBarrenLandCoords.add(coords);
            }
        }
        
        barrenLand.addAll(allBarrenLandCoords); //list of every coordinate in every barren field rect

        }
   
        //Mark each coordinate in the barrenLand list as barren and visited
        for (CoordinateSystem c : barrenLand) {
            int xCd = c.getXCoord();
            int yCd = c.getYCoord();
            mainLandGrid[xCd][yCd] = new CoordinateSystem(xCd, yCd);
            mainLandGrid[xCd][yCd].setCoordAsBarren(true);
            mainLandGrid[xCd][yCd].setCoordAsVisited(true);
        }

       List<Integer> fertileLandAreas = new ArrayList<>();
       fertileLandAreas = findAreasOfAllFertilePlotsBFS(fertileLandAreas,0,0);
        
       String strAreas = "";
       if (!fertileLandAreas.isEmpty()) { 
          for (Integer indvArea : fertileLandAreas) { strAreas = strAreas + indvArea.toString() + " "; }
       }
       else {
          strAreas = "No fertile land available.";
       }
       return strAreas;
    }

    public static void main(String[] args) {
     //if(args.length > 0){
        //initialize grid coordinates, no land has been visited yet, assume all land is initially fertile
         for (int y = 0; y < mainLandGridHeight; y++) {
            for (int x = 0; x < mainLandGridWidth; x++) {
            CoordinateSystem coordMainGrid = new CoordinateSystem(x, y);
            coordMainGrid.setCoordAsBarren(false);
            mainLandGrid[x][y] = coordMainGrid;
           }
       }

        //Barren Land Coordinates
        /*
        //System.out.println("Please enter array of strings in proper format to begin.");
        Scanner s = new Scanner(System.in);
        String[] ipArray = {};
        for (int i = 0; i < ipArray.length; i++) {
           ipArray[i] = s.nextLine();
        }
        s.close();
        to test
        System.out.println(ipArray[0]);
        */

        String[] ipArray = {"0 292 399 307"};
        //String[] ipArray = {"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"};

        String strAreas = fertileLandAreaCalculator(ipArray);
        System.out.println(strAreas);
    }
}
package peggame;
import java.util.ArrayList;
import java.util.Scanner;

public class PegGame {
    public static void main(String[] args) {
        printIntro();
        Boolean Gameon = true;
        int BoardValue = GetBoardValue("pls fill in the board stype index value : ");
        int[][] Board = MakeBoard(BoardValue);
        printBoard(Board);
        while(Gameon){
            Board = loopGame(Board);
            printBoard(Board);

            if(GameWon(Board) || NoMovesLeft(Board)){
                Gameon = false;
            }

        }

        if(GameWon(Board)){
            System.out.println("you won");
        }
        if(NoMovesLeft(Board) && !GameWon(Board)){
            System.out.println("you loose");
        }
    }

    private static void printIntro(){

        System.out.println("                               ");
        System.out.println("                               ");
        System.out.println("WELCOME TO CS300 PEG SOLITAIRE!");
        System.out.println("===============================");
        System.out.println("                               ");
        System.out.println("                               ");
        System.out.println("Board Style Menu               ");
        System.out.println("                               ");
        System.out.println("1) Cross                       ");
        System.out.println("2) Circle                      ");
        System.out.println("3) Triangle                    ");
        System.out.println("4) Simple_t                    ");
        System.out.println("                               ");
        System.out.println("                               ");

    }

    private static int GetBoardValue(String message){
        Scanner sc = new Scanner(System.in);
        System.out.print(message);
        int value = sc.nextInt();
        if(indexof(range(1,5),value) != -1){
            return value;
        }
        return GetBoardValue("the value you filled id invalid pls choose bw 1,2,3,4 : ");
    }

    private static int[][] MakeBoard(int BoardValue){

        int[][] cross = new int[][] {
                {-1, -1, -1, 1, 1, 1, -1, -1, -1},
                {-1, -1, -1, 1, 1, 1, -1, -1, -1},
                { 1,  1,  1, 1, 1, 1,  1,  1,  1},
                { 1,  1,  1, 1, 0, 1,  1,  1,  1},
                { 1,  1,  1, 1, 1, 1,  1,  1,  1},
                {-1, -1, -1, 1, 1, 1, -1, -1, -1},
                {-1, -1, -1, 1, 1, 1, -1, -1, -1}
        };

        int[][] circle = new int[][] {
                {-1, 0, 1, 1, 0,-1},
                { 0, 1, 1, 1, 1, 0},
                { 1, 1, 1, 1, 1, 1},
                { 1, 1, 1, 1, 1, 1},
                { 0, 1, 1, 1, 1, 0},
                {-1, 0, 1, 1, 0,-1}
        };

        int[][] triangle = new int[][] {
                {-1,-1,-1, 0, 1, 0,-1,-1,-1},
                {-1,-1, 0, 1, 1, 1, 0,-1,-1},
                {-1, 0, 1, 1, 0, 1, 1, 0,-1},
                { 0, 1, 1, 1, 1, 1, 1, 1, 0}
        };

        int[][] simple_t = new int[][] {
                { 0, 0, 0, 0, 0},
                { 0, 1, 1, 1, 0},
                { 0, 0, 1, 0, 0},
                { 0, 0, 1, 0, 0},
                { 0, 0, 0, 0, 0}
        };

        ArrayList<int[][]> list = new ArrayList<>();
        list.add(cross); list.add(circle); list.add(triangle); list.add(simple_t);
        return list.get(BoardValue - 1);

    }

    private static void printBoard(int[][] Board){
        int rowcount = 1;

        ArrayList visualBoard = new ArrayList();
        for(int[] row : Board){
            StringBuffer strarray = new StringBuffer();
            strarray.append(rowcount);
            for(int number : row){
                switch (number){
                    case 1:
                        strarray.append(" @ ");
                        break;

                    case -1:
                        strarray.append(" # ");
                        break;

                    case 0:
                        strarray.append(" _ ");
                        break;
                }
            }

            visualBoard.add(strarray);
            rowcount++;
        };
        visualBoard.add(0, stringRange(Board[0].length + 1));
        for(int i = 0; i < visualBoard.size(); i++) System.out.println(visualBoard.get(i));
    }

    private static String stringRange(int len){
        StringBuffer str = new StringBuffer();

        str.append("0 ");
        for(int i = 1; i < len; i++){
            str.append(i);
            str.append("  ");
        }

        return str.toString();
    }

    private static int[] range(int len){
        int[] array = new int[len];
        for(int i = 0; i < len; i++){
            array[i] = i;
        }
        return array;
    }

    private static int[] range(int min,int max){
        int[] array = new int[max];
        for(int i = min; i < max; i++){
            array[i] = i;
        }
        return array;
    }

    private static int[][] loopGame(int[][] Board){
        int[] cordinates = GetColRow(Board);
        int row = cordinates[0]; int col = cordinates[1];
        PegLocation MoveToTake = Getmove(row, col, Board, "pls choose from the following moves : ");
        return UpdateBoard(row, col, Board, MoveToTake);
    }

    private static int[] GetColRow(int[][] Board){
        int row = getRow(Board, "row of the peg you want to move : ");
        int col = getColumn(Board, "column of the pef you want to move : ");
        int[] arr = new int[]{row, col};
        if(Board[row][col] != 1){
            System.out.println("The cordinate does not contain a peg try again");
            return GetColRow(Board);
        }
        return arr;
    }

    private static boolean GameWon(int[][] Board){

        int count = 0;

        for(int[] row : Board){
            for(int number : row){
                if(number == 1){
                    count++;
                }
            }
        }

        if(count <= 1){
            return true;
        }
        return false;
    }

    private static boolean NoMovesLeft(int[][] Board){
        int MovesLeft = 0;

        for(int i = 0; i < Board.length; i++){
            for(int j = 0; j < Board[0].length; j++){
                if (Board[i][j] == 1){
                    MovesLeft += checkNeighbours(i, j, Board).size();
                }
            }
        }

        if(MovesLeft == 0){
            return true;
        }
        return false;
    }

    private static ArrayList checkNeighbours(int i, int j, int[][] Board){
        ArrayList<PegLocation> locations = new ArrayList();

        int[][][] directions = new int[][][]{
                {{i - 1, j}, {i - 2, j}},
                {{i + 1, j}, {i + 2, j}},
                {{i, j - 1}, {i, j - 2}},
                {{i, j + 1}, {i, j + 2}}
        };

        String[] dirStrings = new String[] {"up","down","left","right"};

        for(int[][] dirRatios : directions){
            int[] remove = dirRatios[0];
            int[] reach = dirRatios[1];

            if(inbound(remove, Board) && inbound(reach, Board)){
                if(Board[remove[0]][remove[1]] == 1 && Board[reach[0]][reach[1]] == 0){
                    PegLocation loc = new PegLocation();
                    loc.Update(dirRatios, dirStrings[indexOf(directions,dirRatios)]);
                    locations.add(loc);
                }
            }

        }

        return locations;
    }

    private static int indexOf(int[][][] array, int[][] element){
        int i = 0;
        int len = array.length;
        while(i < len){
            if(array[i] == element){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    }

    private static int indexof(int[] arr, int elem){
        int i = 0;
        int len = arr.length;
        while(i < len){
            if(arr[i] == elem){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    };

    private static int indexofString(String[] arr, String elem){
        int i = 0;
        int len = arr.length;
        while(i < len){
            if(arr[i] == elem){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    };

    private static boolean inbound(int[] ratio,int[][] Board){
        if(ratio[0] < Board.length && ratio[1] < Board[0].length){
            if(ratio[0] >= 0 && ratio[1] >= 0){
                return true;
            }
        }
        return false;
    };

    private static int getColumn(int[][] Board, String message){
        Scanner sc = new Scanner(System.in);
        System.out.print(message);
        int value = sc.nextInt() - 1;

        if(value >= 0 && value < Board[0].length){
            return value;
        }
        return getColumn(Board,"the value is out of bound pls try again : ");
    };

    private static int getRow(int[][] Board, String message){
        Scanner sc = new Scanner(System.in);
        System.out.print(message);
        int value = sc.nextInt() - 1;

        if(value >= 0 && value < Board.length){
            return value;
        }
        return getRow(Board,"the value is out of bound pls try again : ");
    };

    private static PegLocation Getmove(int i, int j, int[][] Board, String message){
        ArrayList<PegLocation> locs = checkNeighbours(i, j, Board);
        StringBuffer string = new StringBuffer();


        for(PegLocation peg : locs){
            string.append("*");
            string.append(peg.dir);
            string.append(" ");
        }

        String[] dirs = new String[] {"up","down","left","right"};

        String options = string.toString();
        System.out.print(message + options + " : ");

        Scanner sc = new Scanner(System.in);
        String value = sc.next();

        if(!value.equals("up") && !value.equals("down") && !value.equals("left") && !value.equals("right")){
            return Getmove(i, j, Board, "invalid move pls choose again : ");
        }
        PegLocation MoveToTake = new PegLocation();
        for(PegLocation peg : locs){
            if(value.equals(peg.dir)) MoveToTake = peg;
        }
        return MoveToTake;
    }

    private static int[][] UpdateBoard(int row, int col, int[][] Board, PegLocation MoveToTake){
        int[][] newBoard = Board;
        newBoard[row][col] = 0;
        newBoard[MoveToTake.locToGo[0]][MoveToTake.locToGo[1]] = 1;
        newBoard[MoveToTake.locToRemove[0]][MoveToTake.locToRemove[1]] = 0;
        return newBoard;
    }

}


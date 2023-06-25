package weblab.weblab9again.domain;

public class Table {
    private char[][] table = new char[3][3];

    public Table(char[][] table) {
        this.table = table;
    }

    public Table() {
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                table[i][j]=' ';
            }
        }
    }


    public char[][] getTable() {
        return table;
    }

    public void setTable(char[][] table) {
        this.table = table;
    }

    public void putInPlace(int row, int column, char piece){
        if (row >=0 && row < 3 && column>=0 && column<3 && table[row][column]==' '){
            table[row][column] = piece;
        }
        else{
            throw new RuntimeException("coordinates are bad");
        }
    }
    public char checkForWinner(){
        //check rows
        for(int i=0;i<3;i++){
            if(table[i][0]!=' ' && table[i][0]==table[i][1] && table[i][1]==table[i][2]){
                return table[i][0];
            }
        }
        //check columns
        for(int i=0;i<3;i++){
            if(table[0][i]!=' ' && table[0][i]==table[1][i] && table[1][i]==table[2][i]){
                return table[0][i];
            }
        }
        //check diagonals
        if(table[0][0]!=' ' && table[0][0]==table[1][1] && table[1][1]==table[2][2])
            return table[0][0];
        if(table[0][2]!=' ' && table[0][2]==table[1][1] && table[1][1]==table[2][0])
            return table[0][2];
        return ' ';
    }
    public boolean checkForTie(){
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(table[i][j]==' ')
                    return false;
        return true;
    }

    public void clearTable(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                table[i][j]=' ';
            }
        }
    }

}

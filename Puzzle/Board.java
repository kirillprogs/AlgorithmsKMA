import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int dimension;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException();
        dimension = tiles.length;
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        for (int i = 0; i < tiles.length; ++i) {
            if (tiles[i].length != dimension)
                throw new IllegalArgumentException();
            this.tiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != 1 + i * dimension + j)
                        count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                int tile = tiles[i][j];
                if (tile != 0) {
                    int correctRow = (tile - 1) / dimension;
                    int correctCol = (tile - 1) % dimension;
                    sum += Math.abs(i - correctRow) + Math.abs(j - correctCol);
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        Board board = (Board) y;
        if (board.dimension != dimension)
            return false;
        for (int i = 0; i < board.dimension; ++i)
            for (int j = 0; j < board.dimension; ++j)
                if (board.tiles[i][j] != tiles[i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new LinkedList<>();
        int zeroRow = 0, zeroCol = 0;
        for (int i = 0; i < dimension; ++i)
            for (int j = 0; j < dimension; ++j)
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
        if (zeroRow != 0)
            list.add(swap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
        if (zeroCol != 0)
            list.add(swap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
        if (zeroRow != dimension - 1)
            list.add(swap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
        if (zeroCol != dimension - 1)
            list.add(swap(zeroRow, zeroCol, zeroRow, zeroCol + 1));
        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (dimension < 2)
            return new Board(this.tiles);
        else if (tiles[0][0] == 0)
            return swap(1, 0, 0, 1);
        else if (tiles[0][1] == 0)
            return swap(0, 0, 1, 0);
        return swap(0, 0, 0, 1);
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension).append('\n');
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j)
                sb.append(tiles[i][j]).append(' ');
            sb.append('\n');
        }
        return sb.toString();
    }

    private Board swap(int fstRow, int fstCol, int sndRow, int sndCol) {
        Board newBoard = new Board(tiles);
        int tmp = newBoard.tiles[fstRow][fstCol];
        newBoard.tiles[fstRow][fstCol] = newBoard.tiles[sndRow][sndCol];
        newBoard.tiles[sndRow][sndCol] = tmp;
        return newBoard;
    }
}
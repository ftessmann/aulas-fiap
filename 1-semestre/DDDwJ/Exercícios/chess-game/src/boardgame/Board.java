package boardgame;

import java.util.Arrays;
import java.util.Objects;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board() {
    }

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Erro criando o tabuleiro, necessário as colunas e linhas.");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Posição não está no tabuleiro.");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não está no tabuleiro");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("Uma peça já está na posição " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não está no tabuleiro.");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não está no tabuleiro");
        }
        return piece(position) != null;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return getRows() == board.getRows() && getColumns() == board.getColumns() && Objects.deepEquals(pieces, board.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRows(), getColumns(), Arrays.deepHashCode(pieces));
    }

    @Override
    public String toString() {
        return "Board{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", pieces=" + Arrays.toString(pieces) +
                '}';
    }
}

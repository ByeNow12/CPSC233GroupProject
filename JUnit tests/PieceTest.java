
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;


public class PieceTest
{
    @Test
    public void test_pawnMoves(){
        String[][] board = new String[][]
	{
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"w_Pa","0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"w_Kn","0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"w_Pa","0"   },
	{"0"   ,"0"   ,"b_Kn","0"   ,"0"   ,"b_Ki","0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   }
	};
	assertTrue("Pawn should be able to take diagonally", Piece.calculatePawnMoves(board, new int[] {2,6}, 'w')[3][5]);
	assertTrue("Pawn should not be able to take allies", !Piece.calculatePawnMoves(board, new int[] {1,1}, 'w')[2][0]);
	boolean[][] eReturn = new boolean[][]
	{
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,true ,false,false,false,false,false,false},
	{false,true ,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false}
	};
	boolean[][] returned = Piece.calculatePawnMoves(board, new int[] {1,1}, 'w');

        assertTrue("Expected array does not match actual", Arrays.deepEquals(returned, eReturn));
    }
    @Test
    public void test_knightMoves(){        
	String[][] board = new String[][]
	{
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"w_Pa","0"   ,"b_Pa","0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"b_Kn","0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   }
	};
	assertTrue("Knight should be able to take opponents", Piece.calculateKnightMoves(board, new int[] {3,2}, 'b')[1][1]);
	assertTrue("Knight should not be able to take allies", !Piece.calculateKnightMoves(board, new int[] {3,2}, 'b')[1][3]);
	boolean[][] eReturn = new boolean[][]
	{
	{false,false,false,false,false,false,false,false},
	{false,true ,false,false,false,false,false,false},
	{true ,false,false,false,true ,false,false,false},
	{false,false,false,false,false,false,false,false},
	{true ,false,false,false,true ,false,false,false},
	{false,true ,false,true ,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false}
	};
	boolean[][] returned = Piece.calculateKnightMoves(board, new int[] {3,2}, 'b');

        assertTrue("Expected array does not match actual", Arrays.deepEquals(returned, eReturn));
    }
    @Test
    public void test_kingMoves(){        
	String[][] board = new String[][]
	{
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"b_Qu","w_Pa","0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"b_Ki","0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   }
	};
	assertTrue("King should be able to take opponents", Piece.calculateKingMoves(board, new int[] {3,5}, 'b')[2][6]);
	assertTrue("King should not be able to take allies", !Piece.calculateKingMoves(board, new int[] {3,5}, 'b')[2][5]);
	boolean[][] eReturn = new boolean[][]
	{
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,true ,false,true ,false},
	{false,false,false,false,true ,false,true ,false},
	{false,false,false,false,true ,true ,true ,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false}
	};
	boolean[][] returned = Piece.calculateKingMoves(board, new int[] {3,5}, 'b');

        assertTrue("Expected array does not match actual", Arrays.deepEquals(returned, eReturn));
    }
    @Test
    public void test_rookMoves(){        
	String[][] board = new String[][]
	{
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"w_Ro","0"   ,"0"   ,"0"   ,"w_Pa","0"   },
	{"0"   ,"0"   ,"b_Kn","0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   }
	};
	assertTrue("Rook should be able to take opponents", Piece.calculateRookMoves(board, new int[] {2,2}, 'w')[3][2]);
	assertTrue("Rook should not be able to take allies", !Piece.calculateRookMoves(board, new int[] {2,2}, 'w')[2][6]);
	boolean[][] eReturn = new boolean[][]
	{
	{false,false,true ,false,false,false,false,false},
	{false,false,true ,false,false,false,false,false},
	{true ,true ,false,true ,true ,true ,false,false},
	{false,false,true ,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false}
	};
	boolean[][] returned = Piece.calculateRookMoves(board, new int[] {2,2}, 'w');

        assertTrue("Expected array does not match actual", Arrays.deepEquals(returned, eReturn));
    }
    @Test
    public void test_bishopMoves(){        
	String[][] board = new String[][]
	{
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"w_Pa","0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"w_Bi","0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"b_Pa","0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   }
	};
	assertTrue("Bishop should be able to take opponents", Piece.calculateBishopMoves(board, new int[] {2,2}, 'w')[4][4]);
	assertTrue("Bishop should not be able to take allies", !Piece.calculateBishopMoves(board, new int[] {2,2}, 'w')[1][1]);
	boolean[][] eReturn = new boolean[][]
	{
	{false,false,false,false,true ,false,false,false},
	{false,false,false,true ,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,true ,false,true ,false,false,false,false},
	{true ,false,false,false,true ,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false}
	};
	boolean[][] returned = Piece.calculateBishopMoves(board, new int[] {2,2}, 'w');

        assertTrue("Expected array does not match actual", Arrays.deepEquals(returned, eReturn));
    }
    @Test
    public void test_queenMoves(){        
	String[][] board = new String[][]
	{
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"w_Pa","0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"w_Qu","0"   ,"0"   ,"0"   ,"w_Pa","0"   },
	{"0"   ,"0"   ,"b_Kn","0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"b_Pa","0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   },
	{"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   ,"0"   }
	};
	assertTrue("Queen should be able to take diagonally", Piece.calculateQueenMoves(board, new int[] {2,2}, 'w')[3][2]);
	assertTrue("Queen should not be able to take allies", !Piece.calculateQueenMoves(board, new int[] {2,2}, 'w')[1][1]);
	boolean[][] eReturn = new boolean[][]
	{
	{false,false,true ,false,true ,false,false,false},
	{false,false,true ,true ,false,false,false,false},
	{true ,true ,false,true ,true ,true ,false,false},
	{false,true ,true ,true ,false,false,false,false},
	{true ,false,false,false,true ,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false},
	{false,false,false,false,false,false,false,false}
	};
	boolean[][] returned = Piece.calculateQueenMoves(board, new int[] {2,2}, 'w');

        assertTrue("Expected array does not match actual", Arrays.deepEquals(returned, eReturn));
    }
}
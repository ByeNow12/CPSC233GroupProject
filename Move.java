public class Move {
	//NEED TO ADD WHAT PIECE TYPE IS BEING MOVED (MAYBE)
	
	private String token;
	private int fromRow;
	private int fromCol;
	private int toRow;
	private int toCol;

	public Move(String token, int fromRow, int fromCol, int toRow, int toCol){
		this.token = token;
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}

	public int[] getFrom(){
		int[] fromVector = new int[2];
		fromVector[0] = fromRow;
		fromVector[1] = fromCol;
		return fromVector;
	}

	public int[] getTo(){
	int[] toVector = new int[2];
		toVector[0] = toRow;
		toVector[1] = toCol;
		return toVector;
	}
	
	public String toString(){
		return token + " ("+fromRow+","+fromCol+") to ("+toRow+","+toCol+")";
	}
	
}

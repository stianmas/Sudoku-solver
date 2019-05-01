/**
 * @author Stian Masserud
 * 
 * Contains subclasses to initialize collum, row and box.
 * Also contains method to check if a value is the row/box/collum.
 */
abstract class Entrails {

	protected Square[] array;

	/**
	 * Check if the box/collum/row contains a certain value.
	 * 
	 * @param The number to be controlled.
	 * @return true if the number is in the array,
	 * 		   fale if not.
	 */
	public boolean exist(int check) {
		for(int i = 0; i < array.length; i++) {
			if(array[i].getValue() == check) {
				return true;
			}
		}
		return false;
	}
}

/** 
 * Class to inizialize Collums
 */
class Collum extends Entrails {
	
	Collum(int length){
		array = new Square[length];
	}
}

/**
 * Class to initialize Rows
 */
class Row  extends Entrails{

	Row(int length){
		array = new Square[length];
	}
}

/**
 * Class to initialize Box
 */
class Box extends Entrails{

	Box(int length) {
	    array = new Square[length];
	}

}

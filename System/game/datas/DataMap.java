package game.datas;

import java.io.*;
import s2D.dataEngine.*;
import s2D.*;

/**
 * Specifies a map data.
 * @author Guillaume Singland
 * @version 1.0
 */
public class DataMap extends Data {
	
	private int test;
	
	/**
	 * Building a new data provokes the creation of a file for this new data and the attribution of a new ID for this data type.
	 */
	public DataMap() {
		super();
		test = 0;
	}
	
	public void initialize() {
		setID(Game.DATA_MANAGER.getFilesNumber(DataMap.class) + 1);
		Game.DATA_MANAGER.saveData(DataMap.class, this);
	}
	
	public void setTest(int t) {
		test = t;
	}
	
	public int getTest() {
		return test;
	}
	
	public String toString() {
		return "" + test;
	}
}

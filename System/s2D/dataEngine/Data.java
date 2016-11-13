package s2D.dataEngine;

import java.io.*;
import s2D.*;

/**
 * Specifies a basic data, which can be serialized. <br/>
 * <br/>
 * To create a new data type, you have to extend this class.<br/>
 * You can put all the information you need in this datatype, except the objects that don't implement the Serializable interface. <br/>
 * In other words, if you want to add in the datatype a personalized object, you have to make this object implement Serializable. <br/>
 * <br/>
 * For each data type, a system of ID is maintained, independant on the other data types.
 * It is done to let two different datas of the same type be differentiable in their files and when we need one or the other.<br/>
 * <br/>
 * For instance, given DataExample and DataTest, here can be a tree of files in the Data directory : <br/>
 * DataExample/dataExample1.sapd <br/>
 * DataExample/dataExample2.sapd <br/>
 * DataTest/dataTest1.sapd <br/>
 * DataTest/dataTest2.sapd <br/>
 * DataTest/dataTest3.sapd <br/>
 * @author Guillaume Singland
 * @version 2.0
 */
public abstract class Data implements Serializable {
	
	private int ID;
	
	public Data() {
		setID(Game.DATA.getFilesNumber(this.getClass()) + 1);
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
}

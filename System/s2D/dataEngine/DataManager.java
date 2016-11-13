package s2D.dataEngine;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.imageio.*;

/**
 * Specifies the DataManager. <br/>
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
public class DataManager {
	
	///////////// PARAMETERS /////////////////////////////
	
	public static final String DATA_EXTENSION = ".sapd";	// The extension of each data file
	
	//////////////////////////////////////////////////////
	
	private Hashtable<String, Image> cache;
	
	public DataManager() {
		cache = new Hashtable<String, Image>();
	}
	
	private <T extends Data> String getDataClass(Class<T> dataClass) {
		String[] path = dataClass.getName().split("\\.");
		return path[path.length - 1];
	}
	
	//////////////////////////////////////////////////////
	
	/**
	 * Adds the image to the cache if it isn't still in.
	 * @param path The path of the image from the Graphics directory
	 */
	public void addToCache(String path) {
		if (!cache.containsKey(path)) {
			File file = new File("../Graphics/" + path);
			Image loadedImage = null;	
			try {
				loadedImage = ImageIO.read(file);
			} catch (IOException e) {
				throw new IllegalArgumentException("The file ../Graphics/" + path + " doesn't exists.");
			} catch (Exception e) { }
			int width = -1;
			int height = -1;	
			while (width == -1 || height == -1) {
				width = loadedImage.getWidth(null);
				height = loadedImage.getHeight(null);
			}
			cache.put(path, loadedImage);
		}
	}
	
	/**
	 * Get an image from the cache.
	 * @param path The path of the image from the Graphics directory
	 */
	public Image getFromCache(String path) {
		addToCache(path);
		return cache.get(path);
	}
	
	/**
	 * Clear the cache.
	 */
	public void clearCache() {
		cache.clear();
	}
	
	/**
	 * Loads a data (of real type dataClass) in the files, identified by its ID. <br/>
	 * @param dataClass The real class of the data which can be given like this : DataExample.class
	 * @param ID The ID of the data (from 1 to the max ID)
	 */
	public <T extends Data> T loadData(Class<T> dataClass, int ID) {
		T data = null;
		String className = getDataClass(dataClass);
		try {
			FileInputStream file = new FileInputStream("../Data/" + className + ID + DATA_EXTENSION);
			ObjectInputStream stream = new ObjectInputStream(file);
			data = dataClass.cast(stream.readObject());
		} catch (Exception e) {
			System.out.println("The file ../Data/" + className + ID + DATA_EXTENSION + " doesn't exist.");
		}
		return data;
	}
	
	/**
	 * Saves data (of type dataClass) by writing it in a file.
	 */
	public <T extends Data> void saveData(T data) {
		String className = getDataClass(data.getClass());
		try {
			FileOutputStream file = new FileOutputStream("../Data/" + className + data.getID() + DATA_EXTENSION);
			ObjectOutputStream stream = new ObjectOutputStream(file);
			stream.writeObject(data);
		} catch (Exception e) {
			System.out.println("There was a problem in writing the file ../Data/" + className + data.getID() + DATA_EXTENSION + ".");
		}
	}
	
	/**
	 * Get the number of files corresponding to the data type.
	 */
	public <T extends Data> int getFilesNumber(Class<T> dataClass) {
		String className = getDataClass(dataClass);
		int id = 1;
		File file = new File("../Data/" + className + id + DATA_EXTENSION);
		while (file.exists()) {
			id ++;
			file = new File("../Data/" + className + id + DATA_EXTENSION);
		}
		return (id - 1);
	}
	
	/**
	 * Writes some text in a file.
	 * @param filePath The file path, from the Data directory
	 * @param text The text to write
	 * @param append If true, the text is added in the end of the file ; else, the file is erased, and then the text is added
	 */
	public void writeLine(String filePath, String text, boolean append) {
		try {
			FileWriter fw = new FileWriter("../Data/" + filePath, append);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw); 
			writer.println(text); 
			writer.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Reads a line of a file.
	 * @param filePath The file path, from the Data directory
	 * @param line The Number of the line, from 1 to the max number of lines of the file
	 */
	public String readLine(String filePath, int line) {
		String result = null;
		try {
			InputStream ips = new FileInputStream("../Data/" + filePath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader reader = new BufferedReader(ipsr);
			int count = 1;
			while ((result = reader.readLine()) != null && count != line) {
				count ++;
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	/**
	 * Get the number of lines in a file.
	 * @param filePath The file path, from the Data directory
	 */
	public int getLineNumber(String filePath) {
		int result = 0;
		try {
			InputStream ips = new FileInputStream("../Data/" + filePath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader reader = new BufferedReader(ipsr);
			while (reader.readLine() != null) {
				result ++;
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
}

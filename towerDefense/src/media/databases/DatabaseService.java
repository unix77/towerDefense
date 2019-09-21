package media.databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exceptions.DatabaseException;

public abstract class DatabaseService {
	
	protected File table;
	
	protected DatabaseService() {
		
	}

	public String getPath(String id) throws DatabaseException {
		String path = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(table.getAbsolutePath()));
	        String entry;
	        String[] data;
	        String line = reader.readLine();
	        boolean found = false;
	        while(!found && line != null) {
	        	entry = line.trim().replaceAll(" +", "");
	        	data = entry.split(",");
	        	found = data[0].equals(id);
	        	path = data[1];
	        	if(!found) {
	        		line = reader.readLine();
	        	}
	        }
	        reader.close();
	        if(!found) {
	        	throw new DatabaseException("The media resource could not be found in the database.");
	        }
		} catch (FileNotFoundException e) {
			System.out.println("The selected table does not exist in the database.");
		} catch (IOException e) {
			System.out.println("A row could not be read from the selected table.");
		}
        return path;
	}
	
}
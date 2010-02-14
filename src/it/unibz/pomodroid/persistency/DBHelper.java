package it.unibz.pomodroid.persistency;

import it.unibz.pomodroid.exceptions.PomodroidException;

import java.io.File;

import android.content.Context;
import android.util.Log;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import com.db4o.foundation.NotSupportedException;

/**
 * @author Thomas Schievenin
 * 
 * This class manages the open source object database db40
 *
 */

public class DBHelper {

	private static ObjectContainer database;
	private Context context;

	/**
	 * Setting database parameters
	 * 
	 * @param context
	 */
	public DBHelper(Context context) {
		DBHelper.database = null;
		this.context = context;
	}

	/**
	 * Setting the class attribute
	 * 
	 * @param database
	 */
	public void setDatabase(ObjectContainer database) {
		DBHelper.database = database;
	}

	/**
	 * Getting the database
	 * 
	 * @return an object container
	 * @throws PomodroidException 
	 */
	public ObjectContainer getDatabase() throws PomodroidException {
		try {
			if (database == null || database.ext().isClosed())
				database = Db4o.openFile(dbConfig(), db4oDBFullPath(context));
			return database;
		} catch (Exception e) {
			Log.e(DBHelper.class.getName(), e.toString());
			throw new PomodroidException("ERROR in DBHelper.getDatabase():"+e.getMessage());
		}
	}

	/**
	 * 
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Getting the database configuration
	 * 
	 * @return configuration
	 */
	private Configuration dbConfig() {
		Configuration configuration = Db4o.newConfiguration();
		return configuration;
	}

	/**
	 * 
	 * @param context
	 * @return database location
	 */
	public String db4oDBFullPath(Context context) {
		return context.getDir("data", 0) + "/" + "android.db4o";
	}

	/**
	 * Close database connection
	 */
	public void close() {
		if (database != null) {
			database.close();
			database = null;
		}
	}

	/**
	 * Deleting the database
	 */
	public void deleteDatabase() {
		close();
		new File(db4oDBFullPath(context)).delete();
	}

	/**
	 * Creates a copy of the DB
	 * 
	 * @param path
	 * @throws PomodroidException
	 */
	public void backup(String path) throws PomodroidException {
		getDatabase().ext().backup(path);
	}

	/**
	 * Restores the DB
	 * 
	 * @param path
	 */
	public void restore(String path) {
		deleteDatabase();
		new File(path).renameTo(new File(db4oDBFullPath(context)));
		new File(path).delete();
	}

}

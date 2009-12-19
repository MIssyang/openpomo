package it.unibz.pomodroid.persistency;

import java.io.File;

import android.content.Context;
import android.util.Log;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;

public class DBHelper {

	private static ObjectContainer database;
	private static Context context;

	public DBHelper(Context context) {
		DBHelper.database = null;
		DBHelper.context = context;
	}

	public void setDatabase(ObjectContainer database) {
		DBHelper.database = database;
	}

	public static ObjectContainer getDatabase() {
		try {
			if (database == null || database.ext().isClosed())
				database = Db4o.openFile(dbConfig(), db4oDBFullPath(context));
			return database;
		} catch (Exception e) {
			Log.e(DBHelper.class.getName(), e.toString());
			return null;
		}
	}

	/**
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

	private static Configuration dbConfig() {
		Configuration configuration = Db4o.newConfiguration();
		return configuration;
	}

	/**
	 * @param context
	 * @return
	 */
	public static String db4oDBFullPath(Context context) {
		return context.getDir("data", 0) + "/" + "android.db4o";
	}

	/**
	 * Close database connection
	 */
	public static void close() {
		if (database != null) {
			database.close();
			database = null;
		}
	}

	public void deleteDatabase() {
		close();
		new File(db4oDBFullPath(context)).delete();
	}

	public void backup(String path) {
		getDatabase().ext().backup(path);
	}

	public void restore(String path) {
		deleteDatabase();
		new File(path).renameTo(new File(db4oDBFullPath(context)));
		new File(path).delete();
	}

}

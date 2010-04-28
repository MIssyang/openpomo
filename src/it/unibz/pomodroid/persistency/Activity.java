/**
 * This file is part of Pomodroid.
 *
 *   Pomodroid is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Pomodroid is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.unibz.pomodroid.persistency;

import java.util.Date;
import java.util.List;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import android.util.Log;
import it.unibz.pomodroid.exceptions.PomodroidException;
import it.unibz.pomodroid.persistency.DBHelper;

/**
 * A class representing an extension of the activity class. Whit the
 * help of the open source object database db40 the activity is saved
 * into a local database.
 * @author Daniel Graziotin 4801 <daniel.graziotin@stud-inf.unibz.it>
 * @author Thomas Schievenin 5701 <thomas.schievenin@stud-inf.unibz.it>
 * 
 */
public class Activity extends it.unibz.pomodroid.models.Activity{

	/**
	 * @param numberPomodoro
	 * @param received
	 * @param deadline
	 * @param description
	 * @param origin
	 * @param originId
	 * @param reporter
	 * @param type
	 */
	public Activity(int numberPomodoro, Date received, Date deadline,
			String summary, String description, String origin, int originId,
			String priority, String reporter, String type) {
		super(numberPomodoro, received, deadline, summary, description, origin,
				originId, priority, reporter, type);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param origin
	 * @param originId
	 */
	public Activity(String origin, int originId) {
		super(origin, originId);
	}

	/**
	 * 
	 * @param origin
	 * @param originId
	 * @param dbHelper
	 * @return a specific activity
	 * @throws PomodroidException
	 */
	public static boolean isPresent(final String origin, final int originId,
			DBHelper dbHelper) throws PomodroidException {
		List<Activity> activities;
		try {
			activities = dbHelper.getDatabase().query(
					new Predicate<Activity>() {
						private static final long serialVersionUID = 1L;

						public boolean match(Activity activity) {
							return activity.getOrigin().equals(origin)
									&& activity.getOriginId() == originId;
						}
					});
			if (activities.isEmpty())
				return false;
			else
				return true;
		} catch (Exception e) {
			Log.e("Activity.isPresent()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.isPresent():"
					+ e.toString());
		}
	}

	/**
	 * @param dbHelper
	 * @return true if an activity is saved into the DB
	 * @throws PomodroidException
	 */
	public boolean save(DBHelper dbHelper) throws PomodroidException {
		try {
			if (!isPresent(this.getOrigin(), this.getOriginId(), dbHelper)) {

				dbHelper.getDatabase().store(this);
				return true;
			} else {
				return this.update(dbHelper);
			}
		} catch (Exception e) {
			Log.e("Activity.save(single)", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.save():"
					+ e.toString());
		} finally {
			dbHelper.commit();
		}
	}

	/**
	 * @param dbHelper
	 * @return true if an activity is saved into the DB
	 * @throws PomodroidException
	 */
	private boolean update(DBHelper dbHelper) throws PomodroidException {
		ObjectSet<Activity> result = dbHelper.getDatabase().queryByExample(
				new Activity(getOrigin(), getOriginId()));
		Activity found = (Activity) result.next();
		found.update(this);
		try {
			dbHelper.getDatabase().store(found);
			return true;
		} catch (Exception e) {
			Log.e("Activity.save(single)", "Update Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.save(update):"
					+ e.toString());
		} finally {
			dbHelper.commit();
		}
	}

	/**
	 * Saves a list of activities
	 * 
	 * @param Activities
	 * @param dbHelper
	 * @throws PomodroidException
	 */
	public void save(List<Activity> Activities, DBHelper dbHelper)
			throws PomodroidException {
		try {
			for (Activity activity : Activities)
				activity.save(dbHelper);
		} catch (Exception e) {
			Log.e("Activity.save(List)", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.save(List):"
					+ e.toString());
		}
	}

	/**
	 * Deletes an activity
	 * 
	 * @param dbHelper
	 * @throws PomodroidException
	 */
	public void delete(DBHelper dbHelper) throws PomodroidException {
		ObjectSet<Activity> result;
		Activity toBeDeleted = null;
		try {
			toBeDeleted = Activity.get(this.getOrigin(), this.getOriginId(), dbHelper);
			result = dbHelper.getDatabase().queryByExample(toBeDeleted);
			Activity found = (Activity) result.next();
			dbHelper.getDatabase().delete(found);
		} catch (Exception e) {
			Log.e("Activity.delete()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.delete():"
					+ e.toString());
		} finally {
			dbHelper.commit();
		}
	}

	/**
	 * Retrieves all activities
	 * 
	 * @param dbHelper
	 * @return
	 * @throws PomodroidException
	 */
	public static List<Activity> getAll(DBHelper dbHelper)
			throws PomodroidException {
		ObjectSet<Activity> result = null;
		try {
			result = dbHelper.getDatabase().queryByExample(Activity.class);
			if (result == null)
				Log.i("Activity.getAll()", "There are no activities stored in db");
		} catch (Exception e) {
			Log.e("Activity.getAll()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.getAll():" + e.toString());
		}
		return result;
	}

	/**
	 * Delete all activities
	 * 
	 * @param dbHelper
	 * @return
	 * @throws PomodroidException
	 */
	
	public static boolean deleteAll(DBHelper dbHelper) throws PomodroidException {
		ObjectSet<Activity> activities = null;
		try {			
			activities = dbHelper.getDatabase().query(new Predicate<Activity>() {
				private static final long serialVersionUID = 1L;
				public boolean match(Activity candidate){ 
					return true;
				}
			}); 
			while(activities.hasNext()) {
				dbHelper.getDatabase().delete(activities.next());
			}
			return true;
		} catch (Exception e) {
			Log.e("Activity.deleteAll()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.deleteAll():"+ e.toString());
		} finally {
			dbHelper.commit();
		}
	}

	/**
	 * @param dbHelper
	 * @return the number of activities
	 * @throws PomodroidException
	 */
	public static int getNumberActivities(DBHelper dbHelper)
			throws PomodroidException {
		List<Activity> activities = Activity.getAll(dbHelper);
		return ((activities == null) ? 0 : activities.size());
	}

	/**
	 * @param origin
	 * @param originId
	 * @param dbHelper
	 * @return a specific activity
	 * @throws PomodroidException
	 */
	public static Activity get(final String origin, final int originId,
			DBHelper dbHelper) throws PomodroidException {
		List<Activity> activities = null;
		Activity result;
		try {
			activities = dbHelper.getDatabase().query(
					new Predicate<Activity>() {
						private static final long serialVersionUID = 1L;

						public boolean match(Activity activity) {
							return activity.getOrigin().equals(origin)
									&& activity.getOriginId() == originId;
						}
					});
			result = activities.get(0);
		} catch (Exception e) {
			Log.e("Activity.getActivity()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.getActivity():"
					+ e.toString());
		}
		return result;
	}

	/**
	 * @param dbHelper
	 * @return to do today activities
	 * @throws PomodroidException
	 */
	public static List<Activity> getTodoToday(DBHelper dbHelper)
			throws PomodroidException {
		List<Activity> activities = null;
		Query query= null;
		try {
				query = dbHelper.getDatabase().query();
				query.constrain(Activity.class);
				query.descend("done").constrain(false);
				query.descend("todoToday").constrain(true);
				query.descend("deadline").orderAscending();
				activities = query.execute();
		} catch (Exception e) {
			Log.e("Activity.getTodoToday()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.getTodoToday():"
					+ e.toString());
		}
		return activities;
	}

	/**
	 * @param dbHelper
	 * @return all completed activities
	 * @throws PomodroidException
	 */
	public static List<Activity> getCompleted(DBHelper dbHelper)
			throws PomodroidException {
		List<Activity> activities = null;
		Query query= null;
		try {
			query = dbHelper.getDatabase().query();
			query.constrain(Activity.class);
			query.descend("done").constrain(true);
			query.descend("deadline").orderAscending();
			activities = query.execute();
		} catch (Exception e) {
			Log.e("Activity.getCompleted()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.getCompleted():"
					+ e.toString());
		}
		return activities;
	}

	/**
	 * @param dbHelper
	 * @return all uncompleted activities
	 * @throws PomodroidException
	 */
	public static List<Activity> getUncompleted(DBHelper dbHelper)
			throws PomodroidException {
		List<Activity> activities = null;
		Query query= null;
		try {
			query = dbHelper.getDatabase().query();
			query.constrain(Activity.class);
			query.descend("done").constrain(false);
			query.descend("deadline").orderAscending();
			activities = query.execute();
		} catch (Exception e) {
			Log.e("Activity.getUncompleted()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.getUncompleted():"
					+ e.toString());
		}
		return activities;
	}

	/**
	 * Closes an activity
	 * 
	 * @param dbHelper
	 * @throws PomodroidException
	 */
	public void close(DBHelper dbHelper) throws PomodroidException {
		try {
			this.setDone();
			this.setTodoToday(false);
			this.update(dbHelper);
		} catch (Exception e) {
			Log.e("Activity.close()", "Problem: " + e.toString());
			throw new PomodroidException("ERROR in Activity.close():"
					+ e.toString());
		}finally {
			dbHelper.commit();
		}
	}


}

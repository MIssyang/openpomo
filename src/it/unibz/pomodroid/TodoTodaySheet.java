package it.unibz.pomodroid;

import it.unibz.pomodroid.exceptions.PomodroidException;
import it.unibz.pomodroid.persistency.Activity;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author bodom_lx
 * 
 */
public class TodoTodaySheet extends SharedListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.setResourceLayout(R.layout.ttsactivityentry);
		super.setContext(this);
		super.onCreate(savedInstanceState);
	}

	/**
	 * Gets the Activities from Activity.getAll() and adds them to the local
	 * list of activities. It calls populateAdapter to populate the adapter with
	 * the new list of activities
	 * 
	 * @see it.unibz.pomodroid.persistency.Activity
	 * @throws PomodroidException
	 */
	@Override
	protected void retrieveActivities() throws PomodroidException {
		try {
			activities = new ArrayList<Activity>();
			List<Activity> retrievedActivities = Activity
					.getTodoToday(this.dbHelper);
			activities.addAll(retrievedActivities);
		} catch (Exception e) {
			throw new PomodroidException(
					"Error in retrieving Activities from the DB! Please try again");
		}
		this.runOnUiThread(populateAdapter);
	}

	/**
	 * This dialog gives the possibility to change the status of each activity
	 * 
	 * @param activity
	 */
	@Override
	protected void openActivityDialog(Activity activity) {
		final Activity selectedActivity = activity;
		new AlertDialog.Builder(this).setTitle(R.string.activity_title)
				.setItems(R.array.tts_dialog,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								try {
									switch (i) {
									case 0:
										Intent intent = new Intent();
										intent.setClass(
												getApplicationContext(),
												Pomodoro.class);
										Bundle b = new Bundle();
										b.putString("origin", selectedActivity
												.getOrigin());
										b.putInt("originId", selectedActivity
												.getOriginId());
										intent.putExtras(b);
										startActivity(intent);
										// startActivityForResult(intent,SUB_ACTIVITY_REQUEST_CODE);
										break;
									case 1:
										selectedActivity.setTodoToday(false);
										selectedActivity.setUndone();
										selectedActivity.save(dbHelper);
										activityAdapter
												.remove(selectedActivity);
										break;
									case 2:
										selectedActivity.close(dbHelper);
										activityAdapter
												.remove(selectedActivity);
										break;
									}
								} catch (PomodroidException e) {
									e.alertUser(getContext());
								} finally {
									dbHelper.close();
								}
							}
						}).show();
	}

}

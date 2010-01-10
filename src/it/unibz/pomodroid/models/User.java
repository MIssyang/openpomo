package it.unibz.pomodroid.models;

import java.util.Date;

/**
 * 
 * @author Thomas Schievenin
 *
 * A class representing a tipical pomodroid user. Each user has its username (string), password (tring), 
 * trac url (absolute url), prom url (absolute url).
 * 
 */

public class User {
	
	private String tracUsername;
	private String tracPassword;
	private String tracUrl;
	private String promUrl;
	private int pomodoroMinutesDuration;
	
	private Date dateFacedPomodoro;
	private int facedPomodoro;
	
	/**
	 * @param tracUsername pomodroid username
	 * @param tracPassword pomodroid password
	 * @param tracUrl absolute trac url
	 * @param promUrl absolute prom url
	 */
	public User(String tracUsername, String tracPassword, String tracUrl, String promUrl) {
		this.tracUsername = tracUsername;
		this.tracPassword = tracPassword;
		this.tracUrl = tracUrl;
		this.promUrl = promUrl;
		this.pomodoroMinutesDuration = 25;
		this.dateFacedPomodoro = new Date();
		this.facedPomodoro = 0;
	}

	public void update (User user){
		this.tracUsername = user.getTracUsername();
		this.tracPassword = user.getTracPassword();
		this.tracUrl = user.getTracUrl();
		this.promUrl = user.getPromUrl();
		this.pomodoroMinutesDuration = user.getPomodoroMinutesDuration();
	}
	/**
	 * @return the absolute prom Url
	 */
	public String getPromUrl() {
		return promUrl;
	}

	/**
	 * @param promUrl the promUrl to set
	 */
	public void setPromUrl(String promUrl) {
		this.promUrl = promUrl;
	}

	/**
	 * @return the trac username
	 */
	public String getTracUsername() {
		return tracUsername;
	}

	/**
	 * @param tracUsername the tracUsername to set
	 */
	public void setTracUsername(String tracUsername) {
		this.tracUsername = tracUsername;
	}

	/**
	 * @return the trac password
	 */
	public String getTracPassword() {
		return tracPassword;
	}

	/**
	 * @param tracPassword the trac password to set
	 */
	public void setTracPassword(String tracPassword) {
		this.tracPassword = tracPassword;
	}

	/**
	 * @return the tracUrl
	 */
	public String getTracUrl() {
		return tracUrl;
	}

	/**
	 * @param tracUrl the trac Url to set
	 */
	public void setTracUrl(String tracUrl) {
		this.tracUrl = tracUrl;
	}
	
	/**
	 * @return pomodoroMinutesDuration length of a Pomodoro in minutes
	 */
	public int getPomodoroMinutesDuration() {
		return pomodoroMinutesDuration;
	}

	/**
	 * @param pomodoroMinutesDuration length of a Pomodoro in minutes
	 */
	public void setPomodoroMinutesDuration(int pomodoroMinutesDuration) {
		this.pomodoroMinutesDuration = pomodoroMinutesDuration;
	}

	public Date getDateFacedPomodoro() {
		return dateFacedPomodoro;
	}

	public void setDateFacedPomodoro(Date dateFacedPomodoro) {
		this.dateFacedPomodoro = dateFacedPomodoro;
	}

	public int getFacedPomodoro() {
		return facedPomodoro;
	}

	public void setFacedPomodoro(int facedPomodoro) {
		this.facedPomodoro = facedPomodoro;
	}

	public boolean isFourthPomodoro(){
		return (this.facedPomodoro % 4==0) ;
	}
	
	public void addPomodoro(){
		 this.facedPomodoro++;
	}
	
}

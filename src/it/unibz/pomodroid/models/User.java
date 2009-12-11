package it.unibz.pomodroid.models;

public class User {
	private String tracUsername;
	private String tracPassword;
	private String tracUrl;
	private String promUrl;
	
	/**
	 * @return the promUrl
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

	public User(String tracUsername, String tracPassword, String tracUrl, String promUrl) {
		this.tracUsername = tracUsername;
		this.tracPassword = tracPassword;
		this.tracUrl = tracUrl;
		this.promUrl = promUrl;
	}

	/**
	 * @return the tracUsername
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
	 * @return the tracPassword
	 */
	public String getTracPassword() {
		return tracPassword;
	}

	/**
	 * @param tracPassword the tracPassword to set
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
	 * @param tracUrl the tracUrl to set
	 */
	public void setTracUrl(String tracUrl) {
		this.tracUrl = tracUrl;
	}
	
	
}

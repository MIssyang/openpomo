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
 *   along with Pomodroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package cc.task3.pomodroid.services;

import android.net.NetworkInfo;

import java.net.URI;
import org.xmlrpc.android.XMLRPCClient;

import cc.task3.pomodroid.exceptions.PomodroidException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * A class that uses the remote procedure call protocol XML-RPC to retrieve information through the HTTP 
 * as a transport mechanism.
* @author Daniel Graziotin <d AT danielgraziotin DOT it>
* @author Thomas Schievenin <thomas.schievenin@stud-inf.unibz.it> * 
 * 
 */
public class XmlRpcClient {
	
	/**
	 * @param url
	 * @param method
	 * @param params
	 * @return a single object
	 * @throws PomodroidException 
	 */
	public static Object fetchSingleResult(String url, String method,
			Object[] params) throws PomodroidException {

		URI uri = URI.create(url);
		XMLRPCClient client = new XMLRPCClient(uri);

		Object result = null;

		try {
			result = client.call(method, params);
		} catch (Exception e) {
			Log.e("XML-RPC exception", e.toString());
			throw new PomodroidException("Connection failed: "+e.toString());
		}
		return result;
	}

	/**
	 * @param url
	 * @param method
	 * @param params
	 * @return one or more objects
	 * @throws PomodroidException 
	 */
	public static Object[] fetchMultiResults(String url, String method,
			Object[] params) throws PomodroidException {

		URI uri = URI.create(url);
		XMLRPCClient client = new XMLRPCClient(uri);

		Object[] result = null;

		try {
			result = (Object[]) client.call(method, params);
		} catch (Exception e) {
			Log.e("XML-RPC exception", e.toString());
			throw new PomodroidException("Connection failed: "+e.toString());
		}
		return result;
	}

	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param method
	 * @param params
	 * @return signle object
	 * @throws PomodroidException 
	 */
	public static Object fetchSingleResult(String url, String username,
			String password, String method, Object[] params) throws PomodroidException {

		URI uri = URI.create(url);
		XMLRPCClient client = new XMLRPCClient(uri);
		client.setBasicAuthentication(username, password);
		Object result = null;

		try {
			result = client.call(method, params);
		} catch (Exception e) {
			Log.e("XML-RPC exception", e.toString());
			throw new PomodroidException("Connection failed: "+e.toString());
		}
		return result;
	}

	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param method
	 * @param params
	 * @return one or more objects
	 * @throws PomodroidException 
	 */
	public static Object[] fetchMultiResults(String url, String username,
			String password, String method, Object[] params) throws PomodroidException {

		URI uri = URI.create(url);
		XMLRPCClient client = new XMLRPCClient(uri);
		client.setBasicAuthentication(username, password);
		Object[] result = null;

		try {
			result = (Object[]) client.call(method, params);
		} catch (Exception e) {
			Log.e("XML-RPC exception", e.toString());
			throw new PomodroidException("Connection failed: "+e.toString());
		}
		return result;
	}
	
	/**
	 *@return boolean return true if the application can access the internet
	 */
	public static boolean isInternetAvailable(Context context){
        NetworkInfo connectionAvailable = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(connectionAvailable==null || !connectionAvailable.isConnected()){
			return false;
		}
		if(connectionAvailable.isRoaming()){
			return true;
		}
		return true;
	}
}

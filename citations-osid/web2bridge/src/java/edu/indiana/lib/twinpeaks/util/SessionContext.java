/**********************************************************************************
*
* Copyright (c) 2003, 2004 The Regents of the University of Michigan, Trustees of Indiana University,
*                  Board of Trustees of the Leland Stanford, Jr., University, and The MIT Corporation
*
* Licensed under the Educational Community License Version 1.0 (the "License");
* By obtaining, using and/or copying this Original Work, you agree that you have read,
* understand, and will comply with the terms and conditions of the Educational Community License.
* You may obtain a copy of the License at:
*
*      http://cvs.sakaiproject.org/licenses/license_1_0.html
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
* INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
* AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
**********************************************************************************/
package edu.indiana.lib.twinpeaks.util;

import edu.indiana.lib.twinpeaks.search.*;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.sql.*;
import java.util.*;

import net.sf.ehcache.*;


/**
 * Expose the session context block
 */
public class SessionContext
{

	private static org.apache.commons.logging.Log	_log = LogUtils.getLog(SessionContext.class);
	/*
	 * ehcache constants
	 */
	/**
	 * Cache name
	 */
	private final static String	CACHENAME			= "org.sakai.sakaibrary.http-osid-sessioncache";
	/**
	 * Items held in memory
	 */
	private static int CACHE_MEMORY_ELEMENTS 	= 50;
	/**
	 * Cache entry maximum time-to-live (seconds).  Zero is infinite.
	 */
	private static int CACHE_TTL							= 12 * (60 * 60);
	/**
	 * Cache entry idle time (seconds)
	 */
	private static int CACHE_IDLE_TIME				= 15 * 60;
	/**
	 * Session id (passed by caller)
	 */
	private String _sessionId 								= null;
	/**
	 * Local storage for name=value pairs
	 */
	private HashMap	_parameterMap							= null;

  /**
   * Private constructor - set up the cache as required
   */
  private SessionContext(String id)
  {
		Element 			element;

		try
		{
			CacheManager	cacheManager;
	  	Cache					cache;

			/*
			 * Fetch singleton cache manager
			 */
			cacheManager = CacheManager.create();
			/*
			 * And create our cache (if necessary)
			 */
			if ((cache = cacheManager.getCache(CACHENAME)) == null)
			{
				cache = new Cache(CACHENAME,							// Name
													CACHE_MEMORY_ELEMENTS, 	// Elements held in memory
													true, 									// Overflow to disk?
													false, 									// Disk elements live forever?
													CACHE_TTL, 							// Element maximum time to live
													CACHE_IDLE_TIME, 				// Element idle time removal
													false, 									// Disk content exists across VM restart?
													240);										// Disk content idle check frequency

				cacheManager.addCache(cache);
				_log.debug("Cache created: " + CACHENAME);
			}
			_log.debug(CACHENAME + " size = " + cache.getSize());
			_log.debug(CACHENAME + " keys = " + cache.getKeys().toString());
			/*
			 * Fetch the session cache (create a new one if necessary)
			 */
			element = cache.get(id);
			_log.debug("cache.get(" + id + ") finds " + element);

			if (element == null)
			{
				element = new Element(id, new HashMap());
				cache.put(element);

				_log.debug("HashMap() created for id " + id);

			}
		}
		catch (Exception exception)
		{
			throw new SearchException(exception.toString());
		}

		_sessionId = id;
 		_parameterMap = (HashMap) element.getValue();
	}

  /**
   * Get a Session Context instance
   */
  public static SessionContext getInstance(String id)
  {
		return new SessionContext(id);
	}

	/**
	 * Normalize a parameter name (add a simple prefix to the parameter name; this
	 *														 differentiates our names from those generated
	 *														 by ehcache)
	 * @param name Parameter name
	 * @return Normalized name
	 */
	private static String normalize(String name) {
		return "_" + name.trim();
	}

  /**
   * Fetch a value
   * @param name Attribute name
   * @return Requested value
   */
  public Object get(String name) {
    return _parameterMap.get(normalize(name));
  }

  /**
   * Set a name=value pair
   * @param name Attribute name
   * @param value Attribute value
   */
  public void put(String name, Object value) {
    _parameterMap.put(normalize(name), value);
  }

  /**
   * Fetch an int value
   * @param name Attribute name
   * @return Requested value
   */
  public int getInt(String name) {
    String value = (String) _parameterMap.get(normalize(name));

    try
    {
    	return Integer.parseInt(value);
		}
		catch (Exception NumberFormatException)
		{
			throw new SearchException("Invalid number: " + value);
		}
  }

  /**
   * Set a name=value pair
   * @param name Attribute name
   * @param value Attribute value
   */
  public void putInt(String name, int value) {
    _parameterMap.put(normalize(name), String.valueOf(value));
  }

  /**
   * Delete a name=value pair
   * @param name Attribute name
   */
  public void remove(String name) {
    _parameterMap.remove(normalize(name));
  }

	/*
	 * Helpers for the "search source" handlers
	 */

  /**
   * Construct a session-wide unique name (unique within a browser session)
   * @parameter The parent (calling) Object
   */
	public static String uniqueSessionName(Object parent) {
    /*
     * With ehcache, we don't need the following level of "unique detail":
     *
     *   return StringUtils.replace(parent.getClass().getName(), "\\.", ":");
     */
    return "";
  }
}
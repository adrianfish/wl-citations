/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2006 The Sakai Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.citation.api;

import java.util.Comparator;
import java.util.Iterator;

/**
 * 
 */
public interface CitationIterator extends Iterator
{
	/**
	 * @return
	 */
	public int getPage();
	
	/**
     * @return
     */
    public int getPageSize();
	
	/**
	 * @return
	 */
	public boolean hasNextPage();
	
	/**
	 * @return
	 */
	public boolean hasPreviousPage();
	
	/**
	 * 
	 */
	public void nextPage();
	
	/**
	 * 
	 */
	public void previousPage();

	/**
	 * @param comparator
	 */
	public void setOrder(Comparator comparator);
	
	/**
	 * @param page
	 */
	public void setPage(int page);

	/**
	 * @param size
	 */
	public void setPageSize(int size);


}

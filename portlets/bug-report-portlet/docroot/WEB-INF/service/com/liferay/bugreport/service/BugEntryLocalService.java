/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.bugreport.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service interface for BugEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see BugEntryLocalServiceUtil
 * @see com.liferay.bugreport.service.base.BugEntryLocalServiceBaseImpl
 * @see com.liferay.bugreport.service.impl.BugEntryLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BugEntryLocalService extends BaseLocalService,
	InvokableLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BugEntryLocalServiceUtil} to access the bug entry local service. Add custom service methods to {@link com.liferay.bugreport.service.impl.BugEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	/**
	* NOTE FOR DEVELOPERS: Never reference this interface directly. Always use
	* {@link com.liferay.bugreport.service.BugEntryLocalServiceUtil} to access
	* the bug entry local service.
	*
	* @throws SolrServerException
	*/
	public long countBugEntriesLast24hours()
		throws org.apache.solr.client.solrj.SolrServerException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List getBugEntriesLast24hours(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException;

	public long countBugEntriesLast7days()
		throws org.apache.solr.client.solrj.SolrServerException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List getBugEntriesLast7days(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException;

	public int countPortletsWithBugs()
		throws org.apache.solr.client.solrj.SolrServerException;

	public long countRecurrentBugEntries()
		throws org.apache.solr.client.solrj.SolrServerException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List getBugEntries(java.util.Date startDate,
		java.util.Date endDate, int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException;
}
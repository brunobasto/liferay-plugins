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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BugEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see BugEntryLocalService
 * @generated
 */
public class BugEntryLocalServiceWrapper implements BugEntryLocalService,
	ServiceWrapper<BugEntryLocalService> {
	public BugEntryLocalServiceWrapper(
		BugEntryLocalService bugEntryLocalService) {
		_bugEntryLocalService = bugEntryLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _bugEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_bugEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _bugEntryLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public long countAllBugEntries()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.countAllBugEntries();
	}

	@Override
	public long countBugEntriesLast7days()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.countBugEntriesLast7days();
	}

	@Override
	public long countBugEntriesLast24hours()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.countBugEntriesLast24hours();
	}

	@Override
	public int countPortletsWithBugs()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.countPortletsWithBugs();
	}

	@Override
	public long countRecurrentBugEntries()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.countRecurrentBugEntries();
	}

	@Override
	public long countSingleBugEntries()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.countSingleBugEntries();
	}

	@Override
	public java.util.List getAllBugEntries(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getAllBugEntries(start, end);
	}

	@Override
	public java.util.List getBugEntries(java.util.Date startDate,
		java.util.Date endDate, int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getBugEntries(startDate, endDate, start,
			end);
	}

	@Override
	public java.util.List getBugEntriesLast7days(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getBugEntriesLast7days(start, end);
	}

	@Override
	public java.util.List getBugEntriesLast24hours(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getBugEntriesLast24hours(start, end);
	}

	/**
	* NOTE FOR DEVELOPERS: Never reference this interface directly. Always use
	* {@link com.liferay.bugreport.service.BugEntryLocalServiceUtil} to access
	* the bug entry local service.
	*
	* @throws SolrServerException
	*/
	@Override
	public org.apache.solr.common.SolrDocument getDocument(java.lang.String uid)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getDocument(uid);
	}

	@Override
	public java.util.List<org.apache.solr.client.solrj.response.Group> getPortletsWithBugs()
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getPortletsWithBugs();
	}

	@Override
	public java.util.List getRecurrentBugEntries(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getRecurrentBugEntries(start, end);
	}

	@Override
	public java.util.List getSingleBugEntries(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return _bugEntryLocalService.getSingleBugEntries(start, end);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public BugEntryLocalService getWrappedBugEntryLocalService() {
		return _bugEntryLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedBugEntryLocalService(
		BugEntryLocalService bugEntryLocalService) {
		_bugEntryLocalService = bugEntryLocalService;
	}

	@Override
	public BugEntryLocalService getWrappedService() {
		return _bugEntryLocalService;
	}

	@Override
	public void setWrappedService(BugEntryLocalService bugEntryLocalService) {
		_bugEntryLocalService = bugEntryLocalService;
	}

	private BugEntryLocalService _bugEntryLocalService;
}
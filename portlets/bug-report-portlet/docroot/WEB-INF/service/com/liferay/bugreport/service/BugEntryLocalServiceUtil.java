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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for BugEntry. This utility wraps
 * {@link com.liferay.bugreport.service.impl.BugEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BugEntryLocalService
 * @see com.liferay.bugreport.service.base.BugEntryLocalServiceBaseImpl
 * @see com.liferay.bugreport.service.impl.BugEntryLocalServiceImpl
 * @generated
 */
public class BugEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.bugreport.service.impl.BugEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static long countBugEntriesLast7days()
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().countBugEntriesLast7days();
	}

	public static long countBugEntriesLast24hours()
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().countBugEntriesLast24hours();
	}

	public static int countPortletsWithBugs()
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().countPortletsWithBugs();
	}

	public static long countRecurrentBugEntries()
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().countRecurrentBugEntries();
	}

	public static long countSingleBugEntries()
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().countSingleBugEntries();
	}

	public static java.util.List getBugEntries(java.util.Date startDate,
		java.util.Date endDate, int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().getBugEntries(startDate, endDate, start, end);
	}

	public static java.util.List getBugEntriesLast7days(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().getBugEntriesLast7days(start, end);
	}

	public static java.util.List getBugEntriesLast24hours(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().getBugEntriesLast24hours(start, end);
	}

	/**
	* NOTE FOR DEVELOPERS: Never reference this interface directly. Always use
	* {@link com.liferay.bugreport.service.BugEntryLocalServiceUtil} to access
	* the bug entry local service.
	*
	* @throws SolrServerException
	*/
	public static org.apache.solr.common.SolrDocument getDocument(
		java.lang.String uid)
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().getDocument(uid);
	}

	public static java.util.List getRecurrentBugEntries(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().getRecurrentBugEntries(start, end);
	}

	public static java.util.List getSingleBugEntries(int start, int end)
		throws org.apache.solr.client.solrj.SolrServerException {
		return getService().getSingleBugEntries(start, end);
	}

	public static void clearService() {
		_service = null;
	}

	public static BugEntryLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					BugEntryLocalService.class.getName());

			if (invokableLocalService instanceof BugEntryLocalService) {
				_service = (BugEntryLocalService)invokableLocalService;
			}
			else {
				_service = new BugEntryLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(BugEntryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(BugEntryLocalService service) {
	}

	private static BugEntryLocalService _service;
}
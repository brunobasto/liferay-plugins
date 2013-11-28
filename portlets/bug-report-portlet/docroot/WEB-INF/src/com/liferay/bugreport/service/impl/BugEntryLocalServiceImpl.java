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

package com.liferay.bugreport.service.impl;

import com.liferay.bugreport.service.base.BugEntryLocalServiceBaseImpl;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;

/**
 * The implementation of the bug entry local service. <p> All custom service
 * methods should be put in this class. Whenever methods are added, rerun
 * ServiceBuilder to copy their definitions into the
 * {@link com.liferay.bugreport.service.BugEntryLocalService} interface. <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM. </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.bugreport.service.base.BugEntryLocalServiceBaseImpl
 * @see com.liferay.bugreport.service.BugEntryLocalServiceUtil
 */
public class BugEntryLocalServiceImpl extends BugEntryLocalServiceBaseImpl {

	public long countAllBugEntries() throws SolrServerException {
		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), 0, 0, "portletId");

		return results.getNumFound();
	}

	public long countBugEntriesLast7days() throws SolrServerException {
		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(),"exceptionDateTime:[NOW-7DAY TO NOW]", 0, 0, "portletId");

		return results.getNumFound();
	}

	public long countBugEntriesLast24hours() throws SolrServerException {
		SolrDocumentList results =
			_executeQuery(
				_buildQueryGetAllBugEntries(), "exceptionDateTime:[NOW-24HOUR TO NOW]", 0, 0, "portletId");

		return results.getNumFound();
	}

	public int countPortletsWithBugs() throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setQuery(_buildQueryGetAllBugEntries());
		query.setStart(0);
		query.setRows(0);
		query.setFields("portletId");
		query.setParam(GroupParams.GROUP, Boolean.TRUE);
		query.setParam(GroupParams.GROUP_TOTAL_COUNT, Boolean.TRUE);
		query.setParam(GroupParams.GROUP_FIELD, "portletId");

		QueryResponse response = solr.query(query);
		GroupResponse groupResponse = response.getGroupResponse();

		return groupResponse.getValues().get(0).getNGroups();
	}

	public long countRecurrentBugEntries() throws SolrServerException {
		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), "exceptionOccurrences:[2 TO *]", 0,
			0, "portletId");

		return results.getNumFound();
	}

	public long countSingleBugEntries() throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), "exceptionOccurrences:1", 0, 0,
			"portletId");

		return results.getNumFound();
	}

	public List getAllBugEntries(int start, int end)
		throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), start, end, "portletId");

		return results;
	}

	public List getBugEntries(Date startDate, Date endDate, int start, int end)
		throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildDateRangeQuery(startDate, endDate), start, end);

		return results;
	}

	public List getBugEntriesLast7days(int start, int end)
		throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), "exceptionDateTime:[NOW-7DAY TO NOW]", start, end);

		return results;
	}

	public List getBugEntriesLast24hours(int start, int end)
		throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), "exceptionDateTime:[NOW-24HOUR TO NOW]", start, end);

		return results;
	}

	/**
	 * NOTE FOR DEVELOPERS: Never reference this interface directly. Always use
	 * {@link com.liferay.bugreport.service.BugEntryLocalServiceUtil} to access
	 * the bug entry local service.
	 *
	 * @throws SolrServerException
	 */

	public SolrDocument getDocument(String uid) throws SolrServerException {

		SolrDocumentList results = _executeQuery("uid:"+uid, 0, 1);

		if (results.getNumFound() == 0) {
			return null;
		}

		return results.get(0);
	}

	public List<Group> getPortletsWithBugs() throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setQuery(_buildQueryGetAllBugEntries());
		query.setStart(0);
		query.setRows(1000);
		query.setFields("portletId", "displayName");
		query.setParam(GroupParams.GROUP, Boolean.TRUE);
		query.setParam(GroupParams.GROUP_TOTAL_COUNT, Boolean.TRUE);
		query.setParam(GroupParams.GROUP_FIELD, "portletId", "displayName");

		QueryResponse response = solr.query(query);
		GroupResponse groupResponse = response.getGroupResponse();

		return groupResponse.getValues().get(0).getValues();
	}

	public List getRecurrentBugEntries(int start, int end)
		throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), "exceptionOccurrences:[2 TO *]",
			start, end);

		return results;
	}

	public List getSingleBugEntries(int start, int end)
		throws SolrServerException {

		SolrDocumentList results = _executeQuery(
			_buildQueryGetAllBugEntries(), "exceptionOccurrences:1", start,
			end);

		return results;
	}

	private String _buildDateRangeQuery(Date startDate, Date endDate) {

		StringBuilder query = new StringBuilder("exceptionDateTime:[");
		query.append(FORMAT.format(startDate));
		query.append(" TO ");
		query.append(FORMAT.format(endDate));
		query.append("]");

		return query.toString();
	}

	private String _buildQueryGetAllBugEntries() {

		return "exceptionType:*";
	}

	private SolrDocumentList _executeQuery(
			String queryString, int start, int end, String... fields)
		throws SolrServerException {

		return _executeQuery(queryString, null, start, end, fields);
	}

	private SolrDocumentList _executeQuery(
			String queryString, String filter, int start, int end,
			String... fields)
		throws SolrServerException {

		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);

		if (filter != null) {
			query.addFilterQuery(filter);
		}

		query.setStart(start);
		query.setRows(end);

		System.out.println(query.toString());

		QueryResponse response = solr.query(query);

		SolrDocumentList results = response.getResults();

		return results;
	}

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
		"yyyy-MM-DD'T'hh:mm:ss'Z'");

	private HttpSolrServer solr = new HttpSolrServer(
		"http://10.0.1.27:8983/solr");

}
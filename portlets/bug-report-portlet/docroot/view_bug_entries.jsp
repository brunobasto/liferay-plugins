<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String filter = ParamUtil.getString(request, "filter");
%>

<h3><%= filter %></h3>

<liferay-portlet:renderURL varImpl="portletURL">
	<liferay-portlet:param name="mvcPath" value="/view.jsp" />
	<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:renderURL>

<%
ArrayList<String> headerNames = new ArrayList<String>();

headerNames.add("Name");
headerNames.add("Portlet");
headerNames.add("Occurences");
headerNames.add("Last Occurence");
%>

<liferay-ui:search-container
	searchContainer='<%= new SearchContainer<Object>(renderRequest, portletURL, headerNames, "no-bugs-found") %>'
>
	<liferay-ui:search-container-results>

		<%
		if (filter.equals("last-24-hours")) {
			results = (SolrDocumentList)BugEntryLocalServiceUtil.getBugEntriesLast24hours(searchContainer.getStart(), searchContainer.getEnd());
		}
		else if (filter.equals("single-entries")) {
			results = (SolrDocumentList)BugEntryLocalServiceUtil.getSingleBugEntries(searchContainer.getStart(), searchContainer.getEnd());
		}
		else if (filter.equals("recurrent-entries")) {
			results = (SolrDocumentList)BugEntryLocalServiceUtil.getRecurrentBugEntries(searchContainer.getStart(), searchContainer.getEnd());
		}
		else if (filter.equals("portlets")) {

		}
		else {
			results = (SolrDocumentList)BugEntryLocalServiceUtil.getAllBugEntries(searchContainer.getStart(), searchContainer.getEnd());
		}

		searchContainer.setResults(results);
		%>

	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row
		className="org.apache.solr.common.SolrDocument"
		modelVar="bugEntry"
	>

		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="mvcPath" value="/view_bug_entry_details.jsp" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="bugEntryUID" value='<%= String.valueOf(bugEntry.get("uid")) %>' />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="Name"
			value='<%= String.valueOf(bugEntry.get("exceptionMessage")) %>'
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="Portlet"
			value='<%= String.valueOf(bugEntry.get("displayName")) %>'
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="Occurences"
			value='<%= String.valueOf(bugEntry.get("exceptionOccurrences")) %>'
		/>

		<liferay-ui:search-container-column-date
			href="<%= rowURL %>"
			name="Last Occurence"
			value="<%= BugEntryUtil.getBugEntryDate(bugEntry) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/bug_entry_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-store"></aui:script>
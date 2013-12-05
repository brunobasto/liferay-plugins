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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SolrDocument bugEntry = (SolrDocument)row.getObject();
%>

<liferay-ui:icon-menu
	cssClass="bug-entry-action"
	showWhenSingleIcon="<%= true %>"
>

	<liferay-portlet:renderURL var="viewURL">
		<portlet:param name="mvcPath" value="/view_bug_entry_details.jsp" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="bugEntryUID" value='<%= String.valueOf(bugEntry.get("uid")) %>' />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		image="view"
		method="get"
		url="<%= viewURL %>"
	/>
</liferay-ui:icon-menu>
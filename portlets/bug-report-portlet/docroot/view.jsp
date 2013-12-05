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
String filter = GetterUtil.getString(SessionClicks.get(request, "bug-entries-portlet-filter", "all-errors"));
%>

<div class="bug-info-container" id="<portlet:namespace />bugInfoContainer">
	<div class="bug-info-wrapper">
		<div class="bug-info well">
			<div class="bug-info-header">All</div>
			<div class="<%= filter.equals("all-errors") ? "active" : StringPool.BLANK %> bug-info-content" data-filter="all-errors"><%= BugEntryLocalServiceUtil.countAllBugEntries() %></div>
			<div class="bug-info-footer">Exceptions</div>
		</div>
	</div>

	<div class="bug-info-wrapper">
		<div class="bug-info well">
			<div class="bug-info-header">In Last 24 hours</div>
			<div class="<%= filter.equals("last-24-hours") ? "active" : StringPool.BLANK %> bug-info-content" data-filter="last-24-hours"><%= BugEntryLocalServiceUtil.countBugEntriesLast24hours() %></div>
			<div class="bug-info-footer">Exceptions</div>
		</div>
	</div>

	<div class="bug-info-wrapper">
		<div class="bug-info well">
			<div class="bug-info-header">Since Last Fix Pack</div>
			<div class="<%= filter.equals("single-entries") ? "active" : StringPool.BLANK %> bug-info-content" data-filter="single-entries"><%= BugEntryLocalServiceUtil.countSingleBugEntries() %></div>
			<div class="bug-info-footer">New Exceptions</div>
		</div>
	</div>

	<div class="bug-info-wrapper">
		<div class="bug-info well">
			<div class="bug-info-header">Since Last Fix Pack</div>
			<div class="<%= filter.equals("recurrent-entries") ? "active" : StringPool.BLANK %> bug-info-content" data-filter="recurrent-entries"><%= BugEntryLocalServiceUtil.countRecurrentBugEntries() %></div>
			<div class="bug-info-footer">New Occurences</div>
		</div>
	</div>

	<div class="bug-info-wrapper">
		<div class="bug-info well">
			<div class="bug-info-header">By Now</div>
			<div class="<%= filter.equals("portlets") ? "active" : StringPool.BLANK %> bug-info-content" data-filter="portlets"><%= BugEntryLocalServiceUtil.countPortletsWithBugs() %></div>
			<div class="bug-info-footer">Portlets with Errors</div>
		</div>
	</div>
</div>

<div class="separator"><!-- --></div>

<div id="<portlet:namespace />resultsContainer">
	<liferay-util:include page="/view_bug_entries.jsp" servletContext="<%= application %>">
		<liferay-util:param name="filter" value="<%= filter %>" />
	</liferay-util:include>
</div>

<liferay-portlet:renderURL varImpl="viewBugEntriesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="mvcPath" value="/view_bug_entries.jsp" />
	<liferay-portlet:param name="filter" value="{filter}" />
	<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:renderURL>

<aui:script use="aui-io-plugin-deprecated,liferay-store">
	var resultsContainer = A.one('#<portlet:namespace />resultsContainer');

	var lastActive = A.one('.bug-info-content.active');

	A.one('#<portlet:namespace />bugInfoContainer').delegate(
		'click',
		function(event) {
			var target = event.currentTarget;

			var filter = target.attr('data-filter');

			if (lastActive) {
				lastActive.removeClass('active');
			}

			target.addClass('active');

			lastActive = target;

			Liferay.Store("bug-entries-portlet-filter", filter);

			var uri = A.Lang.sub(
				decodeURIComponent('<%= viewBugEntriesURL %>'),
				{
					filter: filter
				}
			);

			resultsContainer.plug(
				A.Plugin.IO,
				{
					autoLoad: false,
					method: 'GET',
					uri: uri
				}
			);

			resultsContainer.io.start();
		},
		'.bug-info-content'
	);
</aui:script>
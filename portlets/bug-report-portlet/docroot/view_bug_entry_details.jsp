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
String backURL = ParamUtil.getString(request, "backURL");

String bugEntryUID = ParamUtil.getString(request, "bugEntryUID");

String tabs1 = ParamUtil.getString(request, "tabs1", "summary");

SolrDocument bugEntry = BugEntryLocalServiceUtil.getDocument(bugEntryUID);
%>

<c:choose>
	<c:when test="<%= bugEntry != null %>">
		<liferay-portlet:renderURL varImpl="portletURL">
			<portlet:param name="mvcPath" value="/view_bug_entry_details.jsp" />
			<portlet:param name="backURL" value="<%= backURL %>" />
			<portlet:param name="bugEntryUID" value="<%= bugEntryUID %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:header
			backURL="<%= backURL %>"
			title='<%= String.valueOf(bugEntry.get("exceptionMessage")) %>'
		/>

		<liferay-ui:tabs
			names="summary,environment,request parameters,session,similar issues"
			param="tabs1"
			url="<%= portletURL.toString() %>"
		/>

		<c:choose>
			<c:when test='<%= tabs1.equals("environment") %>'>
				<pre><%= bugEntry.get("environmentProperties") %></pre>
			</c:when>
			<c:when test='<%= tabs1.equals("request parameters") %>'>
				<%= _beautifyJSON(String.valueOf(bugEntry.get("requestParameters"))) %>
			</c:when>
			<c:when test='<%= tabs1.equals("session") %>'>
				<pre><%= bugEntry.get("sessionAttributes") %></pre>
			</c:when>
			<c:when test='<%= tabs1.equals("similar issues") %>'>

				<%
				List<Issue> issues = JiraUtil.getIssues(String.valueOf(bugEntry.get("exceptionStackTrace")));

				for (Issue issue : issues) {
				%>

					<div class="alert alert-info">
						<a href="https://issues.liferay.com/browse/<%= issue.getKey() %>" target="_BLANK">
							<%= issue.getKey() %> - <%= issue.getSummary() %>
						</a>
					</div>

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<pre><%= bugEntry.get("exceptionStackTrace") %></pre>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<liferay-ui:header
			backURL="<%= backURL %>"
			title="Error"
		/>

		No bug entry found with the uuid "<%= HtmlUtil.escape(bugEntryUID) %>"
	</c:otherwise>
</c:choose>

<%!
private static String _beautifyJSON(String json) throws Exception {
	System.out.println(json);
	System.out.println("--------------------------------------");

	HashMap<String, Object> jsonMap = (HashMap<String, Object>)JSONFactoryUtil.looseDeserialize(json);

	StringBundler sb = new StringBundler();

	for (String key : jsonMap.keySet()) {
		sb.append("<div class=\"parameter-value\">");
		sb.append("<b>");
		sb.append(key);
		sb.append("</b>: ");
		sb.append(String.valueOf(jsonMap.get(key)));
		sb.append("</div>");
	}

	return sb.toString();
}
%>
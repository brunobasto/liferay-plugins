package com.liferay.bugreport.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;


public class JiraUtil {

	private static final Set<String> FIELDS_TO_RETRIEVE = new HashSet<String>(
		Arrays.asList("created", "issuetype", "key", "project", "status",
		"summary", "updated"));

	private static final String JQL =
		"project=\"PUBLIC - Liferay Portal Community Edition\" and text~\"?\"";

	private static SearchRestClient SEARCH_CLIENT;

//	static {
//		_initSearchClient();
//	}

	public static List<Issue> getIssues(String stackTrace){

		List<Issue> issues = new ArrayList<Issue>();

        SearchResult results = _getSearchClient().searchJql(
        	_createQuery(stackTrace),5,0,FIELDS_TO_RETRIEVE).claim();

        for (final Issue issue : results.getIssues()) {
        	issues.add(issue);
        }

        return issues;
	}

	public static void main(String[] args) {

		StringBuilder st = new StringBuilder();

        st.append("java.lang.UnsupportedOperationException");
//        st.append("	at java.util.AbstractList.add(AbstractList.java:131)");
//		st.append("	at com.liferay.portal.model.impl.LayoutTypePortletImpl.addPortletId(LayoutTypePortletImpl.java:378)");
//		st.append("	at com.liferay.portal.model.impl.LayoutTypePortletImpl.addPortletId(LayoutTypePortletImpl.java:306)");
//		st.append("	at com.liferay.portal.model.impl.LayoutTypePortletImpl.movePortletId(LayoutTypePortletImpl.java:499)");
//		st.append("	at com.liferay.portal.action.UpdateLayoutAction.execute(UpdateLayoutAction.java:100)");
//		st.append("	at org.apache.struts.action.RequestProcessor.processActionPerform(RequestProcessor.java:431)");
//		st.append("	at org.apache.struts.action.RequestProcessor.process(RequestProcessor.java:236)");
//		st.append("	at com.liferay.portal.struts.PortalRequestProcessor.process(PortalRequestProcessor.java:153)");
//		st.append("	at org.apache.struts.action.ActionServlet.process(ActionServlet.java:1196)");
//		st.append("	at org.apache.struts.action.ActionServlet.doPost(ActionServlet.java:432)");
//		st.append("	at javax.servlet.http.HttpServlet.service(HttpServlet.java:637)");
//		st.append("	at com.liferay.portal.servlet.MainServlet.callParentService(MainServlet.java:600)");

		List<Issue> issues = getIssues(st.toString());

		for (Issue issue : issues) {
			System.out.println(issue.getKey());
		}
	}

	private static String _createQuery(String stackTrace) {

		return JQL.replace("?", stackTrace);
	}

	private static SearchRestClient _getSearchClient() {

		if(SEARCH_CLIENT == null) {
			_initSearchClient();
		}

		return SEARCH_CLIENT;
	}

	private static void _initSearchClient() {

		try {
			JiraRestClientFactory restClientFactory =
				new AsynchronousJiraRestClientFactory();

			JiraRestClient restClient = restClientFactory.create(
				new URI("https://issues.liferay.com"),
				new AnonymousAuthenticationHandler());

			SEARCH_CLIENT = restClient.getSearchClient();
		}
		catch (URISyntaxException e) {
			SEARCH_CLIENT = null;
		}
	}
}

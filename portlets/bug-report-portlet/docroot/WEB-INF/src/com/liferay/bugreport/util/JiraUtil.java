package com.liferay.bugreport.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;


public class JiraUtil {

	private static final Set<String> FIELDS_TO_RETRIEVE = new HashSet<String>(
		Arrays.asList("created", "description", "issuetype", "key", "project",
		"status", "summary", "updated"));


	private static final String JQL =
					"project=\"PUBLIC - Liferay Portal Community Edition\"";
	private static SearchRestClient SEARCH_CLIENT;
	private static JiraRestClient restClient;
	private static JiraRestClientFactory restClientFactory;

	static {
		System.setProperty("jsse.enableSNIExtension", "false");
		_initSearchClient();
	}

	public static List<Issue> getIssues(String stackTrace){

		List<Issue> issues = new ArrayList<Issue>();

        String query = _createQuery(stackTrace);
		SearchResult results = _getSearchClient().searchJql(
        	query,5,0,FIELDS_TO_RETRIEVE).claim();

        for (final Issue issue : results.getIssues()) {
        	issues.add(issue);
        }

        return issues;
	}

	private static String _createQuery(String stackTrace) {

		String[] lines = stackTrace.split("(\\n|\\t)");

		StringBuilder jql = new StringBuilder(JQL);
		StringBuilder nextAnd = new StringBuilder();


		for (String line : lines) {

			String trim = line.trim();
			if(!trim.isEmpty()) {
				nextAnd.append(" and text~\"");
				nextAnd.append("\\\"");
				nextAnd.append(trim);
				nextAnd.append("\\\"");
				nextAnd.append("\"");

				if(jql.length() + nextAnd.length() <= 2001) {
					jql.append(nextAnd);
				}else {
					nextAnd.delete(0, nextAnd.length()-1);
					break;
				}
			}
		}

		return jql.toString();
	}

	private static SearchRestClient _getSearchClient() {

		return SEARCH_CLIENT;
	}

	private static void _initSearchClient() {

		try {
			restClientFactory =
				new AsynchronousJiraRestClientFactory();

			restClient = restClientFactory.create(
				new URI("https://issues.liferay.com/"),
				new AnonymousAuthenticationHandler());

			SEARCH_CLIENT = restClient.getSearchClient();
		}
		catch (URISyntaxException e) {
			SEARCH_CLIENT = null;
		}
	}
}

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

package com.liferay.bugreport.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import org.apache.solr.common.SolrDocument;
public class BugEntryUtil {

	public static Date getBugEntryDate(SolrDocument bugEntry) {
		ArrayList<Object> bugEntryDates = (ArrayList<Object>)bugEntry.get(
			"exceptionDateTime");

		Date bugEntryDate = null;

		try {
			bugEntryDate = FORMAT.parse(
				String.valueOf(bugEntryDates.get(bugEntryDates.size() - 1)));
		}
		catch (ParseException pe) {
			_log.error("Unable to parse date", pe);
		}

		return bugEntryDate;
	}

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(
		"E MMM dd hh:mm:ss Z yyyy");

	private static Log _log = LogFactoryUtil.getLog(BugEntryUtil.class);

}
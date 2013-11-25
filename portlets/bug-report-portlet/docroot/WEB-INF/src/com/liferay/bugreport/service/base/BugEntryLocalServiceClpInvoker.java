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

package com.liferay.bugreport.service.base;

import com.liferay.bugreport.service.BugEntryLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class BugEntryLocalServiceClpInvoker {
	public BugEntryLocalServiceClpInvoker() {
		_methodName14 = "getBeanIdentifier";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "setBeanIdentifier";

		_methodParameterTypes15 = new String[] { "java.lang.String" };

		_methodName18 = "countAllBugEntries";

		_methodParameterTypes18 = new String[] {  };

		_methodName19 = "countBugEntriesLast7days";

		_methodParameterTypes19 = new String[] {  };

		_methodName20 = "countBugEntriesLast24hours";

		_methodParameterTypes20 = new String[] {  };

		_methodName21 = "countPortletsWithBugs";

		_methodParameterTypes21 = new String[] {  };

		_methodName22 = "countRecurrentBugEntries";

		_methodParameterTypes22 = new String[] {  };

		_methodName23 = "countSingleBugEntries";

		_methodParameterTypes23 = new String[] {  };

		_methodName24 = "getAllBugEntries";

		_methodParameterTypes24 = new String[] { "int", "int" };

		_methodName25 = "getBugEntries";

		_methodParameterTypes25 = new String[] {
				"java.util.Date", "java.util.Date", "int", "int"
			};

		_methodName26 = "getBugEntriesLast7days";

		_methodParameterTypes26 = new String[] { "int", "int" };

		_methodName27 = "getBugEntriesLast24hours";

		_methodParameterTypes27 = new String[] { "int", "int" };

		_methodName28 = "getDocument";

		_methodParameterTypes28 = new String[] { "java.lang.String" };

		_methodName29 = "getPortletsWithBugs";

		_methodParameterTypes29 = new String[] {  };

		_methodName30 = "getRecurrentBugEntries";

		_methodParameterTypes30 = new String[] { "int", "int" };

		_methodName31 = "getSingleBugEntries";

		_methodParameterTypes31 = new String[] { "int", "int" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return BugEntryLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			BugEntryLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName18.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes18, parameterTypes)) {
			return BugEntryLocalServiceUtil.countAllBugEntries();
		}

		if (_methodName19.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes19, parameterTypes)) {
			return BugEntryLocalServiceUtil.countBugEntriesLast7days();
		}

		if (_methodName20.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes20, parameterTypes)) {
			return BugEntryLocalServiceUtil.countBugEntriesLast24hours();
		}

		if (_methodName21.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes21, parameterTypes)) {
			return BugEntryLocalServiceUtil.countPortletsWithBugs();
		}

		if (_methodName22.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes22, parameterTypes)) {
			return BugEntryLocalServiceUtil.countRecurrentBugEntries();
		}

		if (_methodName23.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes23, parameterTypes)) {
			return BugEntryLocalServiceUtil.countSingleBugEntries();
		}

		if (_methodName24.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes24, parameterTypes)) {
			return BugEntryLocalServiceUtil.getAllBugEntries(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName25.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes25, parameterTypes)) {
			return BugEntryLocalServiceUtil.getBugEntries((java.util.Date)arguments[0],
				(java.util.Date)arguments[1],
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName26.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes26, parameterTypes)) {
			return BugEntryLocalServiceUtil.getBugEntriesLast7days(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName27.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes27, parameterTypes)) {
			return BugEntryLocalServiceUtil.getBugEntriesLast24hours(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName28.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes28, parameterTypes)) {
			return BugEntryLocalServiceUtil.getDocument((java.lang.String)arguments[0]);
		}

		if (_methodName29.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes29, parameterTypes)) {
			return BugEntryLocalServiceUtil.getPortletsWithBugs();
		}

		if (_methodName30.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes30, parameterTypes)) {
			return BugEntryLocalServiceUtil.getRecurrentBugEntries(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName31.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes31, parameterTypes)) {
			return BugEntryLocalServiceUtil.getSingleBugEntries(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName14;
	private String[] _methodParameterTypes14;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName18;
	private String[] _methodParameterTypes18;
	private String _methodName19;
	private String[] _methodParameterTypes19;
	private String _methodName20;
	private String[] _methodParameterTypes20;
	private String _methodName21;
	private String[] _methodParameterTypes21;
	private String _methodName22;
	private String[] _methodParameterTypes22;
	private String _methodName23;
	private String[] _methodParameterTypes23;
	private String _methodName24;
	private String[] _methodParameterTypes24;
	private String _methodName25;
	private String[] _methodParameterTypes25;
	private String _methodName26;
	private String[] _methodParameterTypes26;
	private String _methodName27;
	private String[] _methodParameterTypes27;
	private String _methodName28;
	private String[] _methodParameterTypes28;
	private String _methodName29;
	private String[] _methodParameterTypes29;
	private String _methodName30;
	private String[] _methodParameterTypes30;
	private String _methodName31;
	private String[] _methodParameterTypes31;
}
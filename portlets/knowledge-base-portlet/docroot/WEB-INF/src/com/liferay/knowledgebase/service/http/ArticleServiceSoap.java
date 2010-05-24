/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.knowledgebase.service.http;

import com.liferay.knowledgebase.service.ArticleServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * <a href="ArticleServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.knowledgebase.service.ArticleServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.knowledgebase.model.ArticleSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.knowledgebase.model.Article}, that is translated to a
 * {@link com.liferay.knowledgebase.model.ArticleSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ArticleServiceHttp
 * @see       com.liferay.knowledgebase.model.ArticleSoap
 * @see       com.liferay.knowledgebase.service.ArticleServiceUtil
 * @generated
 */
public class ArticleServiceSoap {
	public static com.liferay.knowledgebase.model.ArticleSoap addArticle(
		long parentResourcePrimKey, java.lang.String title,
		java.lang.String content, java.lang.String description, int priority,
		java.lang.String dirName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.knowledgebase.model.Article returnValue = ArticleServiceUtil.addArticle(parentResourcePrimKey,
					title, content, description, priority, dirName,
					serviceContext);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addAttachment(long companyId, long groupId,
		long resourcePrimKey, java.lang.String dirName,
		java.lang.String shortFileName, byte[] bytes) throws RemoteException {
		try {
			ArticleServiceUtil.addAttachment(companyId, groupId,
				resourcePrimKey, dirName, shortFileName, bytes);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteArticle(long resourcePrimKey)
		throws RemoteException {
		try {
			ArticleServiceUtil.deleteArticle(resourcePrimKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAttachment(long companyId, long groupId,
		long resourcePrimKey, java.lang.String fileName)
		throws RemoteException {
		try {
			ArticleServiceUtil.deleteAttachment(companyId, groupId,
				resourcePrimKey, fileName);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap getArticle(
		long resourcePrimKey, int version) throws RemoteException {
		try {
			com.liferay.knowledgebase.model.Article returnValue = ArticleServiceUtil.getArticle(resourcePrimKey,
					version);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap[] getArticles(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.knowledgebase.model.Article> returnValue = ArticleServiceUtil.getArticles(resourcePrimKey,
					start, end, orderByComparator);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getArticlesCount(long resourcePrimKey)
		throws RemoteException {
		try {
			int returnValue = ArticleServiceUtil.getArticlesCount(resourcePrimKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap[] getCompanyArticles(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.knowledgebase.model.Article> returnValue = ArticleServiceUtil.getCompanyArticles(companyId,
					start, end, orderByComparator);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanyArticlesCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = ArticleServiceUtil.getCompanyArticlesCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap[] getGroupArticles(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.knowledgebase.model.Article> returnValue = ArticleServiceUtil.getGroupArticles(groupId,
					start, end, orderByComparator);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap[] getGroupArticles(
		long groupId, long parentResourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.knowledgebase.model.Article> returnValue = ArticleServiceUtil.getGroupArticles(groupId,
					parentResourcePrimKey, start, end, orderByComparator);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupArticlesCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = ArticleServiceUtil.getGroupArticlesCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupArticlesCount(long groupId,
		long parentResourcePrimKey) throws RemoteException {
		try {
			int returnValue = ArticleServiceUtil.getGroupArticlesCount(groupId,
					parentResourcePrimKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap getLatestArticle(
		long resourcePrimKey) throws RemoteException {
		try {
			com.liferay.knowledgebase.model.Article returnValue = ArticleServiceUtil.getLatestArticle(resourcePrimKey);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribe(long groupId, long resourcePrimKey)
		throws RemoteException {
		try {
			ArticleServiceUtil.subscribe(groupId, resourcePrimKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribe(long groupId, long resourcePrimKey)
		throws RemoteException {
		try {
			ArticleServiceUtil.unsubscribe(groupId, resourcePrimKey);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.knowledgebase.model.ArticleSoap updateArticle(
		long resourcePrimKey, long parentResourcePrimKey,
		java.lang.String title, java.lang.String content,
		java.lang.String description, int priority, java.lang.String dirName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.knowledgebase.model.Article returnValue = ArticleServiceUtil.updateArticle(resourcePrimKey,
					parentResourcePrimKey, title, content, description,
					priority, dirName, serviceContext);

			return com.liferay.knowledgebase.model.ArticleSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String updateAttachments(long companyId,
		long groupId, long resourcePrimKey, java.lang.String dirName)
		throws RemoteException {
		try {
			java.lang.String returnValue = ArticleServiceUtil.updateAttachments(companyId,
					groupId, resourcePrimKey, dirName);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ArticleServiceSoap.class);
}
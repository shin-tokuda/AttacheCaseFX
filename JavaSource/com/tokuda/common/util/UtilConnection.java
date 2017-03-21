package com.tokuda.common.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tokuda.attachecase.dialog.ExceptionDialog;

/**
 * システム共通で使用するDBコネクションを管理するクラス。
 */
public class UtilConnection {

	/** DB組み込みドライバ名 */
	public static final String DB_DRIVER = "org.h2.Driver";
	/** DB接続情報(ユーザID) */
	public static final String DB_USER_ID = "DBADMIN";
	/** DB接続情報(パスワード) */
	public static final String DB_PASSWORD = "DBADMIN";

	public static Connection getDBConnection(final Connection connection, final String directory) {
		Connection result = connection;

		try {

			if (result == null || result.isClosed()) {
				// DB接続を確立
				Class.forName(DB_DRIVER);
				result = DriverManager.getConnection("jdbc:h2:" + directory + File.separator + "DB;create=true", DB_USER_ID, DB_PASSWORD);
				result.setAutoCommit(true);
				result.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}

		} catch (ClassNotFoundException | SQLException ex) {
			new ExceptionDialog(ex).showAndWait();
		}
		return result;
	}

	public static Connection getDBConnection(final Connection connection, final String driver, final String url, final String userId, final String password) {
		Connection result = connection;

		try {

			if (result == null || result.isClosed()) {
				// DB接続を確立
				Class.forName(driver);
				result = DriverManager.getConnection(url, userId, password);
				result.setAutoCommit(true);
				result.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}

		} catch (ClassNotFoundException | SQLException ex) {
			new ExceptionDialog(ex).showAndWait();
		}
		return result;
	}

	public static void setAutoCommit(final Connection connection, final boolean autoCommit) {

		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException ex) {
			new ExceptionDialog(ex).showAndWait();
		}
	}

	public static void commit(final Connection connection) {

		try {
			connection.commit();
		} catch (SQLException ex) {
			new ExceptionDialog(ex).showAndWait();
		}
	}

	public static void rollback(final Connection connection) {

		try {
			connection.rollback();
		} catch (SQLException ex) {
			new ExceptionDialog(ex).showAndWait();
		}
	}

	public static void close(final Connection connection) {

		try {
			connection.close();
		} catch (SQLException ex) {
			new ExceptionDialog(ex).showAndWait();
		}
	}
}

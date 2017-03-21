package com.tokuda.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * コンポーネントに関するユーティリティを提供するクラス。
 */
public class UtilSQL {

	public static final String SPACE = " ";
	public static final String SPACE4 = "    ";
	public static final String TAB = "\t";
	public static final String COMMA = ",";
	public static final String BETWEEN = "between";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String BR = "\n";
	public static final String S_QUOT = "'";
	public static final String S_PAREN = "(";
	public static final String E_PAREN = ")";

	public static final String EXISTS = "exists";

	public static final String CASE = "case";
	public static final String WHEN = "when";
	public static final String ELSE = "else";
	public static final String END = "end";

	public static final String[] SQL = new String[] { "select", "union", "into", "on", "from", "where", "having", "inner join", "left outer join",
			"right outer join", "full outer join", "group by", "order by", "insert", "into", "values", "update", "set", "delete" };

	/**
	 * コメント行を除去します。
	 *
	 * @param str 処理対象の文字列
	 * @return コメント行を除去した文字列
	 */
	public static String removeComment(String str) {
		StringBuilder builder = new StringBuilder();

		// ｢//｣から始まる行については除外した上で文字列を再生成
		String[] lines = str.split(BR);
		for (String line : lines) {
			if (line.startsWith("//"))
				continue;
			builder.append(line + BR);
		}
		return builder.toString();
	}

	/**
	 * SQLを整形し、インデントを整えます。
	 *
	 * @param sql 整形元となるSQL
	 * @return 整形後のSQL
	 */
	public static String editSqlIndent(String sql) {
		StringBuilder builder = new StringBuilder();

		// --------------------------------------------------------
		// 代入文字列とそれ以外で分割したリストを作成
		// --------------------------------------------------------
		List<String> tempList1 = new ArrayList<String>();

		boolean isPlus = false;
		int offset1 = 0;

		while (offset1 < sql.length()) {

			// 検索開始位置を取得
			int startIndex = offset1;

			if (isPlus) {
				startIndex++;
			}

			// 検索終了位置を取得
			int endIndex = sql.indexOf(S_QUOT, startIndex);

			if (endIndex < 0) {
				endIndex = sql.length();
			} else {

				if (isPlus) {
					endIndex++;
				}
				isPlus = !isPlus;
			}

			// SQLをリストに分割格納
			tempList1.add(sql.substring(offset1, endIndex));

			// オフセットを更新
			offset1 = endIndex;
		}

		// --------------------------------------------------------
		// 代入文字列以外の部分を、整形単位で分割したリストを作成
		// --------------------------------------------------------
		List<String> tempList2 = new ArrayList<String>();

		for (int i = 0; i < tempList1.size(); i++) {
			String str = tempList1.get(i);

			if (!str.startsWith(S_QUOT)) {
				// 代入文字列以外の場合

				// タブを半角スペースに置換
				str = str.replaceAll(TAB, SPACE);
				// 改行を半角スペースに置換
				str = str.replaceAll(BR, SPACE);

				// 「カッコ()」の前後に半角スペースを挿入
				str = str.replaceAll("\\(", SPACE + S_PAREN + SPACE);
				str = str.replaceAll("\\)", SPACE + E_PAREN + SPACE);
				// 「カンマ(,)」の前後に半角スペースを挿入
				str = str.replaceAll(COMMA, SPACE + COMMA + SPACE);

				// 2つ以上連続する半角スペースを除去
				str = str.replaceAll(" {2,}", SPACE);
				// 文字列前後の半角スペースを除去
				str = str.trim();

				// ----------------------------------------------
				// 半角スペースで分割してリストに格納
				// ----------------------------------------------
				int offset2 = 0;

				while (offset2 < str.length()) {
					String pattern = null;

					// パターンマッチング
					for (String str2 : SQL) {

						if ((str.substring(offset2).toLowerCase()).startsWith(str2.toLowerCase() + SPACE)) {
							pattern = str2;
							break;
						}
					}

					// 文節抽出
					int endIndex = 0;
					if (pattern != null) {
						endIndex = offset2 + pattern.length();
					} else {
						endIndex = offset2
								+ ((str.substring(offset2)).indexOf(SPACE) > 0 ? (str.substring(offset2)).indexOf(SPACE) : (str.substring(offset2)).length());
					}

					// 文字列配列へ格納
					tempList2.add(str.substring(offset2, endIndex));

					// オフセットの再設定
					offset2 = endIndex + 1;
				}

			} else {
				// 代入文字列はそのまま
				tempList2.add(str);
			}
		}

		// ----------------------------------------------
		// 行ごとにリストを分割して格納
		// ----------------------------------------------
		List<List<String>> sqlList = new ArrayList<List<String>>();
		List<String> line = new ArrayList<String>();

		boolean isCase = false;
		boolean isBetween = false;
		boolean isFunc = false;

		for (int i = 0; i < tempList2.size(); i++) {
			String str1 = tempList2.get(i);

			if (str1 == null) {
				continue;
			}

			boolean bfBr = false;
			boolean afBr = false;

			// CASE文の開始を判定
			if ((str1.toLowerCase()).equals(CASE.toLowerCase())) {
				isCase = true;
				bfBr = true;
				afBr = true;
			}

			if (!isCase) {
				// CASE文以外の箇所

				if (!isFunc) {

					// パターンにマッチするか判定
					boolean isMatch = false;

					for (String str2 : SQL) {

						if ((str1.toLowerCase()).equals(str2.toLowerCase())) {
							isMatch = true;
							break;
						}
					}

					if (!isMatch) {
						// パターンにマッチしなかった場合

						if (str1.equals(COMMA)) {
							// カンマの場合
							bfBr = false;
							afBr = true;
						} else if ((str1.toLowerCase()).equals(BETWEEN.toLowerCase())) {
							// 「between」の場合
							isBetween = true;
						} else if ((str1.toLowerCase()).equals(AND.toLowerCase())) {
							// 「and」の場合

							if (!isBetween) {
								bfBr = false;
								afBr = true;
							} else {
								isBetween = false;
							}

						} else if (!afBr && (str1.toLowerCase()).equals(OR.toLowerCase())) {
							// 「or」の場合
							bfBr = false;
							afBr = true;
						}

					} else {
						// パターンにマッチした場合
						bfBr = true;
						afBr = true;
					}
				}

				if (str1.equals(S_PAREN)) {
					// カッコ(始まり)の処理

					int cnt = 1;
					for (int j = i + 1; j < tempList2.size(); j++) {
						String str2 = tempList2.get(j);

						// 次のカッコ(終わり)の位置までの文字列にカッコ(終わり)が含まれる場合
						if (str2.equals(E_PAREN)) {
							cnt--;
							if (cnt == 0) {
								isFunc = true;
								break;
							}
						}

						// 次のカッコ(終わり)の位置までの文字列に、パターンにマッチする文字列が含まれる場合
						boolean isMatch = false;
						for (String str3 : SQL) {

							if ((str2.toLowerCase()).equals(str3.toLowerCase())) {
								isMatch = true;
								isFunc = false;
								break;
							}
						}

						if (isMatch) {
							break;
						}

						// 次のカッコ(終わり)の位置までの文字列に「and」「or」が含まれる場合
						if ((str2.toLowerCase()).equals(AND.toLowerCase()) || (str2.toLowerCase()).equals(OR.toLowerCase())) {
							isFunc = false;
							break;
						}

						// 次のカッコ(終わり)の位置までの文字列にカッコ(始まり)が含まれる場合
						if (str2.equals(S_PAREN)) {
							cnt++;
						}
					}

					if (isFunc) {
						// ファンクションの一部と判定された場合
						bfBr = false;
						afBr = false;
					} else {
						// ファンクションの一部ではないと判定された場合
						bfBr = false;
						afBr = true;
					}

				} else if (str1.equals(E_PAREN)) {
					// カッコ(終わり)の処理

					int cnt = 0;
					for (int j = 0; j < line.size(); j++) {
						String str2 = line.get(j);

						if (str2.equals(S_PAREN)) {
							cnt++;
						}
						if (str2.equals(E_PAREN)) {
							cnt--;
						}
					}

					if (isFunc) {
						// ファンクションの一部と判定された場合

						// ファンクション終了の判定
						if (cnt <= 1) {
							isFunc = false;
						}

						// CASE文終了の判定
						boolean isCaseEnd = false;
						for (int j = 0; j < line.size(); j++) {
							String str2 = line.get(j);

							if ((str2.toLowerCase()).equals(END.toLowerCase())) {
								isCaseEnd = true;
								break;
							}
						}

						if (isCaseEnd) {
							// CASE文が終了した場合
							bfBr = true;
							afBr = false;
						} else {
							// CASE文ではなかった場合
							bfBr = false;
							afBr = false;
						}

					} else {
						// ファンクションの一部ではないと判定された場合
						bfBr = true;
						afBr = false;
					}
				}

			} else {
				// CASE文内

				if ((str1.toLowerCase()).equals(WHEN.toLowerCase()) || (str1.toLowerCase()).equals(ELSE.toLowerCase())
						|| (str1.toLowerCase()).equals(END.toLowerCase())) {
					bfBr = true;
					afBr = false;

					// CASE文終了の判定
					if ((str1.toLowerCase()).equals(END.toLowerCase())) {
						isCase = false;
					}
				}
			}

			// 改行処理
			if (bfBr && !line.isEmpty()) {
				sqlList.add(line);
				line = new ArrayList<String>();
			}

			// SQL連結
			line.add(str1);

			// 改行処理
			if (afBr && !line.isEmpty()) {
				sqlList.add(line);
				line = new ArrayList<String>();
			}
		}

		if (!line.isEmpty()) {
			sqlList.add(line);
		}

		// ----------------------------------------------
		// SQL整形
		// ----------------------------------------------
		List<Integer> indentList = new ArrayList<Integer>();
		int indent1 = 0;

		for (int i = 0; i < sqlList.size(); i++) {

			if (i != 0) {
				builder.append(BR);
			}

			List<String> tempList = sqlList.get(i);

			for (int j = 0; j < tempList.size(); j++) {
				String temp = tempList.get(j);

				boolean isIndent2 = true;
				boolean isIndent3 = false;

				if (j != 0) {
					// 行頭以外の処理

					isIndent2 = false;
					if (!temp.equals(COMMA)) {
						builder.append(SPACE);
					}

				} else {
					// 行頭の処理

					for (String str : SQL) {

						if ((temp.toLowerCase()).equals(str.toLowerCase())) {
							isIndent2 = false;
							break;
						}
					}

					if ((builder.substring(0, builder.length() >= BR.length() ? builder.length() - BR.length() : 0)).endsWith(S_PAREN)) {

						if ((SQL[0].toLowerCase()).equals(temp.toLowerCase())) {
							indent1 += 2;
							indentList.add(new Integer(2));
						} else {
							indent1++;
							indentList.add(new Integer(1));
						}

					} else if (temp.equals(E_PAREN)) {

						if (!indentList.isEmpty()) {
							indent1 -= (indentList.get(indentList.size() - 1)).intValue();

							if (indent1 < 0) {
								indent1 = 0;
							}
							indentList.remove(indentList.size() - 1);
						}

					} else if ((temp.toLowerCase()).equals(WHEN.toLowerCase()) || (temp.toLowerCase()).equals(ELSE.toLowerCase())) {
						isIndent3 = true;
					}

					// インデント付加処理
					for (int k = 0; k < indent1; k++) {
						builder.append(SPACE4);
					}

					if (isIndent2) {
						builder.append(SPACE4);
					}

					if (isIndent3) {
						builder.append(SPACE4);
					}
				}
				builder.append(temp);
			}
		}

		// ----------------------------------------------
		// 整形結果のSQLを返却
		// ----------------------------------------------
		return builder.toString();
	}
}

package com.volleyball.file;

import java.io.InputStream;
import java.util.List;

import com.volleyball.match.LeagueMatch;

public interface MatchParser {
	
	/*
	 * 解析输入流，得到LeagueMatch对象集合。
	 * */
	public List<LeagueMatch> parse(InputStream is) throws Exception;

	/*
	 * 序列化LeagueMatch集合，得到XML形式的字符串。
	 */
	public String serialize(List<LeagueMatch> leagueMatches) throws Exception;
}

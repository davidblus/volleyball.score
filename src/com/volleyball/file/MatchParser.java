package com.volleyball.file;

import java.io.InputStream;
import java.util.List;

import com.volleyball.match.LeagueMatch;

public interface MatchParser {
	
	/*
	 * �������������õ�LeagueMatch���󼯺ϡ�
	 * */
	public List<LeagueMatch> parse(InputStream is) throws Exception;

	/*
	 * ���л�LeagueMatch���ϣ��õ�XML��ʽ���ַ�����
	 */
	public String serialize(List<LeagueMatch> leagueMatches) throws Exception;
}

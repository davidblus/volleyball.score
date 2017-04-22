package com.volleyball.file;

import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Pause;
import com.volleyball.match.Point;
import com.volleyball.match.Set;
import com.volleyball.match.Substitution;
import com.volleyball.match.Team;
import com.volleyball.match.TeamMember;

public class DomLeagueMatchParser implements MatchParser {

	public DomLeagueMatchParser() {
	}
	
	public List<Team> parseTeamsNode(Node teamsNode) {

		List<Team> teams = new ArrayList<Team>();
		NodeList teamItems = teamsNode.getChildNodes();
		for (int i = 0; i < teamItems.getLength(); i++) {
			Node itemTeam = teamItems.item(i);
			if (itemTeam.getNodeName().equals("team")) {//<team>
				Team team = new Team();
				team.setNormalTeamMembers(new ArrayList<TeamMember>());
				team.setLiberoTeamMembers(new ArrayList<TeamMember>());
				System.out.println("should be <team>:" + itemTeam.getNodeName());
				NamedNodeMap attributes = itemTeam.getAttributes();
				team.setId(Integer.parseInt(attributes.getNamedItem("id").getNodeValue()));
				NodeList properties = itemTeam.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("name")) {//<name>
						team.setName(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("captainName")) {//<captainName>
						team.setCaptainName(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("coachName")) {//<coachName>
						team.setCoachName(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("normalTeamMember")) {//<normalTeamMember>
						// 继续解析
						TeamMember teamMember = new TeamMember();
						NamedNodeMap teamMemberAttributes = property.getAttributes();
						teamMember.setNumber(Integer.parseInt(teamMemberAttributes.getNamedItem("number").getNodeValue()));
						teamMember.setName(property.getFirstChild().getNodeValue());
						team.addNormalTeamMembers(teamMember);
					}
					else if (nodeName.equals("liberoTeamMember")) {//<liberoTeamMember>
						// 继续解析
						TeamMember teamMember = new TeamMember();
						NamedNodeMap teamMemberAttributes = property.getAttributes();
						teamMember.setNumber(Integer.parseInt(teamMemberAttributes.getNamedItem("number").getNodeValue()));
						teamMember.setName(property.getFirstChild().getNodeValue());
						team.addLiberoTeamMembers(teamMember);
					}
				}
				teams.add(team);
			}
		}
		return teams;
	}
	
	public List<Point> parsePointRecordsNode(Node pointRecordsNode, Match match) {

		List<Point> pointRecords = new ArrayList<Point>();
		NodeList pointItems = pointRecordsNode.getChildNodes();
		for (int i = 0; i < pointItems.getLength(); i++) {
			Node itemPoint = pointItems.item(i);
			if (itemPoint.getNodeName().equals("point")) {//<point>
				Point point = new Point();
				point.addTeamAPosition(null);
				point.addTeamBPosition(null);
				System.out.println("should be <point>:" + itemPoint.getNodeName());
				NodeList properties = itemPoint.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("teamName")) {//<teamName>
						point.setTeam(match.getTeamByName(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("teamMemberNum")) {//<teamMemberNum>
						point.setTeamMember(point.getTeam().getTeamMemberByNum(Integer.parseInt(property.getFirstChild().getNodeValue())));
					}
					else if (nodeName.equals("teamAPosition")) {//<teamAPosition>
						// 未考虑position属性，只按照读取顺序排列
						point.addTeamAPosition(match.getTeamA().getTeamMemberByNum(Integer.parseInt(property.getFirstChild().getNodeValue())));
					}
					else if (nodeName.equals("teamBPosition")) {//<teamBPosition>
						// 未考虑position属性，只按照读取顺序排列
						point.addTeamBPosition(match.getTeamB().getTeamMemberByNum(Integer.parseInt(property.getFirstChild().getNodeValue())));
					}
					else if (nodeName.equals("resultStr")) {//<resultStr>
						point.setResultStr(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("serverScore")) {//<serverScore>
						point.setServerScore(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("clientScore")) {//<clientScore>
						point.setClientScore(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
				}
				pointRecords.add(point);
			}
		}
		return pointRecords;
	}
	
	public List<Pause> parsePauseNode(String aOrB, Node pauseNode, Match match) {
		// 目前不需要判断aOrB是什么，而且不需要match参数

		List<Pause> pauses = new ArrayList<Pause>();
		NodeList pauseItems = pauseNode.getChildNodes();
		for (int i = 0; i < pauseItems.getLength(); i++) {
			Node itemPause = pauseItems.item(i);
			if (itemPause.getNodeName().equals("pause")) {//<pause>
				Pause pause = new Pause();
				System.out.println("should be <pause>:" + itemPause.getNodeName());
				NodeList properties = itemPause.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("initiatorScore")) {//<initiatorScore>
						pause.setInitiatorScore(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("receiverScore")) {//<receiverScore>
						pause.setReceiverScore(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
				}
				pauses.add(pause);
			}
		}
		return pauses;
	}
	
	public List<Pause> parsePauseTeamANode(Node pauseTeamANode, Match match) {
		return this.parsePauseNode("A", pauseTeamANode, match);
	}
	
	public List<Pause> parsePauseTeamBNode(Node pauseTeamBNode, Match match) {
		return this.parsePauseNode("B", pauseTeamBNode, match);
	}
	
	public List<Substitution> parseSubstitutionNode(String aOrB, Node substitutionNode, Team team) {
		// 目前不需要判断aOrB是什么

		List<Substitution> substitutions = new ArrayList<Substitution>();
		NodeList substitutionItems = substitutionNode.getChildNodes();
		for (int i = 0; i < substitutionItems.getLength(); i++) {
			Node itemSubstitution = substitutionItems.item(i);
			if (itemSubstitution.getNodeName().equals("substitution")) {//<substitution>
				Substitution substitution = new Substitution();
				System.out.println("should be <substitution>:" + itemSubstitution.getNodeName());
				NodeList properties = itemSubstitution.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("initiatorScore")) {//<initiatorScore>
						substitution.setInitiatorScore(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("receiverScore")) {//<receiverScore>
						substitution.setReceiverScore(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("downTeamMemberList")) {//<downTeamMemberList>
						NamedNodeMap teamMemberAttributes = property.getAttributes();
						substitution.addDownTeamMemberList(team.getTeamMemberByNum(Integer.parseInt(teamMemberAttributes.getNamedItem("number").getNodeValue())));
					}
					else if (nodeName.equals("upTeamMemberList")) {//<upTeamMemberList>
						NamedNodeMap teamMemberAttributes = property.getAttributes();
						substitution.addUpTeamMemberList(team.getTeamMemberByNum(Integer.parseInt(teamMemberAttributes.getNamedItem("number").getNodeValue())));
					}
				}
				substitutions.add(substitution);
			}
		}
		return substitutions;
	}
	
	public List<Substitution> parseSubstitutionTeamANode(Node substitutionTeamANode, Match match) {
		return this.parseSubstitutionNode("A", substitutionTeamANode, match.getTeamA());
	}
	
	public List<Substitution> parseSubstitutionTeamBNode(Node substitutionTeamBNode, Match match) {
		return this.parseSubstitutionNode("B", substitutionTeamBNode, match.getTeamB());
	}
	
	public List<Set> parseSetsNode(Node setsNode, Match match) {

		List<Set> sets = new ArrayList<Set>();
		NodeList setItems = setsNode.getChildNodes();
		for (int i = 0; i < setItems.getLength(); i++) {
			Node itemSet = setItems.item(i);
			if (itemSet.getNodeName().equals("set")) {//<set>
				Set set = new Set();
				set.addTeamAInitialPosition(null);
				set.addTeamBInitialPosition(null);
				System.out.println("should be <set>:" + itemSet.getNodeName());
				NamedNodeMap attributes = itemSet.getAttributes();
				set.setNum(Integer.parseInt(attributes.getNamedItem("num").getNodeValue()));
				NodeList properties = itemSet.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("firstTeamName")) {//<firstTeamName>
						set.setFirstTeam(match.getTeamByName(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("teamAInitialPosition")) {//<teamAInitialPosition>
						// 未考虑position属性，只按照读取顺序排列
						set.addTeamAInitialPosition(match.getTeamA().getTeamMemberByNum(Integer.parseInt(property.getFirstChild().getNodeValue())));
					}
					else if (nodeName.equals("teamBInitialPosition")) {//<teamBInitialPosition>
						// 未考虑position属性，只按照读取顺序排列
						set.addTeamBInitialPosition(match.getTeamB().getTeamMemberByNum(Integer.parseInt(property.getFirstChild().getNodeValue())));
					}
					else if (nodeName.equals("setStartTime")) {//<setStartTime>
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
						try {
							set.setSetStartTime(sdf.parse(property.getFirstChild().getNodeValue()));
						} catch (DOMException e) {
							set.setSetStartTime(null);
							e.printStackTrace();
						} catch (ParseException e) {
							set.setSetStartTime(null);
							e.printStackTrace();
						}
					}
					else if (nodeName.equals("setEndTime")) {//<setEndTime>
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
						try {
							set.setSetEndTime(sdf.parse(property.getFirstChild().getNodeValue()));
						} catch (DOMException e) {
							set.setSetEndTime(null);
							e.printStackTrace();
						} catch (ParseException e) {
							set.setSetEndTime(null);
							e.printStackTrace();
						}
					}
					else if (nodeName.equals("pointRecords")) {//<pointRecords>
						// 继续解析
						set.setPointRecords(this.parsePointRecordsNode(property, match));
					}
					else if (nodeName.equals("pauseTeamA")) {//<pauseTeamA>
						// 解析A队暂停
						set.setPauseTeamA(this.parsePauseTeamANode(property, match));
					}
					else if (nodeName.equals("pauseTeamB")) {//<pauseTeamB>
						// 解析B队暂停
						set.setPauseTeamB(this.parsePauseTeamBNode(property, match));
					}
					else if (nodeName.equals("substitutionTeamA")) {//<substitutionTeamA>
						// 解析A队换人
						set.setSubstitutionTeamA(this.parseSubstitutionTeamANode(property, match));
					}
					else if (nodeName.equals("substitutionTeamB")) {//<substitutionTeamB>
						// 解析B队换人
						set.setSubstitutionTeamB(this.parseSubstitutionTeamBNode(property, match));
					}
					else if (nodeName.equals("winTeamName")) {//<winTeamName>
						set.setWinTeam(match.getTeamByName(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("finalScoreTeamA")) {//<finalScoreTeamA>
						set.setFinalScoreTeamA(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("finalScoreTeamB")) {//<finalScoreTeamB>
						set.setFinalScoreTeamB(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("setDuration")) {//<setDuration>
						set.setSetDuration(property.getFirstChild().getNodeValue());
					}
				}
				sets.add(set);
			}
		}
		return sets;
	}
	
	public List<Match> parseMatchesNode(Node matchesNode, LeagueMatch leagueMatch) {

		List<Match> matches = new ArrayList<Match>();
		NodeList matchItems = matchesNode.getChildNodes();
		for (int i = 0; i < matchItems.getLength(); i++) {
			Node itemMatch = matchItems.item(i);
			if (itemMatch.getNodeName().equals("match")) {//<match>
				Match match = new Match();
				System.out.println("should be <match>:" + itemMatch.getNodeName());
				NamedNodeMap attributes = itemMatch.getAttributes();
				match.setId(Integer.parseInt(attributes.getNamedItem("id").getNodeValue()));
				NodeList properties = itemMatch.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("name")) {//<name>
						match.setName(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("teamAName")) {//<teamAName>
						match.setTeamA(leagueMatch.getTeamByName(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("teamBName")) {//<teamBName>
						match.setTeamB(leagueMatch.getTeamByName(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("city")) {//<city>
						match.setCity(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("stadium")) {//<stadium>
						match.setStadium(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("site")) {//<site>
						match.setSite(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("stage")) {//<stage>
						match.setStage(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("category")) {//<category>
						match.setCategory(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("matchStartDate")) {//<matchStartDate>
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
						try {
							match.setMatchStartDate(sdf.parse(property.getFirstChild().getNodeValue()));
						} catch (DOMException e) {
							match.setMatchStartDate(null);
							e.printStackTrace();
						} catch (ParseException e) {
							match.setMatchStartDate(null);
							e.printStackTrace();
						}
					}
					else if (nodeName.equals("matchEndDate")) {//<matchEndDate>
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
						try {
							match.setMatchEndDate(sdf.parse(property.getFirstChild().getNodeValue()));
						} catch (DOMException e) {
							match.setMatchEndDate(null);
							e.printStackTrace();
						} catch (ParseException e) {
							match.setMatchEndDate(null);
							e.printStackTrace();
						}
					}
					else if (nodeName.equals("sets")) {//<sets>
						// 继续解析
						match.setSets(this.parseSetsNode(property, match));
					}
					else if (nodeName.equals("pauseTimeA")) {//<pauseTimeA>
						match.setPauseTimeA(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("pauseTimeB")) {//<pauseTimeB>
						match.setPauseTimeB(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("substitutionTimeA")) {//<substitutionTimeA>
						match.setSubstitutionTimeA(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("substitutionTimeB")) {//<substitutionTimeB>
						match.setSubstitutionTimeB(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("winTeamName")) {//<winTeamName>
						match.setWinTeam(leagueMatch.getTeamByName(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("totalScoreTeamA")) {//<totalScoreTeamA>
						match.setTotalScoreTeamA(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("totalScoreTeamB")) {//<totalScoreTeamB>
						match.setTotalScoreTeamB(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("setsDuration")) {//<setsDuration>
						match.setSetsDuration(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
					else if (nodeName.equals("failedSetTime")) {//<failedSetTime>
						match.setFailedSetTime(Integer.parseInt(property.getFirstChild().getNodeValue()));
					}
				}
				matches.add(match);
			}
		}
		return matches;
	}
	
	@Override
	public List<LeagueMatch> parse(InputStream is) throws Exception {
		List<LeagueMatch> leagueMatches = new ArrayList<LeagueMatch>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
			Document doc = builder.parse(is);
			Element rootElement = doc.getDocumentElement();//<leagueMatches>
			NodeList items = rootElement.getElementsByTagName("leagueMatch");//<leagueMatch>
			for (int i = 0; i < items.getLength(); i++) {
				LeagueMatch leagueMatch = new LeagueMatch();
				Node item = items.item(i);
				NamedNodeMap attributes = item.getAttributes();
				leagueMatch.setId(Integer.parseInt(attributes.getNamedItem("id").getNodeValue()));
				NodeList properties = item.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String nodeName = property.getNodeName();
					if (nodeName.equals("name")) {//<name>
						leagueMatch.setName(property.getFirstChild().getNodeValue());
					}
					else if (nodeName.equals("teams")) {//<teams>
						leagueMatch.setTeams(this.parseTeamsNode(property));
					}
					else if (nodeName.equals("matches")) {//<matches>
						// 解析matches
						leagueMatch.setMatches(this.parseMatchesNode(property, leagueMatch));
					}
				}
				leagueMatches.add(leagueMatch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("parse result:" + leagueMatches.toString());
		return leagueMatches;
	}

	public Element serializeTeams(LeagueMatch leagueMatch, Document doc) {
		Element teamsElement = doc.createElement("teams");
		if (leagueMatch.getTeams() != null) {
			for (Team team:leagueMatch.getTeams()) {
				Element teamElement = doc.createElement("team");
				teamElement.setAttribute("id", team.getId() + "");
				
				Element teamNameElement = doc.createElement("name");
				teamNameElement.setTextContent(team.getName());
				teamElement.appendChild(teamNameElement);
				Element captainNameElement = doc.createElement("captainName");
				captainNameElement.setTextContent(team.getCaptainName());
				teamElement.appendChild(captainNameElement);
				Element coachNameElement = doc.createElement("coachName");
				coachNameElement.setTextContent(team.getCoachName());
				teamElement.appendChild(coachNameElement);
				
				for (TeamMember teamMember:team.getNormalTeamMembers()) {
					Element normalTeamMemberElement = doc.createElement("normalTeamMember");
					normalTeamMemberElement.setAttribute("number", teamMember.getNumber() + "");
					normalTeamMemberElement.setTextContent(teamMember.getName());
					teamElement.appendChild(normalTeamMemberElement);
				}
				for (TeamMember teamMember:team.getLiberoTeamMembers()) {
					Element liberoTeamMemberElement = doc.createElement("liberoTeamMember");
					liberoTeamMemberElement.setAttribute("number", teamMember.getNumber() + "");
					liberoTeamMemberElement.setTextContent(teamMember.getName());
					teamElement.appendChild(liberoTeamMemberElement);
				}
				
				teamsElement.appendChild(teamElement);
			}
		}
		return teamsElement;
	}

	public Element serializePointRecords(Set set, Document doc) {
		// 解析这局比赛比分记录列表
		Element pointRecordsElement = doc.createElement("pointRecords");
		if (set.getPointRecords() != null) {
			for (Point point:set.getPointRecords()) {
				Element pointElement = doc.createElement("point");
				
				Element teamNameElement = doc.createElement("teamName");
				teamNameElement.setTextContent(point.getTeam().getName());
				pointElement.appendChild(teamNameElement);
				
				Element teamMemberNumElement = doc.createElement("teamMemberNum");
				teamMemberNumElement.setTextContent("" + point.getTeamMember().getNumber());
				pointElement.appendChild(teamMemberNumElement);
				
				for (int i = 1; i < point.getTeamAPosition().size(); ++i) {
					Element teamAPositionElement = doc.createElement("teamAPosition");
					teamAPositionElement.setAttribute("position", "" + i);
					teamAPositionElement.setTextContent("" + point.getTeamAPosition().get(i).getNumber());
					pointElement.appendChild(teamAPositionElement);
				}
				for (int i = 1; i < point.getTeamBPosition().size(); ++i) {
					Element teamBPositionElement = doc.createElement("teamBPosition");
					teamBPositionElement.setAttribute("position", "" + i);
					teamBPositionElement.setTextContent("" + point.getTeamBPosition().get(i).getNumber());
					pointElement.appendChild(teamBPositionElement);
				}
				
				Element resultStrElement = doc.createElement("resultStr");
				resultStrElement.setTextContent(point.getResultStr());
				pointElement.appendChild(resultStrElement);
				
				Element serverScoreElement = doc.createElement("serverScore");
				serverScoreElement.setTextContent("" + point.getServerScore());
				pointElement.appendChild(serverScoreElement);
				Element clientScoreElement = doc.createElement("clientScore");
				clientScoreElement.setTextContent("" + point.getClientScore());
				pointElement.appendChild(clientScoreElement);
				
				pointRecordsElement.appendChild(pointElement);
			}
		}
		return pointRecordsElement;
	}
	
	public Element serializePauseTeamA(Set set, Document doc) {
		Element pauseTeamAElement = doc.createElement("pauseTeamA");
		if (set.getPauseTeamA() != null) {
			for (Pause pause:set.getPauseTeamA()) {
				Element pauseElement = doc.createElement("pause");
				
				Element initiatorScoreElement = doc.createElement("initiatorScore");
				initiatorScoreElement.setTextContent("" + pause.getInitiatorScore());
				pauseElement.appendChild(initiatorScoreElement);
				Element receiverScoreElement = doc.createElement("receiverScore");
				receiverScoreElement.setTextContent("" + pause.getReceiverScore());
				pauseElement.appendChild(receiverScoreElement);
				
				pauseTeamAElement.appendChild(pauseElement);
			}
		}
		return pauseTeamAElement;
	}
	
	public Element serializePauseTeamB(Set set, Document doc) {
		Element pauseTeamBElement = doc.createElement("pauseTeamB");
		if (set.getPauseTeamB() != null) {
			for (Pause pause:set.getPauseTeamB()) {
				Element pauseElement = doc.createElement("pause");
				
				Element initiatorScoreElement = doc.createElement("initiatorScore");
				initiatorScoreElement.setTextContent("" + pause.getInitiatorScore());
				pauseElement.appendChild(initiatorScoreElement);
				Element receiverScoreElement = doc.createElement("receiverScore");
				receiverScoreElement.setTextContent("" + pause.getReceiverScore());
				pauseElement.appendChild(receiverScoreElement);
				
				pauseTeamBElement.appendChild(pauseElement);
			}
		}
		return pauseTeamBElement;
	}

	public Element serializeSubstitution(Substitution substitution, Document doc) {
		Element substitutionElement = doc.createElement("substitution");
		
		Element initiatorScoreElement = doc.createElement("initiatorScore");
		initiatorScoreElement.setTextContent("" + substitution.getInitiatorScore());
		substitutionElement.appendChild(initiatorScoreElement);
		Element receiverScoreElement = doc.createElement("receiverScore");
		receiverScoreElement.setTextContent("" + substitution.getReceiverScore());
		substitutionElement.appendChild(receiverScoreElement);
		
		for(TeamMember downTeamMemberList:substitution.getDownTeamMemberList()) {
			Element downTeamMemberListElement = doc.createElement("downTeamMemberList");
			downTeamMemberListElement.setAttribute("number", "" + downTeamMemberList.getNumber());
			downTeamMemberListElement.setTextContent(downTeamMemberList.getName());
			substitutionElement.appendChild(downTeamMemberListElement);
		}		
		for(TeamMember upTeamMemberList:substitution.getUpTeamMemberList()) {
			Element upTeamMemberListElement = doc.createElement("upTeamMemberList");
			upTeamMemberListElement.setAttribute("number", "" + upTeamMemberList.getNumber());
			upTeamMemberListElement.setTextContent(upTeamMemberList.getName());
			substitutionElement.appendChild(upTeamMemberListElement);
		}
		
		return substitutionElement;
	}
	
	public Element serializeSubstitutionA(Set set, Document doc) {
		Element substitutionTeamAElement = doc.createElement("substitutionTeamA");
		if (set.getSubstitutionTeamA() != null) {
			for (Substitution substitution:set.getSubstitutionTeamA()) {
				Element substitutionElement = this.serializeSubstitution(substitution, doc);
				substitutionTeamAElement.appendChild(substitutionElement);
			}
		}
		return substitutionTeamAElement;
	}
	
	public Element serializeSubstitutionB(Set set, Document doc) {
		Element substitutionTeamBElement = doc.createElement("substitutionTeamB");
		if (set.getSubstitutionTeamB() != null) {
			for (Substitution substitution:set.getSubstitutionTeamB()) {
				Element substitutionElement = this.serializeSubstitution(substitution, doc);
				substitutionTeamBElement.appendChild(substitutionElement);
			}
		}
		return substitutionTeamBElement;
	}
	
	public Element serializeSets(Match match, Document doc) {
		// 
		Element setsElement = doc.createElement("sets");
		if (match.getSets() != null) {
			for (Set set:match.getSets()) {
				Element setElement = doc.createElement("set");
				setElement.setAttribute("num", "" + set.getNum());
				
				Element firstTeamNameElement = doc.createElement("firstTeamName");
				firstTeamNameElement.setTextContent(set.getFirstTeam().getName());
				setElement.appendChild(firstTeamNameElement);
				
				for (int i = 1; i < set.getTeamAInitialPosition().size(); ++i) {
					Element teamAInitialPositionElement = doc.createElement("teamAInitialPosition");
					teamAInitialPositionElement.setAttribute("position", "" + i);
					teamAInitialPositionElement.setTextContent("" + set.getTeamAInitialPosition().get(i).getNumber());
					setElement.appendChild(teamAInitialPositionElement);
				}
				for (int i = 1; i < set.getTeamBInitialPosition().size(); ++i) {
					Element teamBInitialPositionElement = doc.createElement("teamBInitialPosition");
					teamBInitialPositionElement.setAttribute("position", "" + i);
					teamBInitialPositionElement.setTextContent("" + set.getTeamBInitialPosition().get(i).getNumber());
					setElement.appendChild(teamBInitialPositionElement);
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				Element setStartTimeElement = doc.createElement("setStartTime");
				setStartTimeElement.setTextContent(sdf.format(set.getSetStartTime()));
				setElement.appendChild(setStartTimeElement);
				Element setEndTimeElement = doc.createElement("setEndTime");
				setEndTimeElement.setTextContent(sdf.format(set.getSetEndTime()));
				setElement.appendChild(setEndTimeElement);
				
				Element pointRecordsElement = this.serializePointRecords(set, doc);//<pointRecords>
				setElement.appendChild(pointRecordsElement);
				
				// 考虑记录暂停
				Element pauseTeamAElement = this.serializePauseTeamA(set, doc);//<pauseTeamA>
				setElement.appendChild(pauseTeamAElement);
				Element pauseTeamBElement = this.serializePauseTeamB(set, doc);//<pauseTeamB>
				setElement.appendChild(pauseTeamBElement);

				// 考虑记录换人
				Element substitutionAElement = this.serializeSubstitutionA(set, doc);//<substitutionTeamA>
				setElement.appendChild(substitutionAElement);
				Element substitutionBElement = this.serializeSubstitutionB(set, doc);//<substitutionTeamB>
				setElement.appendChild(substitutionBElement);
				
				Element winTeamNameElement = doc.createElement("winTeamName");
				winTeamNameElement.setTextContent(set.getWinTeam().getName());
				setElement.appendChild(winTeamNameElement);
				Element finalScoreTeamAElement = doc.createElement("finalScoreTeamA");
				finalScoreTeamAElement.setTextContent("" + set.getFinalScoreTeamA());
				setElement.appendChild(finalScoreTeamAElement);
				Element finalScoreTeamBElement = doc.createElement("finalScoreTeamB");
				finalScoreTeamBElement.setTextContent("" + set.getFinalScoreTeamB());
				setElement.appendChild(finalScoreTeamBElement);
				Element setDurationElement = doc.createElement("setDuration");
				setDurationElement.setTextContent(set.getSetDuration());
				setElement.appendChild(setDurationElement);
				
				setsElement.appendChild(setElement);
			}
		}
		return setsElement;
	}

	public Element serializeMatches(LeagueMatch leagueMatch, Document doc) {
		// 
		Element matchesElement = doc.createElement("matches");
		if (leagueMatch.getMatches() != null) {
			for (Match match:leagueMatch.getMatches()) {
				Element matchElement = doc.createElement("match");
				matchElement.setAttribute("id", match.getId() + "");
				
				Element matchNameElement = doc.createElement("name");
				matchNameElement.setTextContent(match.getName());
				matchElement.appendChild(matchNameElement);
				Element teamANameElement = doc.createElement("teamAName");
				teamANameElement.setTextContent(match.getTeamA().getName());
				matchElement.appendChild(teamANameElement);
				Element teamBNameElement = doc.createElement("teamBName");
				teamBNameElement.setTextContent(match.getTeamB().getName());
				matchElement.appendChild(teamBNameElement);
				Element cityElement = doc.createElement("city");
				cityElement.setTextContent(match.getCity());
				matchElement.appendChild(cityElement);
				Element stadiumElement = doc.createElement("stadium");
				stadiumElement.setTextContent(match.getStadium());
				matchElement.appendChild(stadiumElement);
				Element siteElement = doc.createElement("site");
				siteElement.setTextContent(match.getSite());
				matchElement.appendChild(siteElement);
				Element stageElement = doc.createElement("stage");
				stageElement.setTextContent(match.getStage());
				matchElement.appendChild(stageElement);
				Element categoryElement = doc.createElement("category");
				categoryElement.setTextContent(match.getCategory());
				matchElement.appendChild(categoryElement);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				Element matchStartDateElement = doc.createElement("matchStartDate");
				matchStartDateElement.setTextContent(sdf.format(match.getMatchStartDate()));
				matchElement.appendChild(matchStartDateElement);
				Element matchEndDateElement = doc.createElement("matchEndDate");
				matchEndDateElement.setTextContent(sdf.format(match.getMatchEndDate()));
				matchElement.appendChild(matchEndDateElement);
				
				Element setsElement = this.serializeSets(match, doc);//<sets>
				matchElement.appendChild(setsElement);
				
				Element pauseTimeAElement = doc.createElement("pauseTimeA");
				pauseTimeAElement.setTextContent("" + match.getPauseTimeA());
				matchElement.appendChild(pauseTimeAElement);
				Element pauseTimeBElement = doc.createElement("pauseTimeB");
				pauseTimeBElement.setTextContent("" + match.getPauseTimeB());
				matchElement.appendChild(pauseTimeBElement);
				Element substitutionTimeAElement = doc.createElement("substitutionTimeA");
				substitutionTimeAElement.setTextContent("" + match.getSubstitutionTimeA());
				matchElement.appendChild(substitutionTimeAElement);
				Element substitutionTimeBElement = doc.createElement("substitutionTimeB");
				substitutionTimeBElement.setTextContent("" + match.getSubstitutionTimeB());
				matchElement.appendChild(substitutionTimeBElement);
				Element winTeamNameElement = doc.createElement("winTeamName");
				winTeamNameElement.setTextContent(match.getWinTeam().getName());
				matchElement.appendChild(winTeamNameElement);
				Element totalScoreTeamAElement = doc.createElement("totalScoreTeamA");
				totalScoreTeamAElement.setTextContent("" + match.getTotalScoreTeamA());
				matchElement.appendChild(totalScoreTeamAElement);
				Element totalScoreTeamBElement = doc.createElement("totalScoreTeamB");
				totalScoreTeamBElement.setTextContent("" + match.getTotalScoreTeamB());
				matchElement.appendChild(totalScoreTeamBElement);
				Element setsDurationElement = doc.createElement("setsDuration");
				setsDurationElement.setTextContent("" + match.getSetsDuration());
				matchElement.appendChild(setsDurationElement);
				Element failedSetTimeElement = doc.createElement("failedSetTime");
				failedSetTimeElement.setTextContent("" + match.getFailedSetTime());
				matchElement.appendChild(failedSetTimeElement);
				
				matchesElement.appendChild(matchElement);
			}
		}
		return matchesElement;
	}

	@Override
	public String serialize(List<LeagueMatch> leagueMatches) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element rootElement = doc.createElement("leagueMatches");
		for (LeagueMatch leagueMatch:leagueMatches) {
			Element leagueMatchElement = doc.createElement("leagueMatch");
			leagueMatchElement.setAttribute("id", leagueMatch.getId() + "");
			
			Element nameElement = doc.createElement("name");
			nameElement.setTextContent(leagueMatch.getName());
			leagueMatchElement.appendChild(nameElement);
				
			Element teamsElement = this.serializeTeams(leagueMatch, doc);//<teams>
			leagueMatchElement.appendChild(teamsElement);
			
			Element matchesElement = this.serializeMatches(leagueMatch, doc);//<matches>
			leagueMatchElement.appendChild(matchesElement);
			
			rootElement.appendChild(leagueMatchElement);
		}
		
		doc.appendChild(rootElement);
		
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		
		StringWriter writer = new StringWriter();
		
		Source source = new DOMSource(doc);
		Result result = new StreamResult(writer);
		transformer.transform(source, result);
		return writer.toString();
	}

}

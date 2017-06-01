package com.example.volleyball.score;

import android.app.Application;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.example.volleyball.score.GlobalConstant;
import com.volleyball.file.DomLeagueMatchParser;
import com.volleyball.file.FileClass;
import com.volleyball.match.LeagueMatch;

public class DataApplication extends Application {
	
	private String msg = GlobalConstant.msg;
	
	// ����������application���������ڵ� �������ݣ���������һ��List����ʾ��
	private List<LeagueMatch> leagueMatches;// ��ǰӦ�õ����б�����Ϣ
	private int indexOfLeagueMatch;// ��ǰ���ڴ���ı���������leagueMatches������
	private String xmlPath;// xml����·��
	private DomLeagueMatchParser parser;// xml�ĵ�������

	public DataApplication() {
		// 
	}

	@Override
	public void onCreate() {
		Log.d(msg, "DataApplication onCreate() event");
		// ���򴴽���ʱ��ִ��
		String path = FileClass.getSDPath();
		if (path == null) {
			Log.d(msg, "Can not find SD card!!!");
			return;
		}
		String absolutePath = path + "/" + GlobalConstant.folder;
		FileClass.isDirExist(absolutePath);
		String absoluteFileName = absolutePath + "/" + GlobalConstant.xmlFileName;
		FileClass.isFileExist(absoluteFileName);
		System.out.println("absolutePath:" + absoluteFileName);
		this.setXmlPath(absoluteFileName);
		this.setParser(new DomLeagueMatchParser());

		// (davidblus): �򿪳���ʱ�����벢����xml�ļ���
		if (!loadAnalysisXml()) {
			System.out.println("load analysis xml failed!!!");
		}
		super.onCreate();
	}
	public boolean loadAnalysisXml() {
		try {
			FileInputStream fis = new FileInputStream(this.getXmlPath());
			this.setLeagueMatches(this.getParser().parse(fis));
			for (LeagueMatch leagueMatch : this.getLeagueMatches()) {
				System.out.println("leagueMatch:" + leagueMatch.toString());
			}
		} catch (FileNotFoundException e) {
			// 
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean saveLeagueMatchToXml() {
		try {
			FileOutputStream fos = new FileOutputStream(this.getXmlPath());
			String xmlContext = this.getParser().serialize(this.getLeagueMatches());
			fos.write(xmlContext.getBytes("UTF-8"));
			fos.close();
		} catch (FileNotFoundException e) {
			// 
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public int generateUniqueLeagueMatchId() {
		int max = 0;
		for (LeagueMatch leagueMatch:this.getLeagueMatches()) {
			if (max < leagueMatch.getId()) {
				max = leagueMatch.getId();
			}
		}
		return (max + 1);
	}
	public boolean isLeagueMatchNameExist(String name) {
		for (LeagueMatch leagueMatch:this.getLeagueMatches()) {
			if (name.equals(leagueMatch.getName())) {
				return true;
			}
		}
		return false;
	}
	public int getIndexOfLeagueMatchByName(String name) {
		int result = -1;
		for (int i = 0; i < this.getLeagueMatches().size(); i++) {
			if (name.equals(this.getLeagueMatches().get(i).getName())) {
				return i;
			}
		}
		return result;
	}
	public boolean addLeagueMatch(LeagueMatch leagueMatch) {
		List<LeagueMatch> leagueMatches = this.getLeagueMatches();
		leagueMatches.add(leagueMatch);
		this.setLeagueMatches(leagueMatches);
		System.out.println("leagueMatches:" + this.getLeagueMatches().toString());
		return true;
	}
	public boolean delLeagueMatch(LeagueMatch leagueMatch) {
		List<LeagueMatch> leagueMatches = this.getLeagueMatches();
		leagueMatches.remove(leagueMatch);
		this.setLeagueMatches(leagueMatches);
		return true;
	}
	public LeagueMatch getLeagueMatchByName(String name) {
		for (LeagueMatch leagueMatch:this.getLeagueMatches()) {
			if (leagueMatch.getName().equals(name)) {
				return leagueMatch;
			}
		}
		return null;
	}
	public LeagueMatch getNowLeagueMatch() {
		return this.getLeagueMatches().get(this.getIndexOfLeagueMatch());
	}
	public void setNowLeagueMatch(LeagueMatch leagueMatch) {
		List<LeagueMatch> leagueMatches = this.getLeagueMatches();
		leagueMatches.set(this.getIndexOfLeagueMatch(), leagueMatch);
		this.setLeagueMatches(leagueMatches);
	}

	public List<LeagueMatch> getLeagueMatches() {
		return leagueMatches;
	}

	public void setLeagueMatches(List<LeagueMatch> leagueMatches) {
		this.leagueMatches = leagueMatches;
	}

	public int getIndexOfLeagueMatch() {
		return indexOfLeagueMatch;
	}

	public void setIndexOfLeagueMatch(int indexOfLeagueMatch) {
		this.indexOfLeagueMatch = indexOfLeagueMatch;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public DomLeagueMatchParser getParser() {
		return parser;
	}

	public void setParser(DomLeagueMatchParser parser) {
		this.parser = parser;
	}

	@Override
	public void onTerminate() {
		// ������ֹ��ʱ��ִ��
		Log.d(msg, "DataApplication onTerminate() event");
		super.onTerminate();
	}
	@Override
	public void onLowMemory() {
		// ���ڴ��ʱ��ִ��
		Log.d(msg, "DataApplication onLowMemory() event");
		super.onLowMemory();
	}
	@Override
	public void onTrimMemory(int level) {
		// �������ڴ������ʱ��ִ��
		Log.d(msg, "DataApplication onTrimMemory() event");
		super.onTrimMemory(level);
	}
}

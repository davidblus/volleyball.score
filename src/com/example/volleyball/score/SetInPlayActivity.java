package com.example.volleyball.score;

import java.util.ArrayList;
import java.util.List;

import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import com.volleyball.match.Point;
import com.volleyball.match.Set;
import com.volleyball.match.Substitution;
import com.volleyball.match.Team;
import com.volleyball.match.TeamMember;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SetInPlayActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private TextView titleText;//������飺A/B��
	private TextView serverNumText;//������� �ı��ؼ�
	private TextView scoreText;//�����ȷ� �ı��ؼ�
	private RadioGroup getScoreGroup;//�÷ֵ�ѡ��ť��
	private RadioGroup loseScoreGroup;//ʧ�ֵ�ѡ��ť��

	private Team teamA;//A��
	private Team teamB;//B��
	private Team serverTeam;//�������
	private int serverNum;//��ǰ�������
	private int scoreServer;//��ǰ�������򷽱ȷ�
	private int scoreClient;//��ǰ�������򷽱ȷ�
	private List<TeamMember> teamAPosition;//��ǰ����A��λ�ã��±��1-6�ֱ����1-6��λ��0Ϊ������ռ�null
	private List<TeamMember> teamBPosition;//��ǰ����B��λ�ã��±��1-6�ֱ����1-6��λ��0Ϊ������ռ�null
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "SetInPlayActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_in_play);
		
		this.setTitleText((TextView) findViewById(R.id.titleText));
		this.setServerNumText((TextView) findViewById(R.id.serverNumText));
		this.setScoreText((TextView) findViewById(R.id.scoreText));
		this.setGetScoreGroup((RadioGroup) findViewById(R.id.getScoreGroup));
		this.setLoseScoreGroup((RadioGroup) findViewById(R.id.loseScoreGroup));
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		
		this.setTeamA(match.getTeamA());
		this.setTeamB(match.getTeamB());
		
		boolean isFirstInto = true;
		// TODO: ���Ƿ�����ͣ�����ˡ��з�����ע�¼�ʱ��activity��ת����
		if(isFirstInto)	{
			//��һ�ν������activity����ʼ�����á�
			this.setServerTeam(set.getFirstTeam());
			this.setTeamAPosition(set.getTeamAInitialPosition());
			this.setTeamBPosition(set.getTeamBInitialPosition());
			if(this.getServerTeam().getId() == this.getTeamA().getId()) {
				this.setServerNum(this.getTeamAPosition().get(1).getNumber());
			}
			else {
				this.setServerNum(this.getTeamBPosition().get(1).getNumber());
			}
			this.setScoreServer(0);
			this.setScoreClient(0);
			
			//��¼�˾ֱ�����ʼʱ��
			set.setSetStartTime(new java.util.Date());
			match.setNowSet(set);
			leagueMatch.setNowMatch(match);
			app.setNowLeagueMatch(leagueMatch);
			
		}
		
		//���ý�����ʾ����
		this.updateView();
		
		//���õ�ѡ��ť��������ؼ�
		this.getGetScoreGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 
				if(checkedId == -1) {
					return;
				}
				getLoseScoreGroup().clearCheck();
				group.check(checkedId);
			}
		});
		this.getLoseScoreGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 
				if(checkedId == -1) {
					return;
				}
				getGetScoreGroup().clearCheck();
				group.check(checkedId);
			}
		});
		
	}
	public void updateView() {
		//���ý�����ʾ����
		if(this.getServerTeam().getId() == this.getTeamA().getId()) {
			this.getTitleText().setText("������飺A��");
		}
		else {
			this.getTitleText().setText("������飺B��");
		}
		this.getServerNumText().setText("" + this.getServerNum());
		this.getScoreText().setText("" + this.getScoreServer() + ":" + this.getScoreClient());
		this.getGetScoreGroup().clearCheck();
		this.getLoseScoreGroup().clearCheck();
	}
	public void pause(View view) {
		// �����ͣ��ť��������ͣactivity
		Intent i = new Intent(SetInPlayActivity.this, SetPauseActivity.class);
		startActivity(i);
	}
	public void substitution(View view) {
		// ������˰�ť�����뻻��activity��ʹ��startactivityforresult�ķ�ʽ������requestCode=0
		Intent i = new Intent(SetInPlayActivity.this, SetSubstitutionActivity.class);
		i.putExtra("teamAPosition", (ArrayList<TeamMember>)this.getTeamAPosition());
		i.putExtra("teamBPosition", (ArrayList<TeamMember>)this.getTeamBPosition());
		// requestCode = 0
		startActivityForResult(i, 0);
	}
	public List<TeamMember> doSubstitution(List<TeamMember> oldPosition, Substitution substitution) {
		for (int i = 0; i < substitution.getDownTeamMemberList().size(); ++i) {
			int index = oldPosition.indexOf(substitution.getDownTeamMemberList().get(i));
			oldPosition.set(index, substitution.getUpTeamMemberList().get(i));
		}
		return oldPosition;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 
		Log.d(msg, "SetInPlayActivity onActivityResult() event");
		if(requestCode == 0 && resultCode == 0) {
			// �ӻ���activity��ת����
			try {
				Bundle result = data.getExtras();
				String team = result.getString("substitutionTeam");
				System.out.println("substitutionTeam:" + team);
				
				DataApplication app = (DataApplication) getApplication();
				LeagueMatch leagueMatch = app.getNowLeagueMatch();
				Match match = leagueMatch.getNowMatch();
				Set set = match.getNowSet();
				if (team.equals("A")) {
					Substitution lastSubstitution = set.getLastSubstitutionTeamA();
					this.setTeamAPosition(this.doSubstitution(this.getTeamAPosition(), lastSubstitution));
					if(this.getServerTeam().getId() == this.getTeamA().getId()) {
						this.setServerNum(this.getTeamAPosition().get(1).getNumber());
					}
				}
				else if (team.equals("B")) {
					Substitution lastSubstitution = set.getLastSubstitutionTeamB();
					this.setTeamBPosition(this.doSubstitution(this.getTeamBPosition(), lastSubstitution));
					if(this.getServerTeam().getId() == this.getTeamB().getId()) {
						this.setServerNum(this.getTeamBPosition().get(1).getNumber());
					}
				}
				else {
					System.out.println("���˶���Ȳ���A��Ҳ����B������");
				}
			} catch (Exception e) {
				// 
				System.out.println("ȡ�����ˡ�");
			}
			this.updateView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	public void setEnd(View view) {
		// 
		new AlertDialog.Builder(view.getContext()).setTitle("��������")
		.setMessage("ȷ������������")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 
				DataApplication app = (DataApplication) getApplication();
				LeagueMatch leagueMatch = app.getNowLeagueMatch();
				Match match = leagueMatch.getNowMatch();
				Set set = match.getNowSet();
				
				set.setSetEndTime(new java.util.Date());
				set.setWinTeam(getServerTeam());
				if(getServerTeam().getId() == getTeamA().getId()) {
					set.setFinalScoreTeamA(getScoreServer());
					set.setFinalScoreTeamB(getScoreClient());
				}
				else {
					set.setFinalScoreTeamA(getScoreClient());
					set.setFinalScoreTeamB(getScoreServer());
				}
				long durationSecond = (set.getSetEndTime().getTime() - set.getSetStartTime().getTime()) / 1000;
				long hour = durationSecond % (24*3600) / 3600;
				long minute = durationSecond % 3600 / 60;
				long second = durationSecond % 60;
				set.setSetDuration("" + hour + "'" + minute + "''" + second);
				
				match.setNowSet(set);
				if(match.calSetWinTeam() != null) {
					//���������ѽ���������㲢��������match�е���Ϣ
					System.out.println("���������ѽ���������");
					match.setMatchEndDate(new java.util.Date());
					match.calSetPauseTimeA();
					match.calSetPauseTimeB();
					match.calSetSubstitutionTimeA();
					match.calSetSubstitutionTimeB();
					match.calSetTotalScoreTeamA();
					match.calSetTotalScoreTeamB();
					match.calSetSetsDuration();
					match.calSetFailedSetTime();
					
					leagueMatch.setNowMatch(match);
					app.setNowLeagueMatch(leagueMatch);
					
					//��ת���������activity
					Intent i = new Intent(SetInPlayActivity.this, ShowMatchResultActivity.class);
					startActivity(i);
					finish();
				}
				else {
					//��������δ��������ת����һ�ֱ���
					Set nextSet = new Set();
					nextSet.setNum(match.getSetNumber() + 1);
					match.addSet(nextSet);
					match.setSetNumber(match.getSetNumber() + 1);
					
					leagueMatch.setNowMatch(match);
					app.setNowLeagueMatch(leagueMatch);
					
					// ��ת����һ�ֱ�������ǰ��дλ����Ϣactivity
					Intent i = new Intent(SetInPlayActivity.this, FillOutPreSetActivity.class);
					startActivity(i);
					finish();
				}
			}
		}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 				
			}
		}).show();
	}
	public void withdraw(View view) {
		// 
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		
		Point point = set.popPointRecord();
		if(point == null) {
			Toast.makeText(SetInPlayActivity.this, "�޷����أ�����", Toast.LENGTH_SHORT).show();
			return;
		}
		if(point.getResultStr().indexOf("�÷�") != -1) {
			this.setScoreServer(point.getServerScore() - 1);
		}
		else {
			this.setServerTeam(point.getTeam());
			this.setServerNum(point.getTeamMember().getNumber());
			this.setScoreServer(point.getServerScore());
			this.setScoreClient(point.getClientScore() - 1);
			this.setTeamAPosition(point.getTeamAPosition());
			this.setTeamBPosition(point.getTeamBPosition());
		}
		
		this.updateView();

		match.setNowSet(set);
		leagueMatch.setNowMatch(match);
		app.setNowLeagueMatch(leagueMatch);
	}
	
	public void nextPoint(View view) {
		// 
		RadioButton getScoreRadio = (RadioButton) findViewById(this.getGetScoreGroup().getCheckedRadioButtonId());
		RadioButton loseScoreRadio = (RadioButton) findViewById(this.getLoseScoreGroup().getCheckedRadioButtonId());
		if(getScoreRadio == null && loseScoreRadio == null) {
			Toast.makeText(SetInPlayActivity.this, "��ѡ���ʧ�����������", Toast.LENGTH_SHORT).show();
			return;
		}
		
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		Match match = leagueMatch.getNowMatch();
		Set set = match.getNowSet();
		
		Point point = new Point();
		point.setTeam(this.getServerTeam());
		if(this.getServerTeam().getId() == this.getTeamA().getId()) {
			point.setTeamMember(this.getTeamAPosition().get(1));
		}
		else {
			point.setTeamMember(this.getTeamBPosition().get(1));
		}
		point.setTeamAPosition(this.getTeamAPosition());
		point.setTeamBPosition(this.getTeamBPosition());
		
		if(getScoreRadio != null) {
			//ԭ���򷽵÷�
			point.setResultStr((String) getScoreRadio.getText());
			point.setServerScore(this.getScoreServer() + 1);
			point.setClientScore(this.getScoreClient());
			
			this.setScoreServer(this.getScoreServer() + 1);
		}
		else {
			//ԭ����ʧ�֣�����
			point.setResultStr((String) loseScoreRadio.getText());
			point.setServerScore(this.getScoreServer());
			point.setClientScore(this.getScoreClient() + 1);
			
			if(this.getServerTeam().getId() == this.getTeamA().getId()) {
				this.setServerTeam(this.getTeamB());
				this.setTeamBPosition(this.rotatePosition(this.getTeamBPosition()));
				this.setServerNum(this.getTeamBPosition().get(1).getNumber());
			}
			else {
				this.setServerTeam(this.getTeamA());
				this.setTeamAPosition(this.rotatePosition(this.getTeamAPosition()));
				this.setServerNum(this.getTeamAPosition().get(1).getNumber());
			}
			int tempScore = this.getScoreServer();
			this.setScoreServer(this.getScoreClient() + 1);
			this.setScoreClient(tempScore);
		}
		
		this.updateView();
		
		set.pushPointRecord(point);
		match.setNowSet(set);
		leagueMatch.setNowMatch(match);
		app.setNowLeagueMatch(leagueMatch);
	}

	public List<TeamMember> rotatePosition(List<TeamMember> teamPosition) {
		for(int i = 0; i < 6; ++i) {
			teamPosition.set(i, teamPosition.get(i+1));
		}
		teamPosition.set(6, teamPosition.get(0));
		teamPosition.set(0, null);
		return teamPosition;
	}
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "SetInPlayActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "SetInPlayActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "SetInPlayActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "SetInPlayActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "SetInPlayActivity onDestroy() event");
	}

	public TextView getTitleText() {
		return titleText;
	}

	public void setTitleText(TextView titleText) {
		this.titleText = titleText;
	}

	public TextView getServerNumText() {
		return serverNumText;
	}

	public void setServerNumText(TextView serviceNumText) {
		this.serverNumText = serviceNumText;
	}

	public TextView getScoreText() {
		return scoreText;
	}

	public void setScoreText(TextView scoreText) {
		this.scoreText = scoreText;
	}

	public RadioGroup getGetScoreGroup() {
		return getScoreGroup;
	}

	public void setGetScoreGroup(RadioGroup getScoreGroup) {
		this.getScoreGroup = getScoreGroup;
	}

	public RadioGroup getLoseScoreGroup() {
		return loseScoreGroup;
	}

	public void setLoseScoreGroup(RadioGroup loseScoreGroup) {
		this.loseScoreGroup = loseScoreGroup;
	}

	public int getServerNum() {
		return serverNum;
	}

	public void setServerNum(int serviceNum) {
		this.serverNum = serviceNum;
	}

	public int getScoreServer() {
		return scoreServer;
	}

	public void setScoreServer(int scoreService) {
		this.scoreServer = scoreService;
	}

	public int getScoreClient() {
		return scoreClient;
	}

	public void setScoreClient(int scoreClient) {
		this.scoreClient = scoreClient;
	}

	public List<TeamMember> getTeamAPosition() {
		return teamAPosition;
	}

	public void setTeamAPosition(List<TeamMember> teamAPosition) {
		this.teamAPosition = teamAPosition;
	}

	public List<TeamMember> getTeamBPosition() {
		return teamBPosition;
	}

	public void setTeamBPosition(List<TeamMember> teamBPosition) {
		this.teamBPosition = teamBPosition;
	}

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	public Team getServerTeam() {
		return serverTeam;
	}

	public void setServerTeam(Team serviceTeam) {
		this.serverTeam = serviceTeam;
	}
}

package com.example.volleyball.score;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.volleyball.file.DomLeagueMatchParser;
import com.volleyball.file.FileClass;
import com.volleyball.match.LeagueMatch;
import com.volleyball.match.Match;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportMatchActivity extends Activity {

	private String msg = GlobalConstant.msg;

	private ListView chooseMatchList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 
		Log.d(msg, "ExportMatchActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export_match);
		
		this.setChooseMatchList((ListView) findViewById(R.id.matchChooseListView));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getMatchData());
		this.getChooseMatchList().setAdapter(adapter);
	}

	private List<String> getMatchData() {
		// ��DataApplication������ȡ�Ѿ������õ����ݡ�
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = app.getNowLeagueMatch();
		List<String> data = new ArrayList<String>();
		for (Match match:leagueMatch.getMatches()) {
			data.add(match.getStage() + "��" + match.getName());
		}
		return data;
	}

	public void exportSelectedMatches(View view) {
		// 
		String fileNameRoot = "Match";
		long[] selectedTeamId = this.getListSelectededItemIds(this.getChooseMatchList());
		System.out.println("Hit btnExportSelected");
		ArrayList<LeagueMatch> exportLeagueMatches = new ArrayList<LeagueMatch>();
		DataApplication app = (DataApplication) getApplication();
		LeagueMatch leagueMatch = new LeagueMatch(app.getNowLeagueMatch());
		
		List<Match> matches = new ArrayList<Match>();
		for (long position:selectedTeamId) {
			String selectedName = (String) this.getChooseMatchList().getItemAtPosition((int) position);
			System.out.println("selectedName:" + selectedName);
			Match match = leagueMatch.getMatchByStageName(selectedName);
			matches.add(match);
		}
		leagueMatch.setMatches(matches);
		
		exportLeagueMatches.add(leagueMatch);
		DomLeagueMatchParser parser = new DomLeagueMatchParser();
		try {
			String xmlContext = parser.serialize(exportLeagueMatches);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CHINA);
			String time = sdf.format(new java.util.Date());
			String exportXmlPath = FileClass.getSDPath() + "/" + GlobalConstant.folder + "/" + fileNameRoot + "_" + time + ".xml";
			FileClass.isFileExist(exportXmlPath);
			FileOutputStream fos = new FileOutputStream(exportXmlPath);
			fos.write(xmlContext.getBytes("UTF-8"));
			fos.close();
			System.out.println("exportXmlPath:" + exportXmlPath);
			new AlertDialog.Builder(view.getContext()).setTitle("����������¼")
			.setMessage("�����ļ�·����" + exportXmlPath)
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 
				}
			}).show();
		} catch (Exception e) {
			// 
			e.printStackTrace();
			return;
		}
	}
    // ����ʹ��getCheckItemIds()����
    public long[] getListSelectededItemIds(ListView listView) {
         
        long[] ids = new long[listView.getCount()];//getCount()����ȡ��ListView��������item�ܸ���
        //�����û�ѡ��Item���ܸ���
        int checkedTotal = 0;
        for (int i = 0; i < listView.getCount(); i++) {
            //������Item�Ǳ�ѡ�е�
            if (listView.isItemChecked(i)) {
                ids[checkedTotal++] = i;
            }
        }
 
        if (checkedTotal < listView.getCount()) {
            //����ѡ�е�Item��ID����
            final long[] selectedIds = new long[checkedTotal];
            //���鸴�� ids
            System.arraycopy(ids, 0, selectedIds, 0, checkedTotal);
            return selectedIds;
        } else {
            //�û������е�Item��ѡ��
            return ids;
        }
    }
    
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart(){
		super.onStart();
		Log.d(msg, "ExportMatchActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "ExportMatchActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "ExportMatchActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "ExportMatchActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "ExportMatchActivity onDestroy() event");
	}

	public ListView getChooseMatchList() {
		return chooseMatchList;
	}

	public void setChooseMatchList(ListView chooseMatchList) {
		this.chooseMatchList = chooseMatchList;
	}

}

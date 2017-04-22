package com.example.volleyball.score;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.volleyball.file.DomLeagueMatchParser;
import com.volleyball.file.FileClass;
import com.volleyball.match.LeagueMatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportLeagueMatchActivity extends Activity {

	private String msg = GlobalConstant.msg;
	
	private ListView chooseLeagueMatchList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(msg, "ExportLeagueMatchActivity onCreate() event");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export_league_match);
		
		this.setChooseLeagueMatchList((ListView) this.findViewById(R.id.matchChooseListView));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getLeagueMatchData());
		this.getChooseLeagueMatchList().setAdapter(adapter);
	}
	
	private List<String> getLeagueMatchData() {
		// ��DataApplication������ȡ�Ѿ������õ����ݡ�
		final DataApplication app = (DataApplication) getApplication();
		List<LeagueMatch> leagueMatches = app.getLeagueMatches();
		List<String> data = new ArrayList<String>();
		for (LeagueMatch leagueMatch:leagueMatches) {
			data.add(leagueMatch.getName());
		}
		return data;
	}
	
	public void exportSelectedLeagueMatches(View v) {
		String fileNameRoot = "LeagueMatch";
		long[] selectedLeagueMatchId = this.getListSelectededItemIds(this.getChooseLeagueMatchList());
		System.out.println("Hit btnExportSelected");
		ArrayList<LeagueMatch> exportLeagueMatches = new ArrayList<LeagueMatch>();
		int id = 0;
		for (long position:selectedLeagueMatchId) {
			String name = (String) this.getChooseLeagueMatchList().getItemAtPosition((int) position);
			System.out.println("name:" + name);
			id = id + 1;
			LeagueMatch leagueMatch = new LeagueMatch(id, name);
			exportLeagueMatches.add(leagueMatch);
		}
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
			new AlertDialog.Builder(v.getContext()).setTitle("��������")
			.setMessage("�����ļ�·����" + exportXmlPath)
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		Log.d(msg, "ExportLeagueMatchActivity onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(msg, "ExportLeagueMatchActivity onResume() event");
	}
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(msg, "ExportLeagueMatchActivity onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(msg, "ExportLeagueMatchActivity onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(msg, "ExportLeagueMatchActivity onDestroy() event");
	}

	public ListView getChooseLeagueMatchList() {
		return chooseLeagueMatchList;
	}

	public void setChooseLeagueMatchList(ListView chooseLeagueMatchList) {
		this.chooseLeagueMatchList = chooseLeagueMatchList;
	}

}

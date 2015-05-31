package org.radiowar.taginfo.activity.more;

import org.radiowar.taginfo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AboutUsAdapter extends BaseAdapter {
		Activity activity;

		public AboutUsAdapter(Activity a) {
			activity = a;
		}

		public int getCount() {
			return 3;
		}

		public Object getItem(int item) {
			return item;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int choose = position;
			switch (position) {
			case 0:
				convertView = LayoutInflater.from(activity.getApplicationContext())
						.inflate(R.layout.aboutus_cell1, null);
				break;
			case 1:
				convertView = LayoutInflater.from(activity.getApplicationContext())
						.inflate(R.layout.aboutus_cell2, null);
				break;
			default:
				convertView = LayoutInflater.from(activity.getApplicationContext())
						.inflate(R.layout.aboutus_cell3, null);
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(choose == 1){
						AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(
								activity);
						alertbBuilder
								.setTitle("mail to:")
								.setMessage("info@radiowar.org")
								.setPositiveButton("ok",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {

												Uri uri = Uri.parse("mailto:info@radiowar.org");  
											    Intent it = new Intent(Intent.ACTION_SENDTO, uri);  
											    it.putExtra(Intent.EXTRA_SUBJECT, "info@radiowar.org"); //设置收件人
											    activity.startActivity(it);  
											}
										})
								.setNegativeButton("cancel",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int which) {
												dialog.cancel();
											}
										}).create();
						alertbBuilder.show();
					}
					else if (choose == 2) {
						AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(
								activity);
						alertbBuilder
								.setTitle("website:")
								.setMessage("http://radiowar.org")
								.setPositiveButton("ok",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int which) {
													Intent websiteIntent = new Intent(
													Intent.ACTION_VIEW,
													Uri.parse("http://radiowar.org"));
													activity.startActivity(websiteIntent);
											}
										})
								.setNegativeButton("cancel",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {
												dialog.cancel();
											}
										}).create();
						alertbBuilder.show();
					}
				}

			});

			return convertView;
		}
	}

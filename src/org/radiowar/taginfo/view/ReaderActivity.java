package org.radiowar.taginfo.view;

import java.io.IOException;

import org.radiowar.taginfo.R;
import org.radiowar.taginfo.mifare.MifareBlock;
import org.radiowar.taginfo.mifare.MifareClassCard;
import org.radiowar.taginfo.mifare.MifareSector;
import org.radiowar.taginfo.util.Converter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ReaderActivity extends Activity implements OnClickListener{

	private Button leftButton;
	private Button rightButton;
	private TextView titleText;
	
	private ProgressDialog mydialog;
	
	// NFC parts
	private static NfcAdapter mAdapter;
	private static PendingIntent mPendingIntent;
	private static IntentFilter[] mFilters;
	private static String[][] mTechLists;


	private static final int AUTH = 1;
	private static final int EMPTY_BLOCK_0 = 2;
	private static final int EMPTY_BLOCK_1 = 3;
	private static final int NETWORK = 4;
	private static final String TAG = "purchtagscanact";
	
	private String[] defaultKeys = new String[]{
			 "FFFFFFFFFFFF"
			,"B5FF67CBA951"
			,"000000000000"
			,"A0A1A2A3A4A5"
			,"B0B1B2B3B4B5"
			,"4D3A99C351DD"
			,"1A982C7E459A"
			,"AABBCCDDEEFF"
			,"EE9BD361B01B"
	};
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.reader);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  // titlebar为自己标题栏的布局
        
        this.leftButton = (Button)this.findViewById(R.id.leftButton);
        this.rightButton = (Button)this.findViewById(R.id.rightButton);
        this.titleText = (TextView)this.findViewById(R.id.titleText);
        
        this.leftButton.setOnClickListener(this);
        this.rightButton.setOnClickListener(this);
		
		mAdapter = NfcAdapter.getDefaultAdapter(this);
        
		// Create a generic PendingIntent that will be deliver to this activity.
		// The NFC stack
		// will fill in the intent with the details of the discovered tag before
		// delivering to
		// this activity.
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		// Setup an intent filter for all MIME based dispatches
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		
		try 
		{
			ndef.addDataType("*/*");
		} 
		catch (MalformedMimeTypeException e) 
		{
			throw new RuntimeException("fail", e);
		}
		
		mFilters = new IntentFilter[] { ndef, };

		// Setup a tech list for all NfcF tags
		mTechLists = new String[][] { new String[] { MifareClassic.class
				.getName() } };
		
		Intent intent = getIntent();
		resolveIntent(intent);
    }
    
    private void resolveIntent(Intent intent) {
		// 1) Parse the intent and get the action that triggered this intent
		String action = intent.getAction();
		// 2) Check if it was triggered by a tag discovered interruption.
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			
			mydialog = ProgressDialog.show(ReaderActivity.this, "loading","loading");
			
			// 3) Get an instance of the TAG from the NfcAdapter
			Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			// 4) Get an instance of the Mifare classic card from this TAG
			// intent
			MifareClassic mfc = MifareClassic.get(tagFromIntent);
			MifareClassCard mifareClassCard=null;
			System.out.println(mfc.getTag().getId());
			System.out.println(mfc.getTag().getTechList());
			
			try { // 5.1) Connect to card
				mfc.connect();
				boolean auth = false;
				// 5.2) and get the number of sectors this card has..and loop
				// thru these sectors
				int secCount = mfc.getSectorCount();
				mifareClassCard= new MifareClassCard(secCount);
				int bCount = 0;
				int bIndex = 0;
				for (int j = 0; j < secCount; j++) {
					MifareSector mifareSector = new MifareSector();
					mifareSector.sectorIndex = j;
					// 6.1) authenticate the sector
					
					for(String key : defaultKeys)
					{
						auth = mfc.authenticateSectorWithKeyA(j,
								Converter.hexStringToByteArray(key));
						if(auth)
							break;
					}
					
							
					mifareSector.authorized = auth;
					if (auth) {
						// 6.2) In each sector - get the block count
						bCount = mfc.getBlockCountInSector(j);
						bCount =Math.min(bCount, MifareSector.BLOCKCOUNT);
						bIndex = mfc.sectorToBlock(j);
						for (int i = 0; i < bCount; i++) {

							// 6.3) Read the block
							byte []data = mfc.readBlock(bIndex);
							//System.out.println(Converter.getHexString(data, 16));
							MifareBlock mifareBlock = new MifareBlock(data);
							mifareBlock.blockIndex = bIndex;
							// 7) Convert the data into a string from Hex
							// format.

							bIndex++;
							mifareSector.blocks[i] = mifareBlock;
							
						}
						mifareClassCard.setSector(mifareSector.sectorIndex,
								mifareSector);
					} else { // Authentication failed - Handle it

					}
				}
				this.mydialog.dismiss();
				mfc.close();
				Intent dataIntent = new Intent(this,DataListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("mifare", mifareClassCard);
				dataIntent.putExtras(bundle);
				this.startActivityForResult(dataIntent, 0);
				
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
				showAlert(3);
				this.mydialog.dismiss();
			}finally{

				if(mifareClassCard!=null){
					mifareClassCard.debugPrint();
				}
			}
		}// End of IF
	}// End of method

    private void showAlert(int alertCase) {
		// prepare the alert box
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		switch (alertCase) {

		case AUTH:// Card Authentication Error
			alertbox.setMessage("Authentication Failed on Block 0");
			break;
		case EMPTY_BLOCK_0: // Block 0 Empty
			alertbox.setMessage("Failed reading Block 0");
			break;
		case EMPTY_BLOCK_1:// Block 1 Empty
			alertbox.setMessage("failed");
			break;
		case NETWORK: // Communication Error
			alertbox.setMessage("Tag reading error");
			break;
		}
		// set a positive/yes button and create a listener
		alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			// Save the data from the UI to the database - already done
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});
		// display box
		alertbox.show();

	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
				mTechLists);
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
		resolveIntent(intent);
		// mText.setText("Discovered tag " + ++mCount + " with intent: " +
		// intent);
	}

	@Override
	public void onPause() {
		super.onPause();
		mAdapter.disableForegroundDispatch(this);
	}

	@Override
	public void onClick(View view) {
		if(view == this.leftButton)
		{
			setResult(RESULT_OK, getIntent());
			finish();
		}	
		else if(view == this.rightButton)
		{
			
		}
	}
}

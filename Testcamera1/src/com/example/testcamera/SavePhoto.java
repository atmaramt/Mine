package com.example.testcamera;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testcamera.R;


public class SavePhoto extends Activity {

	private static final int SAMPLE_SIZE = 2;
	private static final String TAG = "SavePhoto";
	File file;
	private Uri outputFileUri;
	private ImageView capturedImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capturephoto);
		capturedImage = (ImageView)findViewById(R.id.imageView1);
		Button capture = (Button)findViewById(R.id.button1);
		capture.setOnClickListener(new View.OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				file = new File(Environment.getExternalStorageDirectory(), "BiryhdayPhoto/ " + String.valueOf(System.currentTimeMillis()) + ".jpg"); 
				 
//				outputFileUri = Uri.fromFile(file); 
				Log.d(TAG, "outputFileUri-->"+outputFileUri);
//				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
				 
				startActivityForResult(intent, 1); 

			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "onActivityResult-->"+"requestCode->"+requestCode+" resultCode->"+resultCode +" data-->"+data);
		 if (requestCode == 1) { 
			    switch( resultCode ) { 
			      case 0: 
			        break; 
			 
			      case -1: 
			    	  Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			    	  capturedImage.setImageBitmap(photo); 
			    	  
//			    	  capturedImage.setImageBitmap(setupImage(data)); 
			        break; 
			    } 
			  } 
	}
	
	public Bitmap setupImage(Intent data) { 
		  BitmapFactory.Options options = new BitmapFactory.Options(); 
		  options.inSampleSize = SAMPLE_SIZE;     // SAMPLE_SIZE = 2 
		 
		  Bitmap tempBitmap = null; 
		  Bitmap bm = null; 
		  try { 
		    tempBitmap = (Bitmap) data.getExtras().get("data"); 
		    bm = tempBitmap; 
		 
		    Log.v("ManageImage-hero", "the data.getData seems to be valid"); 
		     
		    FileOutputStream out = new FileOutputStream(outputFileUri.getPath()); 
		    tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); 
		  } catch (NullPointerException ex) { 
		    Log.v("ManageImage-other", "another phone type"); 
		    bm = otherImageProcessing(options); 
		  } catch(Exception e) { 
		    Log.e("ManageImage-setupImage", "problem setting up the image", e); 
		  } 
		 
		  return bm; 
		}

	private Bitmap otherImageProcessing(BitmapFactory.Options options) {
		Bitmap bm = null;
		Log.d(TAG, "otherImageProcessing");
		try {
			Log.d(TAG, "outputFileUri path->"+outputFileUri.getPath());
			FileInputStream fis = new FileInputStream(outputFileUri.getPath());
			BufferedInputStream bis = new BufferedInputStream(fis);
			bm = BitmapFactory.decodeStream(bis, null, options);

			// cleaning up
			fis.close();
			bis.close();
		} catch (Exception e) {
			Log.e("ManageImage-otherImageProcessing", "Cannot load image", e);
		}

		return bm;
	}

}

package zumma.com.ninegistapp.ui.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zumma.com.ninegistapp.ParseConstants;
import zumma.com.ninegistapp.R;
import zumma.com.ninegistapp.utils.FileHelper;

public class SelectPicture extends Activity implements View.OnClickListener {

    private static final String TAG = SelectPicture.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri outputCropUri;
    private Uri cameraUri;
    private boolean isCamera;
    private ImageView imageView;
    private ImageView testView;
    private File imageFile;
    private boolean imageSelected = false;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_picture);
        firebase = new Firebase(ParseConstants.FIREBASE_URL).child("9Gist").child("BvYGVZYoPb").child("basicInfo").child("picture");
        findViewById(R.id.picure_button_ok).setOnClickListener(this);
        findViewById(R.id.picture_button_cancel).setOnClickListener(this);
        findViewById(R.id.pic_image_button).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.picture_image);
        testView = (ImageView) findViewById(R.id.test_view);
        setUpImageFile();
        initImage();
    }

    private void setUpImageFile(){
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "9NineGist" + File.separator);
        if(root.mkdirs() || root.exists()){
            //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + "picture" + ".jpg";
            imageFile = new File(root, imageFileName);
        }
    }

    private void setUpPicture(){
        firebase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, dataSnapshot.toString() + " -onChildAdded");
                String imageString = dataSnapshot.getValue().toString();
                byte[] decodedImage = Base64.decode(imageString, Base64.DEFAULT);
                Bitmap byteImage = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                if (byteImage != null) {
                    //Uri.fromFile(new File(getCacheDir(), "cropped"))
                    testView.setImageDrawable(null);
                    testView.setImageBitmap(byteImage);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pic_image_button:
                openImageIntent();
                break;
            case R.id.picture_button_cancel:
                cancelUpdate();
                break;
            case R.id.picure_button_ok:
                updatePhoto();
                break;
            default:
                Toast.makeText(this, "Default", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void cancelUpdate(){
        Intent intent = new Intent(SelectPicture.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openImageIntent() {

        cameraUri = Uri.fromFile(imageFile);
        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        //Get All Packages/Apps that responds to camera intents
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        //For each returned as ResolveInfo:
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void updatePhoto(){
        if(imageSelected){
//            ParseUser current = ParseUser.getCurrentUser();
            byte[] ourByte = FileHelper.getByteArrayFromFile(this, outputCropUri);
            String imageFile = Base64.encodeToString(ourByte, Base64.DEFAULT);
            firebase.setValue(imageFile, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        Log.d(TAG, "Data could not be saved. " + firebaseError.getMessage());
                    } else {
                        Log.d(TAG, "Inside else of onComplete ");
                        setUpPicture();
                    }
                }
            });
//            ParseFile file = new ParseFile("profileImage.jpg", ourByte);
//            current.put("profileImage", file);
//            current.saveInBackground(new SaveCallback(){
//                @Override
//                public void done(ParseException e){
//                    if (e == null) {
//                        Intent intent = new Intent(SelectPicture.this, HomeActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(SelectPicture.this, "Error uploading. Try again please.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
        else{
            Toast.makeText(this, "Select an image or click cancel to proceed with no image.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        outputCropUri = Uri.fromFile(imageFile); //Uri.fromFile(new File(getCacheDir(), "cropped"));
        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if(resultCode == RESULT_OK) {
                    if (data == null) {
                        isCamera = true;
                    }
                    else {
                        final String action = data.getAction();
                        if (action == null) {
                            isCamera = false;
                        }
                        else {
                            isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        }
                    }
                    if (isCamera) {
                        new Crop(cameraUri).output(outputCropUri).asSquare().start(this);
                    }
                    else {
                        new Crop(data.getData()).output(outputCropUri).asSquare().start(this);
                    }
                }
                break;
            case Crop.REQUEST_CROP:
                loadImage();
                break;
            default:
                break;
        }
    }

    private void initImage(){
        outputCropUri = Uri.fromFile(imageFile);
        if(imageFile.exists()){
            imageView.setImageDrawable(null);
            imageView.setImageURI(outputCropUri);
        }
    }

    private void loadImage(){
//        Picasso.with(SelectPicture.this)
//                .load(outputCropUri)
//                .placeholder(R.drawable.ic_contact_picture_180_holo_light)
//                .fit()
//                .centerCrop()
//                .into(imageView);
        imageView.setImageDrawable(null);
        imageView.setImageURI(outputCropUri);
        imageSelected = true;
        Log.d(TAG, getContentResolver().getType(outputCropUri)+" is it not null?");
    }
}

package ch.beerpro.presentation.details.createrating;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.GeoPoint;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.presentation.details.DetailsActivity;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class CreateRatingActivity extends AppCompatActivity implements AddPlaceFragment.OnFragmentInteractionListener {

    public static final String ITEM = "item";
    public static final String RATING = "rating";
    private static final String TAG = "CreateRatingActivity";

    private static final String PLACES_API_KEY = "AIzaSyAkTuWEyt4zP0aR-IZIWnvDWBm3cFaCjhc";

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1337;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.addRatingBar)
    RatingBar addRatingBar;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.avatar)
    ImageView avatar;

    @BindView(R.id.ratingText)
    EditText ratingText;

    @BindView(R.id.photoExplanation)
    TextView photoExplanation;




    private CreateRatingViewModel model;
    private String placeName = "";
    private LatLng latLng = new LatLng(90, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        Nammu.init(this);

        //Initialize Places SDK
        Places.initialize(getApplicationContext(), PLACES_API_KEY);
        PlacesClient placesClient = Places.createClient(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activity_rating));

        Beer item = (Beer) getIntent().getExtras().getSerializable(ITEM);
        float rating = getIntent().getExtras().getFloat(RATING);

        model = ViewModelProviders.of(this).get(CreateRatingViewModel.class);
        model.setItem(item);

        addRatingBar.setRating(rating);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AddPlaceFragment fragment = new AddPlaceFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        int permissionCheck =
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                }

                @Override
                public void permissionRefused() {
                }
            });
        }

        EasyImage.configuration(this).setImagesFolderName("BeerPro");

        photo.setOnClickListener(view -> {
            EasyImage.openChooserWithDocuments(CreateRatingActivity.this, "", 0);
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Uri photoUrl = user.getPhotoUrl();
            GlideApp.with(this).load(photoUrl).apply(new RequestOptions().circleCrop()).into(avatar);
        }
        if (model.getPhoto() != null) {
            photo.setImageURI(model.getPhoto());
            photoExplanation.setText(null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                Log.i("CreateRatingActivity", imageFiles.toString());

                UCrop.Options options = new UCrop.Options() {
                    {
                        setToolbarTitle("Foto zuschneiden");
                        setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                        setActiveWidgetColor(getResources().getColor(R.color.colorAccent));
                        setCropFrameColor(getResources().getColor(R.color.colorAccent));
                        setCropGridColor(getResources().getColor(R.color.colorAccent));
                        setDimmedLayerColor(getResources().getColor(R.color.windowBackgroundColor));
                        setHideBottomControls(true);
                    }
                };
                // TODO store the image name in the viewmodel or instance state!
                UCrop.of(Uri.fromFile(imageFiles.get(0)),
                        Uri.fromFile(new File(getCacheDir(), "image_" + UUID.randomUUID().toString())))
                        .withAspectRatio(1, 1).withMaxResultSize(1024, 1024).withOptions(options)
                        .start(CreateRatingActivity.this);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CreateRatingActivity.this);
                    if (photoFile != null)
                        photoFile.delete();
                }
            }
        });

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        } else if (requestCode == AUTOCOMPLETE_REQUEST_CODE){
            if(resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                this.latLng = place.getLatLng();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ShowPlaceFragment fragment = ShowPlaceFragment.newInstance(place.getName());
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                this.placeName = place.getName();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR){
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED){
                Log.i(TAG, "Place selection cancelled.");
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            model.setPhoto(resultUri);
            photo.setImageURI(resultUri);
            photoExplanation.setText(null);
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rating_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                saveRating();
                return true;
            case android.R.id.home:
                if (getParentActivityIntent() == null) {
                    onBackPressed();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRating() {
        float rating = addRatingBar.getRating();
        String comment = ratingText.getText().toString();

        GeoPoint geoPoint = new GeoPoint(this.latLng.latitude, this.latLng.longitude);

        Log.e(TAG, "name=" + this.placeName + ", lat=" + this.latLng.latitude + ", lng=" + this.latLng.longitude);

        // TODO show a spinner!
        // TODO return the new rating to update the new average immediately
        model.saveRating(model.getItem(), rating, comment, placeName, geoPoint, model.getPhoto())
                .addOnSuccessListener(task -> onBackPressed())
                .addOnFailureListener(error -> Log.e(TAG, "Could not save rating", error));
    }

    @Override
    public void displayPlaceSelector() {

        //Show place picker
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public Intent getParentActivityIntent(){
        Intent intent = super.getParentActivityIntent();
        intent.putExtra(DetailsActivity.ITEM_ID, ((Beer) getIntent().getExtras().getSerializable(ITEM)).getId());
        return intent;
    }
}


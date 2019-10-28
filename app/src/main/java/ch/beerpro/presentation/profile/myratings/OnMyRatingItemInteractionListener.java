package ch.beerpro.presentation.profile.myratings;

import com.google.firebase.firestore.GeoPoint;

import ch.beerpro.domain.models.Rating;

public interface OnMyRatingItemInteractionListener {

    void onMoreClickedListener(Rating item);

    void onWishClickedListener(Rating item);

    void onMapClickedListener(GeoPoint geoPoint);
}

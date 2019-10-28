package ch.beerpro.presentation.details;

import com.google.firebase.firestore.GeoPoint;

import ch.beerpro.domain.models.Rating;

interface OnRatingChangedListener {
    void onRatingLikedListener(Rating rating);
    void OnShowOnMapClicked(Rating rating);

}

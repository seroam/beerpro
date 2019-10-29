package ch.beerpro.presentation.details;

import ch.beerpro.domain.models.Rating;

interface OnRatingChangedListener {
    void onRatingLikedListener(Rating rating);
    void OnShowOnMapClicked(Rating rating);

}

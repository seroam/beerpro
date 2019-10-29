## Changes

### Implemented feature: Beurteilung mit Zusatzinformationen (5P)

In addition to an overall rating, users can now also rate their beers on Bitterness. They can do this when adding a new rating by clicking the stars on any beer's detail screen and then selecting the desired level in half-star increments (from 0-5).


Additionally, a place can also be specified in the rating. To do this, when creating a rating, simply tap 'Ort hinzufügen' and start typing a name.


Clicking a place's name or the place icon in a rating will open a map with a marker on the given location.


These two additions will then be shown on the ratings in Bewertungen, Meine Bewertungen and on the beer detail screens.



### Bugfix
Fixed a bug where hitting the back arrow in the top left in the CreateRatingActivity caused the app to crash.

The cause was that DetailsActivity uses the Intent in its onCreate method which is not properly passed back with `NavUtils.navigateUpFromSameTask(this)`.

The fix was to override `getParentActivityIntent` in `CreateRatingActivity.java`:

```@Override
    public Intent getParentActivityIntent(){
        Intent intent = super.getParentActivityIntent();
        intent.putExtra(DetailsActivity.ITEM_ID, ((Beer) getIntent().getExtras().getSerializable(ITEM)).getId());
        return intent;
    }```

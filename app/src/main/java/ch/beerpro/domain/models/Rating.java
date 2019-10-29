package ch.beerpro.domain.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.Map;

public class Rating implements Entity {
    public static final String COLLECTION = "ratings";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String beerName;
    private String userId;
    private String userName;
    private String userPhoto;
    private String photo;
    private float rating;
    private float bitterness;
    private String comment;
    private String placeName;
    private GeoPoint latLng;

    /**
     * We use a Map instead of an Array to be able to query it.
     *
     * @see <a href="https://firebase.google.com/docs/firestore/solutions/arrays#solution_a_map_of_values"/>
     */
    private Map<String, Boolean> likes;
    private Date creationDate;

    public Rating(String id, String beerId, String beerName, String userId, String userName, String userPhoto, String photo, float rating, float bitterness, String comment, String placeName, GeoPoint latLng, Map<String, Boolean> likes, Date creationDate) {
        this.id = id;
        this.beerId = beerId;
        this.beerName = beerName;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.photo = photo;
        this.rating = rating;
        this.bitterness = bitterness;
        this.comment = comment;
        this.placeName = placeName;
        this.latLng = latLng;
        this.likes = likes;
        this.creationDate = creationDate;
    }

    public Rating() {
    }

    public String getId() {
        return this.id;
    }

    public String getBeerId() {
        return this.beerId;
    }

    public String getBeerName() {
        return this.beerName;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPhoto() {
        return this.userPhoto;
    }

    public String getPhoto() {
        return this.photo;
    }

    public float getRating() {
        return this.rating;
    }

    public float getBitterness(){ return this.bitterness; }

    public String getComment() {
        return this.comment;
    }

    public String getPlaceName(){ return this.placeName; }

    public GeoPoint getLatLng(){ return this.latLng; }

    public Map<String, Boolean> getLikes() {
        return this.likes;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setBitterness(float bitterness) { this.bitterness = bitterness; }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPlaceName(String placeName){
        this.placeName = placeName;
    }

    public void setLatLng(GeoPoint latLng) { this.latLng = latLng; }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Rating)) return false;
        final Rating other = (Rating) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$beerId = this.getBeerId();
        final Object other$beerId = other.getBeerId();
        if (this$beerId == null ? other$beerId != null : !this$beerId.equals(other$beerId))
            return false;
        final Object this$beerName = this.getBeerName();
        final Object other$beerName = other.getBeerName();
        if (this$beerName == null ? other$beerName != null : !this$beerName.equals(other$beerName))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId))
            return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName))
            return false;
        final Object this$userPhoto = this.getUserPhoto();
        final Object other$userPhoto = other.getUserPhoto();
        if (this$userPhoto == null ? other$userPhoto != null : !this$userPhoto.equals(other$userPhoto))
            return false;
        final Object this$photo = this.getPhoto();
        final Object other$photo = other.getPhoto();
        if (this$photo == null ? other$photo != null : !this$photo.equals(other$photo))
            return false;
        if (Float.compare(this.getRating(), other.getRating()) != 0) return false;
        if (Float.compare(this.getBitterness(), other.getBitterness()) != 0) return false;
        final Object this$comment = this.getComment();
        final Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment))
            return false;
        final Object this$placeName = this.getPlaceName();
        final Object other$placeName = other.getPlaceName();
        if (this$placeName == null ? other$placeName != null : !this$placeName.equals(other$placeName))
            return false;
        final Object this$latLng = this.getLatLng();
        final Object other$latLng = other.getLatLng();
        if (this$latLng == null ? other$latLng != null : !this$latLng.equals(other$latLng))
            return false;
        final Object this$likes = this.getLikes();
        final Object other$likes = other.getLikes();
        if (this$likes == null ? other$likes != null : !this$likes.equals(other$likes))
            return false;
        final Object this$creationDate = this.getCreationDate();
        final Object other$creationDate = other.getCreationDate();
        return this$creationDate == null ? other$creationDate == null : this$creationDate.equals(other$creationDate);
    }

    private boolean canEqual(final Object other) {
        return other instanceof Rating;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $beerId = this.getBeerId();
        result = result * PRIME + ($beerId == null ? 43 : $beerId.hashCode());
        final Object $beerName = this.getBeerName();
        result = result * PRIME + ($beerName == null ? 43 : $beerName.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $userPhoto = this.getUserPhoto();
        result = result * PRIME + ($userPhoto == null ? 43 : $userPhoto.hashCode());
        final Object $photo = this.getPhoto();
        result = result * PRIME + ($photo == null ? 43 : $photo.hashCode());
        result = result * PRIME + Float.floatToIntBits(this.getRating());
        result = result * PRIME + Float.floatToIntBits(this.getBitterness());
        final Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        final Object $placeName = this.getPlaceName();
        result = result * PRIME + ($placeName == null ? 43 : $placeName.hashCode());
        final Object $latLng = this.getLatLng();
        result = result * PRIME + ($latLng == null ? 43 : $latLng.hashCode());
        final Object $likes = this.getLikes();
        result = result * PRIME + ($likes == null ? 43 : $likes.hashCode());
        final Object $creationDate = this.getCreationDate();
        result = result * PRIME + ($creationDate == null ? 43 : $creationDate.hashCode());
        return result;
    }

    @NonNull
    public String toString() {
        return "Rating(id=" + this.getId() + ", beerId=" + this.getBeerId() + ", beerName=" + this.getBeerName() + ", userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", userPhoto=" + this.getUserPhoto() + ", photo=" + this.getPhoto() + ", rating=" + this.getRating() + ", bitterness=" + this.getBitterness() + ", comment=" + this.getComment() + ", placeName=" + this.getPlaceName() + ", latLng=" + this.getLatLng().toString() + ", likes=" + this.getLikes() + ", creationDate=" + this.getCreationDate() + ")";
    }
}

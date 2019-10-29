package ch.beerpro.presentation.details;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.domain.models.Rating;
import ch.beerpro.presentation.utils.EntityDiffItemCallback;




public class RatingsRecyclerViewAdapter extends ListAdapter<Rating, RatingsRecyclerViewAdapter.ViewHolder> {

    private static final EntityDiffItemCallback<Rating> DIFF_CALLBACK = new EntityDiffItemCallback<>();

    private final OnRatingChangedListener listener;
    private FirebaseUser user;

    public RatingsRecyclerViewAdapter(OnRatingChangedListener listener, FirebaseUser user) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_details_ratings_listentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment)
        TextView comment;

        @BindView(R.id.avatar)
        ImageView avatar;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.ratingBarBitterness)
        RatingBar ratingBarBitterness;

        @BindView(R.id.authorName)
        TextView authorName;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.numLikes)
        TextView numLikes;

        @BindView(R.id.like)
        ImageView like;

        @BindView(R.id.pin)
        ImageView pin;

        @BindView(R.id.place_name)
        TextView placeName;

        @BindView(R.id.photo)
        ImageView photo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(Rating item, OnRatingChangedListener listener) {
            comment.setText(item.getComment());

            ratingBar.setNumStars(5);
            ratingBar.setRating(item.getRating());
            ratingBarBitterness.setRating(item.getBitterness());
            String formattedDate =
                    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(item.getCreationDate());
            date.setText(formattedDate);

            if (item.getPhoto() != null) {
                GlideApp.with(itemView).load(item.getPhoto()).into(photo);
                photo.setVisibility(View.VISIBLE);
            } else {
                GlideApp.with(itemView).clear(photo);
                photo.setVisibility(View.GONE);
            }

            authorName.setText(item.getUserName());
            GlideApp.with(itemView).load(item.getUserPhoto()).apply(new RequestOptions().circleCrop()).into(avatar);

            numLikes.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.getLikes().size()));
            if (item.getLikes().containsKey(user.getUid())) {
                like.setColorFilter(itemView.getResources().getColor(R.color.colorPrimary));
            } else {
                like.setColorFilter(itemView.getResources().getColor(android.R.color.darker_gray));
            }
            if (listener != null) {
                like.setOnClickListener(v -> listener.onRatingLikedListener(item));
                pin.setOnClickListener(v -> listener.OnShowOnMapClicked(item));
                placeName.setOnClickListener(v -> listener.OnShowOnMapClicked(item));
            }

            placeName.setText((item.getPlaceName()));
        }
    }
}

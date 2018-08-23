package com.sawant.recycler_multi_viewtype;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.sawant.recycler_multi_viewtype.Model.AUDIO_TYPE;
import static com.sawant.recycler_multi_viewtype.Model.IMAGE_TYPE;
import static com.sawant.recycler_multi_viewtype.Model.TEXT_TYPE;

public class MultiViewRecyclerAdapter extends RecyclerView.Adapter {


    private final Context context;
    private final ArrayList<Model> dataList;
    private final int totalTypes;
    private MediaPlayer mPlayer;
    private boolean fabStateVolume = false;


    public MultiViewRecyclerAdapter(Context context, ArrayList<Model> dataList) {
        this.context = context;
        this.dataList = dataList;
        totalTypes = dataList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TEXT_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);

            case Model.IMAGE_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.image_type, parent, false);
                return new ImageTypeViewHolder(view);
            case Model.AUDIO_TYPE:
                view = LayoutInflater.from(context).inflate(R.layout.audio_type, parent, false);
                return new AudioTypeViewHolder(view);
        }


        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList != null && dataList.get(position) != null) {
            switch (dataList.get(position).type) {
                case TEXT_TYPE:
                    return TEXT_TYPE;
                case IMAGE_TYPE:
                    return IMAGE_TYPE;
                case AUDIO_TYPE:
                    return AUDIO_TYPE;
                default:
                    return -1;
            }
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        final Model model = dataList.get(position);
        if (model != null) {
            switch (model.type) {
                case TEXT_TYPE:
                    ((TextTypeViewHolder) holder).textData.setText(model.text);
                    break;
                case IMAGE_TYPE:
                    ((ImageTypeViewHolder) holder).txtType.setText(model.text);
                    ((ImageTypeViewHolder) holder).image.setImageResource(model.data);
                    break;

                case AUDIO_TYPE:
                    ((AudioTypeViewHolder) holder).textView.setText(model.text);
                    ((AudioTypeViewHolder) holder).actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (fabStateVolume) {
                                if (mPlayer.isPlaying()) {
                                    mPlayer.stop();
                                }
                                ((AudioTypeViewHolder) holder).actionButton.setImageResource(android.R.drawable.ic_media_play);
                                fabStateVolume = false;
                            } else {
                                mPlayer = MediaPlayer.create(context, model.data);
                                mPlayer.setLooping(true);
                                mPlayer.start();
                                ((AudioTypeViewHolder) holder).actionButton.setImageResource(android.R.drawable.ic_media_pause);
                                fabStateVolume = true;

                            }
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TextTypeViewHolder extends RecyclerView.ViewHolder {
        TextView textData;
        CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            textData = (TextView) itemView.findViewById(R.id.type);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtType;
        private final ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.background);
        }
    }

    public class AudioTypeViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        FloatingActionButton actionButton;

        public AudioTypeViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.type);
            this.actionButton = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }
    }
}

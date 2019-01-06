package com.costar.talkwithidol.ui.fragments.channeldetailactivity.mvp;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

;
;

@AutoValue
public abstract class ChannelSubsParams {


    @SerializedName("subscribed_channels")
    public abstract JsonArray subscribed_channels();

    @SerializedName("unsubscribed_channels")
    public abstract JsonArray unsubscribed_channels();

    @SerializedName("customer_id")
    public abstract String customer_id();




    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder subscribed_channels(JsonArray jsonArray);

        public abstract Builder unsubscribed_channels(JsonArray jsonArray);

        public abstract Builder customer_id(String customer_id);


        public abstract ChannelSubsParams build();
    }

    public static Builder builder() {
        return new AutoValue_ChannelSubsParams.Builder();
    }


    public static TypeAdapter<ChannelSubsParams> typeAdapter(Gson gson) {
        return new AutoValue_ChannelSubsParams.GsonTypeAdapter(gson);
    }
}

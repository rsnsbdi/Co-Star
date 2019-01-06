package com.costar.talkwithidol.app.network.models.UpdatePreferenceResponse;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class UpdatePreferenceParams  {



    @SerializedName("email_notification")
    public abstract JsonObject email_notification();

    @SerializedName("event_notification")
    public abstract JsonObject event_notification();





    @AutoValue.Builder
    public abstract static class Builder{

        public abstract Builder email_notification(JsonObject email_notification);

        public abstract Builder event_notification(JsonObject event_notification);


        public abstract UpdatePreferenceParams build();
    }

    public static UpdatePreferenceParams.Builder builder() {
        return new AutoValue_UpdatePreferenceParams.Builder();
    }


    public static TypeAdapter<UpdatePreferenceParams> typeAdapter(Gson gson) {
        return new AutoValue_UpdatePreferenceParams.GsonTypeAdapter(gson);
    }
}

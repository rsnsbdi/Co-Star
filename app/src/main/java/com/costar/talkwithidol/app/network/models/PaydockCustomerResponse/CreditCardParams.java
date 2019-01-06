package com.costar.talkwithidol.app.network.models.PaydockCustomerResponse;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

;
;

@AutoValue
public abstract class CreditCardParams {


    @SerializedName("payment_source")
    public abstract JsonObject payment_source();

    @SerializedName("reference")
    public abstract String reference();
    @SerializedName("first_name")
    public abstract String first_name();
    @SerializedName("last_name")
    public abstract String last_name();
    @SerializedName("email")
    public abstract String email();
    @SerializedName("phone")
    public abstract String phone();


    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder payment_source(JsonObject dob);
        public abstract Builder reference (String ref);
        public abstract Builder first_name(String first_name);
        public abstract Builder last_name(String last_name);
        public abstract Builder email(String email);
        public abstract Builder phone(String phone);

        public abstract CreditCardParams build();
    }

    public static Builder builder() {
        return new AutoValue_CreditCardParams.Builder();
    }


    public static TypeAdapter<CreditCardParams> typeAdapter(Gson gson) {
        return new AutoValue_CreditCardParams.GsonTypeAdapter(gson);
    }
}

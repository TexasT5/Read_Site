package Model;

import com.google.gson.annotations.SerializedName;

public class Price{
    @SerializedName("sellingPrice")
    DefaultPrice sellingPrice;
    @SerializedName("discountedPrice")
    DefaultPrice discountedPrice;
    @SerializedName("originalPrice")
    DefaultPrice originalPrice;
}

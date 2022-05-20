package Model.Trendyol;

import com.google.gson.annotations.SerializedName;

public class Price{
    @SerializedName("sellingPrice")
    public DefaultPrice sellingPrice;
    @SerializedName("discountedPrice")
    public DefaultPrice discountedPrice;
    @SerializedName("originalPrice")
    public DefaultPrice originalPrice;
}

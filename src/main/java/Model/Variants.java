package Model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class Variants {
    @SerializedName("fulfilmentType")
    public String fulfilmentType;
    @SerializedName("itemNumber")
    public int itemNumber;
    @SerializedName("attributeValue")
    public String attributeValue;
    @SerializedName("rushDeliveryMerchantListingExist")
    public boolean rushDeliveryMerchantListingExist;
    @SerializedName("fastDeliveryOptions")
    public List<Object> fastDeliveryOptions;
    @SerializedName("listingId")
    public String listingId;
    @SerializedName("sellable")
    public boolean sellable;
    @SerializedName("attributeId")
    public int attributeId;
    @SerializedName("isWinner")
    public boolean isWinner;
    @SerializedName("attributeType")
    public String attributeType;
    @SerializedName("lowerPriceMerchantListingExist")
    public boolean lowerPriceMerchantListingExist;
    @SerializedName("hasCollectable")
    public boolean hasCollectable;
    @SerializedName("attributeName")
    public String attributeName;
    @SerializedName("attributeBeautifiedValue")
    public String attributeBeautifiedValue;
    @SerializedName("unitInfo")
    public UnitInfo unitInfo;
    @SerializedName("stock")
    public Object stock;
    @SerializedName("stamps")
    public List<Stamps> stamps;
    @SerializedName("barcode")
    public String barcode;
    @SerializedName("discountedPriceInfo")
    public String discountedPriceInfo;
    @SerializedName("availableForClaim")
    public boolean availableForClaim;

}

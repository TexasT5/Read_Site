package Model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class Variants {
    @SerializedName("fulfilmentType")
    String fulfilmentType;
    @SerializedName("itemNumber")
    int itemNumber;
    @SerializedName("attributeValue")
    String attributeValue;
    @SerializedName("rushDeliveryMerchantListingExist")
    boolean rushDeliveryMerchantListingExist;
    @SerializedName("fastDeliveryOptions")
    List<Object> fastDeliveryOptions;
    @SerializedName("listingId")
    String listingId;
    @SerializedName("sellable")
    boolean sellable;
    @SerializedName("attributeId")
    int attributeId;
    @SerializedName("isWinner")
    boolean isWinner;
    @SerializedName("attributeType")
    String attributeType;
    @SerializedName("lowerPriceMerchantListingExist")
    boolean lowerPriceMerchantListingExist;
    @SerializedName("hasCollectable")
    boolean hasCollectable;
    @SerializedName("attributeName")
    String attributeName;
    @SerializedName("attributeBeautifiedValue")
    String attributeBeautifiedValue;
    @SerializedName("unitInfo")
    UnitInfo unitInfo;
    @SerializedName("stock")
    Object stock;
    @SerializedName("stamps")
    List<Stamps> stamps;
    @SerializedName("barcode")
    public String barcode;
    @SerializedName("discountedPriceInfo")
    String discountedPriceInfo;
    @SerializedName("availableForClaim")
    boolean availableForClaim;

}

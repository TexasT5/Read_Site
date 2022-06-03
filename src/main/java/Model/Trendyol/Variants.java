package Model.Trendyol;

import com.google.gson.annotations.SerializedName;

import javax.management.openmbean.OpenMBeanConstructorInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Variants {
    @SerializedName("fulfilmentType")
    public String fulfilmentType = "";
    @SerializedName("itemNumber")
    public int itemNumber = -1;
    @SerializedName("attributeValue")
    public String attributeValue = "";
    @SerializedName("rushDeliveryMerchantListingExist")
    public boolean rushDeliveryMerchantListingExist = false;
    @SerializedName("fastDeliveryOptions")
    public List<Object> fastDeliveryOptions = new ArrayList<Object>(Arrays.asList(new Object()));
    @SerializedName("listingId")
    public String listingId = "";
    @SerializedName("sellable")
    public boolean sellable = false;
    @SerializedName("attributeId")
    public int attributeId = 0;
    @SerializedName("isWinner")
    public boolean isWinner = false;
    @SerializedName("attributeType")
    public String attributeType = "";
    @SerializedName("lowerPriceMerchantListingExist")
    public boolean lowerPriceMerchantListingExist = false;
    @SerializedName("hasCollectable")
    public boolean hasCollectable = false;
    @SerializedName("attributeName")
    public String attributeName = "";
    @SerializedName("attributeBeautifiedValue")
    public String attributeBeautifiedValue = "";
    @SerializedName("unitInfo")
    public UnitInfo unitInfo;
    @SerializedName("stock")
    public Object stock;
    @SerializedName("stamps")
    public List<Stamps> stamps;
    @SerializedName("barcode")
    public String barcode = "";
    @SerializedName("discountedPriceInfo")
    public String discountedPriceInfo = "";
    @SerializedName("availableForClaim")
    public boolean availableForClaim = false;

}

package Model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.List;

public class Product {
    @SerializedName("businessUnit")
    public String businessUnit;
    @SerializedName("rushDeliveryMerchantListingExist")
    public boolean rushDeliveryMerchantListingExist;
    @SerializedName("brandCategoryBanners")
    public List<Type> brandCategoryBanners;
    @SerializedName("productGroupId")
    public int productGroupId;
    @SerializedName("variants")
    public List<Variants> variants;
}

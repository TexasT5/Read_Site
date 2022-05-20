package Model.Trendyol;

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
    @SerializedName("ratingScore")
    public RatingScore ratingScore;
    @SerializedName("otherMerchantVariants")
    public List<Object> otherMerchantVariants;
    @SerializedName("price")
    public Price price;
    public WalletRebate walletRebate;
    public List<Object> alternativeVariants;
    public boolean showVariants;
    public int id;
    public boolean scheduledDelivery;
    public Brand brand;
    public boolean showSexualContent;
    public List<Object> landings;
    public boolean hasStock;
    public String nameWithProductCode;
    public List<String> images;
    public int cargoRemainingDays;
    public boolean isMarketplace;
    public boolean isRunningOut;
    public Merchant merchant;
    public String questionsUrl;
    public int tax;
    public List<ContentDescriptions> contentDescriptions;
    public List<AllVariants> allVariants;
    public String name;
    public String uxLayout;
    public Campaign campaign;
    public OriginalCategory originalCategory;
    public boolean showValidFlashSales;
    public String sizeChartUrl;
    public String color;
    public Gender gender;
    public boolean showStarredAttributes;
    public boolean sellerQuestionEnabled;
    public boolean isArtWork;
    public String description;
    public boolean isBasketDiscount;
    public boolean isDigitalGood;
    public DeliveryInformation deliveryInformation;
    public String installmentText;
    public String reviewsUrl;
    public boolean isVasEnabled;
    public int maxInstallment;
    public boolean isSellable;
    public CrossPromotionAward crossPromotionAward;
    public boolean hasHtmlContent;
    public String url;
    public boolean isFreeCargo;
    public String productCode;
    public boolean sizeExpectationAvailable;
    public List<Object> productStamps;
    public boolean lowerPriceMerchantListingExist;
    public boolean showExpiredFlashSales;
    public Object categoryTopRankings;
    public List<Attributes> attributes;
    public String installmentBanner;
    public Category category;
    public List<Object> buyMorePayLessPromotions;
    public long favoriteCount;
}

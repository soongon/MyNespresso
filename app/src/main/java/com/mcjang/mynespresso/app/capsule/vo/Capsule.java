package com.mcjang.mynespresso.app.capsule.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mcjan on 2016-11-30.
 */

public class Capsule implements Serializable {

    private String catalogId;
    private String formatedPrice;
    private String currencyIsoCode;
    private boolean isInStock;
    private boolean isOrderable;
    private String maxQuantity;
    private String name;
    private String nesOAInternationalProductId;
    private String nesOAName;
    private int priceValue;
    private int salesMultiple;
    private List<String> supportedTechnologyCodes;
    private String urlDetail;
    private String alternatePriceWithCurrency;
    private boolean isHighlight;
    private Media mediaHighlightQuickOrderBackground;
    private Media mediaQuickOrder;
    private String urlQuickView;
    private boolean maxQuantityReached;
    private int quantityInShoppingBag;
    private CapsuleRange capsuleRange;
    private boolean welcomeOffer;
    private String productDataType;
    private String fmtPriceValue;
    private String type;
    private String code;

    private CapsuleDetail capsuleDetail;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getFormatedPrice() {
        return formatedPrice;
    }

    public void setFormatedPrice(String formatedPrice) {
        this.formatedPrice = formatedPrice;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    public boolean isOrderable() {
        return isOrderable;
    }

    public void setOrderable(boolean orderable) {
        isOrderable = orderable;
    }

    public String getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(String maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNesOAInternationalProductId() {
        return nesOAInternationalProductId;
    }

    public void setNesOAInternationalProductId(String nesOAInternationalProductId) {
        this.nesOAInternationalProductId = nesOAInternationalProductId;
    }

    public String getNesOAName() {
        return nesOAName;
    }

    public void setNesOAName(String nesOAName) {
        this.nesOAName = nesOAName;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(int priceValue) {
        this.priceValue = priceValue;
    }

    public int getSalesMultiple() {
        return salesMultiple;
    }

    public void setSalesMultiple(int salesMultiple) {
        this.salesMultiple = salesMultiple;
    }

    public List<String> getSupportedTechnologyCodes() {
        return supportedTechnologyCodes;
    }

    public void setSupportedTechnologyCodes(List<String> supportedTechnologyCodes) {
        this.supportedTechnologyCodes = supportedTechnologyCodes;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public String getAlternatePriceWithCurrency() {
        return alternatePriceWithCurrency;
    }

    public void setAlternatePriceWithCurrency(String alternatePriceWithCurrency) {
        this.alternatePriceWithCurrency = alternatePriceWithCurrency;
    }

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    public Media getMediaHighlightQuickOrderBackground() {
        return mediaHighlightQuickOrderBackground;
    }

    public void setMediaHighlightQuickOrderBackground(Media mediaHighlightQuickOrderBackground) {
        this.mediaHighlightQuickOrderBackground = mediaHighlightQuickOrderBackground;
    }

    public Media getMediaQuickOrder() {
        return mediaQuickOrder;
    }

    public void setMediaQuickOrder(Media mediaQuickOrder) {
        this.mediaQuickOrder = mediaQuickOrder;
    }

    public String getUrlQuickView() {
        return urlQuickView;
    }

    public void setUrlQuickView(String urlQuickView) {
        this.urlQuickView = urlQuickView;
    }

    public boolean isMaxQuantityReached() {
        return maxQuantityReached;
    }

    public void setMaxQuantityReached(boolean maxQuantityReached) {
        this.maxQuantityReached = maxQuantityReached;
    }

    public int getQuantityInShoppingBag() {
        return quantityInShoppingBag;
    }

    public void setQuantityInShoppingBag(int quantityInShoppingBag) {
        this.quantityInShoppingBag = quantityInShoppingBag;
    }

    public CapsuleRange getCapsuleRange() {
        return capsuleRange;
    }

    public void setCapsuleRange(CapsuleRange capsuleRange) {
        this.capsuleRange = capsuleRange;
    }

    public boolean isWelcomeOffer() {
        return welcomeOffer;
    }

    public void setWelcomeOffer(boolean welcomeOffer) {
        this.welcomeOffer = welcomeOffer;
    }

    public String getProductDataType() {
        return productDataType;
    }

    public void setProductDataType(String productDataType) {
        this.productDataType = productDataType;
    }

    public String getFmtPriceValue() {
        return fmtPriceValue;
    }

    public void setFmtPriceValue(String fmtPriceValue) {
        this.fmtPriceValue = fmtPriceValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CapsuleDetail getCapsuleDetail() {
        return capsuleDetail;
    }

    public void setCapsuleDetail(CapsuleDetail capsuleDetail) {
        this.capsuleDetail = capsuleDetail;
    }
}

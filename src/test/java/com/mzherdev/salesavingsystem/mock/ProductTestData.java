package com.mzherdev.salesavingsystem.mock;

import com.mzherdev.salesavingsystem.model.Product;

import java.util.Arrays;
import java.util.List;

public class ProductTestData {

    private ProductTestData(){}

    public static final int START_ID = 1;

    public static final int JEANS_PRODUCT_ID = START_ID;
    public static final int T_SHIRT_PRODUCT_ID = JEANS_PRODUCT_ID + 1;
    public static final int PULLOVER_PRODUCT_ID = T_SHIRT_PRODUCT_ID + 1;
    public static final int SCARF_PRODUCT_ID = PULLOVER_PRODUCT_ID + 1;
    public static final int SHORTS_PRODUCT_ID = SCARF_PRODUCT_ID + 1;
    public static final int SUNGLASSES_PRODUCT_ID = SHORTS_PRODUCT_ID + 1;
    public static final int SHOES_PRODUCT_ID = SUNGLASSES_PRODUCT_ID + 1;
    public static final int CUFFLINKS_PRODUCT_ID = SHOES_PRODUCT_ID + 1;
    public static final int COAT_PRODUCT_ID = CUFFLINKS_PRODUCT_ID + 1;
    public static final int BOOTS_PRODUCT_ID = COAT_PRODUCT_ID + 1;

    public static final int UNKNOWN_PRODUCT_ID = 100;

    public static final Product JEANS = new Product(JEANS_PRODUCT_ID, "Jeans", 9.5);
    public static final Product T_SHIRT = new Product(T_SHIRT_PRODUCT_ID, "T-shirt", 5.0);
    public static final Product PULLOVER = new Product(PULLOVER_PRODUCT_ID, "Pullover", 39.99);
    public static final Product SCARF = new Product(SCARF_PRODUCT_ID, "Scarf", 2.4);
    public static final Product SHORTS = new Product(SHORTS_PRODUCT_ID, "Shorts", 2.5);
    public static final Product SUNGLASSES = new Product(SUNGLASSES_PRODUCT_ID, "Sunglasses", 10.0);
    public static final Product SHOES = new Product(SHOES_PRODUCT_ID, "Shoes", 25.0);
    public static final Product CUFFLINKS = new Product(CUFFLINKS_PRODUCT_ID, "Cufflinks", 1.65);
    public static final Product COAT = new Product(COAT_PRODUCT_ID, "Coat", 52.0);
    public static final Product BOOTS = new Product(COAT_PRODUCT_ID, "Boots", 30.5);

    public static final List<Product> ALL = Arrays.asList(JEANS, T_SHIRT, PULLOVER,
            SCARF, SHORTS, SUNGLASSES, SHOES, CUFFLINKS, COAT, BOOTS);
}

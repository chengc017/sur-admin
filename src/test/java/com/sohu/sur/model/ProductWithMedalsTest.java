package com.sohu.sur.model;

import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: guoyong
 * Date: 11-3-23 下午1:37
 */
public class ProductWithMedalsTest {

    @Test
    public void testToJSON() throws Exception {

        Product product = new Product();
        product.setId(new ObjectId());
        product.setName("女人");
        product.setDescription("女人频道");
        product.setCode("123");

        Medal m = new Medal();
        m.setId(new ObjectId());
        m.setName("铜章");
        m.setCode("456");
        m.setForProduct(product.getId());
        m.setDiscount(0.5);
        m.setMinActiveDays(3);
        m.setMaxActiveDays(6);
        List<Medal> medals = new ArrayList<Medal>(1);
        medals.add(m);

        ProductWithMedals pwm = new ProductWithMedals(product, medals);

        System.out.println(pwm.toJSON());

        List<ProductWithMedals> productWithMedalsList = new ArrayList<ProductWithMedals>(1);
        productWithMedalsList.add(pwm);
        System.out.println(ProductWithMedals.toJSON(productWithMedalsList));

    }
}

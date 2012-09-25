package com.sohu.sur.model;

import java.io.Serializable;
import java.util.List;

import com.google.code.morphia.annotations.*;
import org.bson.types.ObjectId;

@Entity(value = "medal_stars", cap = @CappedAt(value = 10485760, count = 6), noClassnameStored = false, slaveOk = false)
public class AllProductMedalStars implements Serializable{

    @Id
    private ObjectId id;

    @Embedded("items")
    private List<ProductMedalStars> productMedalStarsList;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<ProductMedalStars> getProductMedalStarsList() {
        return productMedalStarsList;
    }

    public void setProductMedalStarsList(
            List<ProductMedalStars> productMedalStarsList) {
        this.productMedalStarsList = productMedalStarsList;
    }
}

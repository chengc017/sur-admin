package com.sohu.sur.model;


import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: guoyong
 * Date: 11-3-23 上午11:44
 */
public class ProductWithMedals {

    /**
     * means Product code
     */
    private String id;

    private String name;

    private String description;

    private List<Medal> medals = new ArrayList<Medal>(4);

    public ProductWithMedals(Product product, List<Medal> medalList) {
        this.id = product.getCode();
        this.name = product.getName();
        this.description = product.getDescription();

        for (Medal m : medalList) {
            medals.add(m);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Medal> getMedals() {
        return medals;
    }

    public String toJSON() {
        JSONSerializer serializer = getJSONSerializer();
        serializer.write(this);
        return serializer.toString();
    }

    public static String toJSON(List<ProductWithMedals> productWithMedalsList) {
        JSONSerializer serializer = getJSONSerializer();
        serializer.write(productWithMedalsList);
        return serializer.toString();
    }

    protected static JSONSerializer getJSONSerializer() {

        JSONSerializer serializer = new JSONSerializer();

        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                if (source instanceof Medal && "id".equals(name)) {
                    return false;
                }
                if ("forProduct".equals(name)) {
                    return false;
                }
                if ("minActiveDays".equals(name)) {
                    return false;
                }
                if ("maxActiveDays".equals(name)) {
                    return false;
                }
                if ("createTime".equals(name)) {
                    return false;
                }
                return true;
            }
        };

        serializer.getPropertyFilters().add(filter);

        return serializer;
    }

}

package com.sohu.sur.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * toolbar内容展示
 * 
 * @author xuewuhao
 * 
 */

@Entity("toolbar")
public class Toolbar extends ManagedEntity<ObjectId>{
    
    @Id
    private ObjectId id;
    
    private String content;

    
    public ObjectId getId() {
        return id;
    }

    
    public void setId(ObjectId id) {
        this.id = id;
    }


    
    public String getContent() {
        return content;
    }


    
    public void setContent(String content) {
        this.content = content;
    }

    
    
    

}

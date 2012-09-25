package com.sohu.sur.model;

import java.io.Serializable;

/**
 * @author 韩孝冰
 *
 * @param <ID>
 * 
 * 可被后台管理的实体接口
 */
public abstract class ManagedEntity<ID>  implements Serializable {

	public abstract ID getId();

}

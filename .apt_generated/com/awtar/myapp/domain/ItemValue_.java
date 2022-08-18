package com.awtar.myapp.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItemValue.class)
public abstract class ItemValue_ {

	public static volatile SingularAttribute<ItemValue, Long> availableStockQuantity;
	public static volatile SingularAttribute<ItemValue, Item> item;
	public static volatile SingularAttribute<ItemValue, LocalDate> priceDate;
	public static volatile SingularAttribute<ItemValue, Double> price;
	public static volatile SingularAttribute<ItemValue, Boolean> archivated;
	public static volatile SingularAttribute<ItemValue, Long> id;

	public static final String AVAILABLE_STOCK_QUANTITY = "availableStockQuantity";
	public static final String ITEM = "item";
	public static final String PRICE_DATE = "priceDate";
	public static final String PRICE = "price";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";

}


package com.awtar.myapp.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DonationsReceivedItem.class)
public abstract class DonationsReceivedItem_ {

	public static volatile SingularAttribute<DonationsReceivedItem, LocalDate> date;
	public static volatile SingularAttribute<DonationsReceivedItem, DonationsReceived> donationsReceived;
	public static volatile SingularAttribute<DonationsReceivedItem, Item> item;
	public static volatile SingularAttribute<DonationsReceivedItem, Integer> quantity;
	public static volatile SingularAttribute<DonationsReceivedItem, Boolean> archivated;
	public static volatile SingularAttribute<DonationsReceivedItem, Long> id;

	public static final String DATE = "date";
	public static final String DONATIONS_RECEIVED = "donationsReceived";
	public static final String ITEM = "item";
	public static final String QUANTITY = "quantity";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";

}


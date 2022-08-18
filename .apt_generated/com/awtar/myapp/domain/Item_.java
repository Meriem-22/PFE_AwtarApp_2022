package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.ItemGender;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public abstract class Item_ {

	public static volatile SetAttribute<Item, CompositeItem> composers;
	public static volatile SingularAttribute<Item, ItemGender> gender;
	public static volatile SingularAttribute<Item, Nature> nature;
	public static volatile SetAttribute<Item, CompositeItem> composants;
	public static volatile SetAttribute<Item, DonationItemDetails> donationDetails;
	public static volatile SetAttribute<Item, DonationsReceivedItem> donationsReceiveds;
	public static volatile SingularAttribute<Item, String> urlPhotoContentType;
	public static volatile SingularAttribute<Item, byte[]> urlPhoto;
	public static volatile SetAttribute<Item, ChildStatusItem> childStatuses;
	public static volatile SingularAttribute<Item, Boolean> composed;
	public static volatile SingularAttribute<Item, Boolean> archivated;
	public static volatile SingularAttribute<Item, String> name;
	public static volatile SetAttribute<Item, SchoolLevelItem> schoolLevels;
	public static volatile SingularAttribute<Item, Long> id;
	public static volatile SetAttribute<Item, ItemValue> itemValues;

	public static final String COMPOSERS = "composers";
	public static final String GENDER = "gender";
	public static final String NATURE = "nature";
	public static final String COMPOSANTS = "composants";
	public static final String DONATION_DETAILS = "donationDetails";
	public static final String DONATIONS_RECEIVEDS = "donationsReceiveds";
	public static final String URL_PHOTO_CONTENT_TYPE = "urlPhotoContentType";
	public static final String URL_PHOTO = "urlPhoto";
	public static final String CHILD_STATUSES = "childStatuses";
	public static final String COMPOSED = "composed";
	public static final String ARCHIVATED = "archivated";
	public static final String NAME = "name";
	public static final String SCHOOL_LEVELS = "schoolLevels";
	public static final String ID = "id";
	public static final String ITEM_VALUES = "itemValues";

}


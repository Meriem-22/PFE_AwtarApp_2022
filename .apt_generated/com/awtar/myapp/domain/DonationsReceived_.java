package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DonationsReceived.class)
public abstract class DonationsReceived_ {

	public static volatile SingularAttribute<DonationsReceived, byte[]> urlPhoto;
	public static volatile SingularAttribute<DonationsReceived, Boolean> archivated;
	public static volatile SingularAttribute<DonationsReceived, Long> id;
	public static volatile SingularAttribute<DonationsReceived, AuthorizingOfficer> authorizingOfficer;
	public static volatile SetAttribute<DonationsReceived, DonationsReceivedItem> items;
	public static volatile SingularAttribute<DonationsReceived, String> urlPhotoContentType;

	public static final String URL_PHOTO = "urlPhoto";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";
	public static final String AUTHORIZING_OFFICER = "authorizingOfficer";
	public static final String ITEMS = "items";
	public static final String URL_PHOTO_CONTENT_TYPE = "urlPhotoContentType";

}


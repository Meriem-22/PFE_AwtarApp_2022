package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Gender;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Profile.class)
public abstract class Profile_ {

	public static volatile SingularAttribute<Profile, String> lastName;
	public static volatile SingularAttribute<Profile, Parent> parent;
	public static volatile SingularAttribute<Profile, String> address;
	public static volatile SingularAttribute<Profile, String> urlCinAttachedContentType;
	public static volatile SingularAttribute<Profile, Gender> gender;
	public static volatile SingularAttribute<Profile, City> placeOfResidence;
	public static volatile SingularAttribute<Profile, String> firstNameArabic;
	public static volatile SingularAttribute<Profile, String> cin;
	public static volatile SingularAttribute<Profile, String> lastNameArabic;
	public static volatile SingularAttribute<Profile, LocalDate> dateOfBirth;
	public static volatile SingularAttribute<Profile, String> urlPhotoContentType;
	public static volatile SingularAttribute<Profile, Tutor> tutor;
	public static volatile SingularAttribute<Profile, byte[]> urlPhoto;
	public static volatile SingularAttribute<Profile, String> firstName;
	public static volatile SingularAttribute<Profile, City> birthPlace;
	public static volatile SingularAttribute<Profile, String> phone;
	public static volatile SingularAttribute<Profile, Boolean> archivated;
	public static volatile SingularAttribute<Profile, Long> id;
	public static volatile SingularAttribute<Profile, AuthorizingOfficer> authorizingOfficer;
	public static volatile SetAttribute<Profile, StatusOfHealth> statusOfHealths;
	public static volatile SingularAttribute<Profile, byte[]> urlCinAttached;
	public static volatile SingularAttribute<Profile, String> email;
	public static volatile SingularAttribute<Profile, Child> child;

	public static final String LAST_NAME = "lastName";
	public static final String PARENT = "parent";
	public static final String ADDRESS = "address";
	public static final String URL_CIN_ATTACHED_CONTENT_TYPE = "urlCinAttachedContentType";
	public static final String GENDER = "gender";
	public static final String PLACE_OF_RESIDENCE = "placeOfResidence";
	public static final String FIRST_NAME_ARABIC = "firstNameArabic";
	public static final String CIN = "cin";
	public static final String LAST_NAME_ARABIC = "lastNameArabic";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String URL_PHOTO_CONTENT_TYPE = "urlPhotoContentType";
	public static final String TUTOR = "tutor";
	public static final String URL_PHOTO = "urlPhoto";
	public static final String FIRST_NAME = "firstName";
	public static final String BIRTH_PLACE = "birthPlace";
	public static final String PHONE = "phone";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";
	public static final String AUTHORIZING_OFFICER = "authorizingOfficer";
	public static final String STATUS_OF_HEALTHS = "statusOfHealths";
	public static final String URL_CIN_ATTACHED = "urlCinAttached";
	public static final String EMAIL = "email";
	public static final String CHILD = "child";

}


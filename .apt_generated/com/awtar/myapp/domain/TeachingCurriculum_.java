package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TeachingCurriculum.class)
public abstract class TeachingCurriculum_ {

	public static volatile SingularAttribute<TeachingCurriculum, Status> result;
	public static volatile SingularAttribute<TeachingCurriculum, SchoolLevel> schoolLevel;
	public static volatile SingularAttribute<TeachingCurriculum, Double> annualResult;
	public static volatile SingularAttribute<TeachingCurriculum, String> attachedFileContentType;
	public static volatile SingularAttribute<TeachingCurriculum, Boolean> archivated;
	public static volatile SingularAttribute<TeachingCurriculum, String> beginningYear;
	public static volatile SingularAttribute<TeachingCurriculum, Long> id;
	public static volatile SingularAttribute<TeachingCurriculum, String> endYear;
	public static volatile SingularAttribute<TeachingCurriculum, String> remarks;
	public static volatile SingularAttribute<TeachingCurriculum, byte[]> attachedFile;
	public static volatile SingularAttribute<TeachingCurriculum, Child> child;
	public static volatile SingularAttribute<TeachingCurriculum, EducationalInstitution> educationalInstitution;

	public static final String RESULT = "result";
	public static final String SCHOOL_LEVEL = "schoolLevel";
	public static final String ANNUAL_RESULT = "annualResult";
	public static final String ATTACHED_FILE_CONTENT_TYPE = "attachedFileContentType";
	public static final String ARCHIVATED = "archivated";
	public static final String BEGINNING_YEAR = "beginningYear";
	public static final String ID = "id";
	public static final String END_YEAR = "endYear";
	public static final String REMARKS = "remarks";
	public static final String ATTACHED_FILE = "attachedFile";
	public static final String CHILD = "child";
	public static final String EDUCATIONAL_INSTITUTION = "educationalInstitution";

}


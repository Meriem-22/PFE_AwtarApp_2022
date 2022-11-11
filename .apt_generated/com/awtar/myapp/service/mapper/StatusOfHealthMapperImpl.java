package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.StatusOfHealth;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.StatusOfHealthDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-10T17:58:42+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class StatusOfHealthMapperImpl implements StatusOfHealthMapper {

    @Override
    public void partialUpdate(StatusOfHealth arg0, StatusOfHealthDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getHealthStatusCategory() != null ) {
            if ( arg0.getHealthStatusCategory() == null ) {
                arg0.setHealthStatusCategory( new HealthStatusCategory() );
            }
            healthStatusCategoryDTOToHealthStatusCategory( arg1.getHealthStatusCategory(), arg0.getHealthStatusCategory() );
        }
        if ( arg1.getHealthStatusDate() != null ) {
            arg0.setHealthStatusDate( arg1.getHealthStatusDate() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getPerson() != null ) {
            if ( arg0.getPerson() == null ) {
                arg0.setPerson( new Profile() );
            }
            profileDTOToProfile( arg1.getPerson(), arg0.getPerson() );
        }
        byte[] urlDetailsAttached = arg1.getUrlDetailsAttached();
        if ( urlDetailsAttached != null ) {
            arg0.urlDetailsAttached( Arrays.copyOf( urlDetailsAttached, urlDetailsAttached.length ) );
        }
        if ( arg1.getUrlDetailsAttachedContentType() != null ) {
            arg0.urlDetailsAttachedContentType( arg1.getUrlDetailsAttachedContentType() );
        }
    }

    @Override
    public List<StatusOfHealthDTO> toDto(List<StatusOfHealth> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<StatusOfHealthDTO> list = new ArrayList<StatusOfHealthDTO>( arg0.size() );
        for ( StatusOfHealth statusOfHealth : arg0 ) {
            list.add( toDto( statusOfHealth ) );
        }

        return list;
    }

    @Override
    public StatusOfHealth toEntity(StatusOfHealthDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        StatusOfHealth statusOfHealth = new StatusOfHealth();

        statusOfHealth.setArchivated( arg0.getArchivated() );
        statusOfHealth.setHealthStatusCategory( healthStatusCategoryDTOToHealthStatusCategory1( arg0.getHealthStatusCategory() ) );
        statusOfHealth.setHealthStatusDate( arg0.getHealthStatusDate() );
        statusOfHealth.setId( arg0.getId() );
        statusOfHealth.setPerson( profileDTOToProfile1( arg0.getPerson() ) );
        byte[] urlDetailsAttached = arg0.getUrlDetailsAttached();
        if ( urlDetailsAttached != null ) {
            statusOfHealth.urlDetailsAttached( Arrays.copyOf( urlDetailsAttached, urlDetailsAttached.length ) );
        }
        statusOfHealth.urlDetailsAttachedContentType( arg0.getUrlDetailsAttachedContentType() );

        return statusOfHealth;
    }

    @Override
    public List<StatusOfHealth> toEntity(List<StatusOfHealthDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<StatusOfHealth> list = new ArrayList<StatusOfHealth>( arg0.size() );
        for ( StatusOfHealthDTO statusOfHealthDTO : arg0 ) {
            list.add( toEntity( statusOfHealthDTO ) );
        }

        return list;
    }

    @Override
    public StatusOfHealthDTO toDto(StatusOfHealth s) {
        if ( s == null ) {
            return null;
        }

        StatusOfHealthDTO statusOfHealthDTO = new StatusOfHealthDTO();

        statusOfHealthDTO.setPerson( toDtoProfileId( s.getPerson() ) );
        statusOfHealthDTO.setHealthStatusCategory( toDtoHealthStatusCategoryName( s.getHealthStatusCategory() ) );
        statusOfHealthDTO.setId( s.getId() );
        statusOfHealthDTO.setHealthStatusDate( s.getHealthStatusDate() );
        byte[] urlDetailsAttached = s.getUrlDetailsAttached();
        if ( urlDetailsAttached != null ) {
            statusOfHealthDTO.setUrlDetailsAttached( Arrays.copyOf( urlDetailsAttached, urlDetailsAttached.length ) );
        }
        statusOfHealthDTO.setUrlDetailsAttachedContentType( s.getUrlDetailsAttachedContentType() );
        statusOfHealthDTO.setArchivated( s.getArchivated() );

        return statusOfHealthDTO;
    }

    @Override
    public ProfileDTO toDtoProfileId(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId( profile.getId() );

        return profileDTO;
    }

    @Override
    public HealthStatusCategoryDTO toDtoHealthStatusCategoryName(HealthStatusCategory healthStatusCategory) {
        if ( healthStatusCategory == null ) {
            return null;
        }

        HealthStatusCategoryDTO healthStatusCategoryDTO = new HealthStatusCategoryDTO();

        healthStatusCategoryDTO.setId( healthStatusCategory.getId() );
        healthStatusCategoryDTO.setName( healthStatusCategory.getName() );

        return healthStatusCategoryDTO;
    }

    protected void healthStatusCategoryDTOToHealthStatusCategory(HealthStatusCategoryDTO healthStatusCategoryDTO, HealthStatusCategory mappingTarget) {
        if ( healthStatusCategoryDTO == null ) {
            return;
        }

        if ( healthStatusCategoryDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( healthStatusCategoryDTO.getArchivated() );
        }
        if ( healthStatusCategoryDTO.getId() != null ) {
            mappingTarget.setId( healthStatusCategoryDTO.getId() );
        }
        if ( healthStatusCategoryDTO.getName() != null ) {
            mappingTarget.setName( healthStatusCategoryDTO.getName() );
        }
    }

    protected void authorizingOfficerDTOToAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficerDTO, AuthorizingOfficer mappingTarget) {
        if ( authorizingOfficerDTO == null ) {
            return;
        }

        if ( authorizingOfficerDTO.getAbbreviation() != null ) {
            mappingTarget.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        }
        if ( authorizingOfficerDTO.getActivity() != null ) {
            mappingTarget.setActivity( authorizingOfficerDTO.getActivity() );
        }
        if ( authorizingOfficerDTO.getId() != null ) {
            mappingTarget.setId( authorizingOfficerDTO.getId() );
        }
        if ( authorizingOfficerDTO.getManager() != null ) {
            mappingTarget.setManager( authorizingOfficerDTO.getManager() );
        }
        if ( authorizingOfficerDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( authorizingOfficerDTO.getManagerCin() );
        }
    }

    protected void cityDTOToCity(CityDTO cityDTO, City mappingTarget) {
        if ( cityDTO == null ) {
            return;
        }

        if ( cityDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( cityDTO.getArchivated() );
        }
        if ( cityDTO.getGovernorate() != null ) {
            mappingTarget.setGovernorate( cityDTO.getGovernorate() );
        }
        if ( cityDTO.getId() != null ) {
            mappingTarget.setId( cityDTO.getId() );
        }
        if ( cityDTO.getIsGovernorate() != null ) {
            mappingTarget.setIsGovernorate( cityDTO.getIsGovernorate() );
        }
        if ( cityDTO.getName() != null ) {
            mappingTarget.setName( cityDTO.getName() );
        }
    }

    protected void profileDTOToAuthorizingOfficer(ProfileDTO profileDTO, AuthorizingOfficer mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
    }

    protected void profileDTOToTutor(ProfileDTO profileDTO, Tutor mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
    }

    protected void familyDTOToFamily(FamilyDTO familyDTO, Family mappingTarget) {
        if ( familyDTO == null ) {
            return;
        }

        if ( familyDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer( familyDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( familyDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor( familyDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( familyDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( familyDTO.getArchivated() );
        }
        if ( familyDTO.getArea() != null ) {
            mappingTarget.setArea( familyDTO.getArea() );
        }
        if ( familyDTO.getDwelling() != null ) {
            mappingTarget.setDwelling( familyDTO.getDwelling() );
        }
        if ( familyDTO.getFamilyName() != null ) {
            mappingTarget.setFamilyName( familyDTO.getFamilyName() );
        }
        if ( familyDTO.getId() != null ) {
            mappingTarget.setId( familyDTO.getId() );
        }
        if ( familyDTO.getNotebookOfHandicap() != null ) {
            mappingTarget.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        }
        if ( familyDTO.getNotebookOfPoverty() != null ) {
            mappingTarget.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        }
    }

    protected void childDTOToChild(ChildDTO childDTO, Child mappingTarget) {
        if ( childDTO == null ) {
            return;
        }

        if ( childDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer( childDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( childDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor( childDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( childDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.setFamily( new Family() );
            }
            familyDTOToFamily( childDTO.getFamily(), mappingTarget.getFamily() );
        }
        if ( childDTO.getId() != null ) {
            mappingTarget.setId( childDTO.getId() );
        }
    }

    protected void parentDTOToParent(ParentDTO parentDTO, Parent mappingTarget) {
        if ( parentDTO == null ) {
            return;
        }

        if ( parentDTO.getAnnualRevenue() != null ) {
            mappingTarget.setAnnualRevenue( parentDTO.getAnnualRevenue() );
        }
        if ( parentDTO.getCnss() != null ) {
            mappingTarget.setCnss( parentDTO.getCnss() );
        }
        if ( parentDTO.getDateOfDeath() != null ) {
            mappingTarget.setDateOfDeath( parentDTO.getDateOfDeath() );
        }
        if ( parentDTO.getDeceased() != null ) {
            mappingTarget.setDeceased( parentDTO.getDeceased() );
        }
        if ( parentDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.setFamily( new Family() );
            }
            familyDTOToFamily( parentDTO.getFamily(), mappingTarget.getFamily() );
        }
        if ( parentDTO.getFamilyHead() != null ) {
            if ( mappingTarget.getFamilyHead() == null ) {
                mappingTarget.setFamilyHead( new Family() );
            }
            familyDTOToFamily( parentDTO.getFamilyHead(), mappingTarget.getFamilyHead() );
        }
        if ( parentDTO.getId() != null ) {
            mappingTarget.setId( parentDTO.getId() );
        }
        if ( parentDTO.getMaritalStatus() != null ) {
            mappingTarget.setMaritalStatus( parentDTO.getMaritalStatus() );
        }
        if ( parentDTO.getOccupation() != null ) {
            mappingTarget.setOccupation( parentDTO.getOccupation() );
        }
    }

    protected void tutorDTOToTutor(TutorDTO tutorDTO, Tutor mappingTarget) {
        if ( tutorDTO == null ) {
            return;
        }

        if ( tutorDTO.getActivity() != null ) {
            mappingTarget.setActivity( tutorDTO.getActivity() );
        }
        if ( tutorDTO.getId() != null ) {
            mappingTarget.setId( tutorDTO.getId() );
        }
        if ( tutorDTO.getManager() != null ) {
            mappingTarget.setManager( tutorDTO.getManager() );
        }
        if ( tutorDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( tutorDTO.getManagerCin() );
        }
    }

    protected void profileDTOToProfile(ProfileDTO profileDTO, Profile mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getAddress() != null ) {
            mappingTarget.setAddress( profileDTO.getAddress() );
        }
        if ( profileDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( profileDTO.getArchivated() );
        }
        if ( profileDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer( profileDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( profileDTO.getBirthPlace() != null ) {
            if ( mappingTarget.getBirthPlace() == null ) {
                mappingTarget.setBirthPlace( new City() );
            }
            cityDTOToCity( profileDTO.getBirthPlace(), mappingTarget.getBirthPlace() );
        }
        if ( profileDTO.getChild() != null ) {
            if ( mappingTarget.getChild() == null ) {
                mappingTarget.setChild( new Child() );
            }
            childDTOToChild( profileDTO.getChild(), mappingTarget.getChild() );
        }
        if ( profileDTO.getCin() != null ) {
            mappingTarget.setCin( profileDTO.getCin() );
        }
        if ( profileDTO.getDateOfBirth() != null ) {
            mappingTarget.setDateOfBirth( profileDTO.getDateOfBirth() );
        }
        if ( profileDTO.getEmail() != null ) {
            mappingTarget.setEmail( profileDTO.getEmail() );
        }
        if ( profileDTO.getFirstName() != null ) {
            mappingTarget.setFirstName( profileDTO.getFirstName() );
        }
        if ( profileDTO.getFirstNameArabic() != null ) {
            mappingTarget.setFirstNameArabic( profileDTO.getFirstNameArabic() );
        }
        if ( profileDTO.getGender() != null ) {
            mappingTarget.setGender( profileDTO.getGender() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getLastName() != null ) {
            mappingTarget.setLastName( profileDTO.getLastName() );
        }
        if ( profileDTO.getLastNameArabic() != null ) {
            mappingTarget.setLastNameArabic( profileDTO.getLastNameArabic() );
        }
        if ( profileDTO.getParent() != null ) {
            if ( mappingTarget.getParent() == null ) {
                mappingTarget.setParent( new Parent() );
            }
            parentDTOToParent( profileDTO.getParent(), mappingTarget.getParent() );
        }
        if ( profileDTO.getPhone() != null ) {
            mappingTarget.setPhone( profileDTO.getPhone() );
        }
        if ( profileDTO.getPlaceOfResidence() != null ) {
            if ( mappingTarget.getPlaceOfResidence() == null ) {
                mappingTarget.setPlaceOfResidence( new City() );
            }
            cityDTOToCity( profileDTO.getPlaceOfResidence(), mappingTarget.getPlaceOfResidence() );
        }
        if ( profileDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.tutor( new Tutor() );
            }
            tutorDTOToTutor( profileDTO.getTutor(), mappingTarget.getTutor() );
        }
        byte[] urlCinAttached = profileDTO.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            mappingTarget.urlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        if ( profileDTO.getUrlCinAttachedContentType() != null ) {
            mappingTarget.urlCinAttachedContentType( profileDTO.getUrlCinAttachedContentType() );
        }
        byte[] urlPhoto = profileDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            mappingTarget.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( profileDTO.getUrlPhotoContentType() != null ) {
            mappingTarget.urlPhotoContentType( profileDTO.getUrlPhotoContentType() );
        }
    }

    protected HealthStatusCategory healthStatusCategoryDTOToHealthStatusCategory1(HealthStatusCategoryDTO healthStatusCategoryDTO) {
        if ( healthStatusCategoryDTO == null ) {
            return null;
        }

        HealthStatusCategory healthStatusCategory = new HealthStatusCategory();

        healthStatusCategory.setArchivated( healthStatusCategoryDTO.getArchivated() );
        healthStatusCategory.setId( healthStatusCategoryDTO.getId() );
        healthStatusCategory.setName( healthStatusCategoryDTO.getName() );

        return healthStatusCategory;
    }

    protected AuthorizingOfficer authorizingOfficerDTOToAuthorizingOfficer1(AuthorizingOfficerDTO authorizingOfficerDTO) {
        if ( authorizingOfficerDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        authorizingOfficer.setActivity( authorizingOfficerDTO.getActivity() );
        authorizingOfficer.setId( authorizingOfficerDTO.getId() );
        authorizingOfficer.setManager( authorizingOfficerDTO.getManager() );
        authorizingOfficer.setManagerCin( authorizingOfficerDTO.getManagerCin() );

        return authorizingOfficer;
    }

    protected City cityDTOToCity1(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        City city = new City();

        city.setArchivated( cityDTO.getArchivated() );
        city.setGovernorate( cityDTO.getGovernorate() );
        city.setId( cityDTO.getId() );
        city.setIsGovernorate( cityDTO.getIsGovernorate() );
        city.setName( cityDTO.getName() );

        return city;
    }

    protected AuthorizingOfficer profileDTOToAuthorizingOfficer1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setActivity( profileDTO.getActivity() );
        authorizingOfficer.setId( profileDTO.getId() );

        return authorizingOfficer;
    }

    protected Tutor profileDTOToTutor1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setActivity( profileDTO.getActivity() );
        tutor.setId( profileDTO.getId() );

        return tutor;
    }

    protected Family familyDTOToFamily1(FamilyDTO familyDTO) {
        if ( familyDTO == null ) {
            return null;
        }

        Family family = new Family();

        family.setAuthorizingOfficer( profileDTOToAuthorizingOfficer1( familyDTO.getAuthorizingOfficer() ) );
        family.setTutor( profileDTOToTutor1( familyDTO.getTutor() ) );
        family.setArchivated( familyDTO.getArchivated() );
        family.setArea( familyDTO.getArea() );
        family.setDwelling( familyDTO.getDwelling() );
        family.setFamilyName( familyDTO.getFamilyName() );
        family.setId( familyDTO.getId() );
        family.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        family.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );

        return family;
    }

    protected Child childDTOToChild1(ChildDTO childDTO) {
        if ( childDTO == null ) {
            return null;
        }

        Child child = new Child();

        child.setAuthorizingOfficer( profileDTOToAuthorizingOfficer1( childDTO.getAuthorizingOfficer() ) );
        child.setTutor( profileDTOToTutor1( childDTO.getTutor() ) );
        child.setFamily( familyDTOToFamily1( childDTO.getFamily() ) );
        child.setId( childDTO.getId() );

        return child;
    }

    protected Parent parentDTOToParent1(ParentDTO parentDTO) {
        if ( parentDTO == null ) {
            return null;
        }

        Parent parent = new Parent();

        parent.setAnnualRevenue( parentDTO.getAnnualRevenue() );
        parent.setCnss( parentDTO.getCnss() );
        parent.setDateOfDeath( parentDTO.getDateOfDeath() );
        parent.setDeceased( parentDTO.getDeceased() );
        parent.setFamily( familyDTOToFamily1( parentDTO.getFamily() ) );
        parent.setFamilyHead( familyDTOToFamily1( parentDTO.getFamilyHead() ) );
        parent.setId( parentDTO.getId() );
        parent.setMaritalStatus( parentDTO.getMaritalStatus() );
        parent.setOccupation( parentDTO.getOccupation() );

        return parent;
    }

    protected Tutor tutorDTOToTutor1(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setActivity( tutorDTO.getActivity() );
        tutor.setId( tutorDTO.getId() );
        tutor.setManager( tutorDTO.getManager() );
        tutor.setManagerCin( tutorDTO.getManagerCin() );

        return tutor;
    }

    protected Profile profileDTOToProfile1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setAddress( profileDTO.getAddress() );
        profile.setArchivated( profileDTO.getArchivated() );
        profile.setAuthorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer1( profileDTO.getAuthorizingOfficer() ) );
        profile.setBirthPlace( cityDTOToCity1( profileDTO.getBirthPlace() ) );
        profile.setChild( childDTOToChild1( profileDTO.getChild() ) );
        profile.setCin( profileDTO.getCin() );
        profile.setDateOfBirth( profileDTO.getDateOfBirth() );
        profile.setEmail( profileDTO.getEmail() );
        profile.setFirstName( profileDTO.getFirstName() );
        profile.setFirstNameArabic( profileDTO.getFirstNameArabic() );
        profile.setGender( profileDTO.getGender() );
        profile.setId( profileDTO.getId() );
        profile.setLastName( profileDTO.getLastName() );
        profile.setLastNameArabic( profileDTO.getLastNameArabic() );
        profile.setParent( parentDTOToParent1( profileDTO.getParent() ) );
        profile.setPhone( profileDTO.getPhone() );
        profile.setPlaceOfResidence( cityDTOToCity1( profileDTO.getPlaceOfResidence() ) );
        profile.tutor( tutorDTOToTutor1( profileDTO.getTutor() ) );
        byte[] urlCinAttached = profileDTO.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            profile.urlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        profile.urlCinAttachedContentType( profileDTO.getUrlCinAttachedContentType() );
        byte[] urlPhoto = profileDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            profile.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        profile.urlPhotoContentType( profileDTO.getUrlPhotoContentType() );

        return profile;
    }
}

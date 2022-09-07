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
    date = "2022-09-07T09:58:11+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class StatusOfHealthMapperImpl implements StatusOfHealthMapper {

    @Override
    public StatusOfHealth toEntity(StatusOfHealthDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StatusOfHealth statusOfHealth = new StatusOfHealth();

        statusOfHealth.setId( dto.getId() );
        statusOfHealth.setHealthStatusDate( dto.getHealthStatusDate() );
        byte[] urlDetailsAttached = dto.getUrlDetailsAttached();
        if ( urlDetailsAttached != null ) {
            statusOfHealth.setUrlDetailsAttached( Arrays.copyOf( urlDetailsAttached, urlDetailsAttached.length ) );
        }
        statusOfHealth.setUrlDetailsAttachedContentType( dto.getUrlDetailsAttachedContentType() );
        statusOfHealth.setArchivated( dto.getArchivated() );
        statusOfHealth.person( profileDTOToProfile( dto.getPerson() ) );
        statusOfHealth.healthStatusCategory( healthStatusCategoryDTOToHealthStatusCategory( dto.getHealthStatusCategory() ) );

        return statusOfHealth;
    }

    @Override
    public List<StatusOfHealth> toEntity(List<StatusOfHealthDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StatusOfHealth> list = new ArrayList<StatusOfHealth>( dtoList.size() );
        for ( StatusOfHealthDTO statusOfHealthDTO : dtoList ) {
            list.add( toEntity( statusOfHealthDTO ) );
        }

        return list;
    }

    @Override
    public List<StatusOfHealthDTO> toDto(List<StatusOfHealth> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StatusOfHealthDTO> list = new ArrayList<StatusOfHealthDTO>( entityList.size() );
        for ( StatusOfHealth statusOfHealth : entityList ) {
            list.add( toDto( statusOfHealth ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(StatusOfHealth entity, StatusOfHealthDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getHealthStatusDate() != null ) {
            entity.setHealthStatusDate( dto.getHealthStatusDate() );
        }
        byte[] urlDetailsAttached = dto.getUrlDetailsAttached();
        if ( urlDetailsAttached != null ) {
            entity.setUrlDetailsAttached( Arrays.copyOf( urlDetailsAttached, urlDetailsAttached.length ) );
        }
        if ( dto.getUrlDetailsAttachedContentType() != null ) {
            entity.setUrlDetailsAttachedContentType( dto.getUrlDetailsAttachedContentType() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getPerson() != null ) {
            if ( entity.getPerson() == null ) {
                entity.person( new Profile() );
            }
            profileDTOToProfile1( dto.getPerson(), entity.getPerson() );
        }
        if ( dto.getHealthStatusCategory() != null ) {
            if ( entity.getHealthStatusCategory() == null ) {
                entity.healthStatusCategory( new HealthStatusCategory() );
            }
            healthStatusCategoryDTOToHealthStatusCategory1( dto.getHealthStatusCategory(), entity.getHealthStatusCategory() );
        }
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

    protected AuthorizingOfficer profileDTOToAuthorizingOfficer(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setId( profileDTO.getId() );
        authorizingOfficer.setActivity( profileDTO.getActivity() );

        return authorizingOfficer;
    }

    protected Tutor profileDTOToTutor(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setId( profileDTO.getId() );
        tutor.setActivity( profileDTO.getActivity() );

        return tutor;
    }

    protected Family familyDTOToFamily(FamilyDTO familyDTO) {
        if ( familyDTO == null ) {
            return null;
        }

        Family family = new Family();

        family.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( familyDTO.getAuthorizingOfficer() ) );
        family.setTutor( profileDTOToTutor( familyDTO.getTutor() ) );
        family.setId( familyDTO.getId() );
        family.setFamilyName( familyDTO.getFamilyName() );
        family.setDwelling( familyDTO.getDwelling() );
        family.setArea( familyDTO.getArea() );
        family.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        family.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        family.setArchivated( familyDTO.getArchivated() );

        return family;
    }

    protected Parent parentDTOToParent(ParentDTO parentDTO) {
        if ( parentDTO == null ) {
            return null;
        }

        Parent parent = new Parent();

        parent.setId( parentDTO.getId() );
        parent.setAnnualRevenue( parentDTO.getAnnualRevenue() );
        parent.setCnss( parentDTO.getCnss() );
        parent.setMaritalStatus( parentDTO.getMaritalStatus() );
        parent.setOccupation( parentDTO.getOccupation() );
        parent.setDeceased( parentDTO.getDeceased() );
        parent.setDateOfDeath( parentDTO.getDateOfDeath() );
        parent.familyHead( familyDTOToFamily( parentDTO.getFamilyHead() ) );
        parent.family( familyDTOToFamily( parentDTO.getFamily() ) );

        return parent;
    }

    protected Child childDTOToChild(ChildDTO childDTO) {
        if ( childDTO == null ) {
            return null;
        }

        Child child = new Child();

        child.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( childDTO.getAuthorizingOfficer() ) );
        child.setTutor( profileDTOToTutor( childDTO.getTutor() ) );
        child.setId( childDTO.getId() );
        child.family( familyDTOToFamily( childDTO.getFamily() ) );

        return child;
    }

    protected AuthorizingOfficer authorizingOfficerDTOToAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficerDTO) {
        if ( authorizingOfficerDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setId( authorizingOfficerDTO.getId() );
        authorizingOfficer.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        authorizingOfficer.setActivity( authorizingOfficerDTO.getActivity() );
        authorizingOfficer.setManager( authorizingOfficerDTO.getManager() );
        authorizingOfficer.setManagerCin( authorizingOfficerDTO.getManagerCin() );

        return authorizingOfficer;
    }

    protected Tutor tutorDTOToTutor(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setId( tutorDTO.getId() );
        tutor.setActivity( tutorDTO.getActivity() );
        tutor.setManager( tutorDTO.getManager() );
        tutor.setManagerCin( tutorDTO.getManagerCin() );

        return tutor;
    }

    protected City cityDTOToCity(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        City city = new City();

        city.setId( cityDTO.getId() );
        city.setName( cityDTO.getName() );
        city.setGovernorate( cityDTO.getGovernorate() );
        city.setIsGovernorate( cityDTO.getIsGovernorate() );
        city.setArchivated( cityDTO.getArchivated() );

        return city;
    }

    protected Profile profileDTOToProfile(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setId( profileDTO.getId() );
        profile.setFirstName( profileDTO.getFirstName() );
        profile.setLastName( profileDTO.getLastName() );
        profile.setFirstNameArabic( profileDTO.getFirstNameArabic() );
        profile.setLastNameArabic( profileDTO.getLastNameArabic() );
        profile.setGender( profileDTO.getGender() );
        profile.setDateOfBirth( profileDTO.getDateOfBirth() );
        profile.setCin( profileDTO.getCin() );
        byte[] urlPhoto = profileDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            profile.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        profile.setUrlPhotoContentType( profileDTO.getUrlPhotoContentType() );
        profile.setAddress( profileDTO.getAddress() );
        profile.setPhone( profileDTO.getPhone() );
        profile.setEmail( profileDTO.getEmail() );
        byte[] urlCinAttached = profileDTO.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            profile.setUrlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        profile.setUrlCinAttachedContentType( profileDTO.getUrlCinAttachedContentType() );
        profile.setArchivated( profileDTO.getArchivated() );
        profile.parent( parentDTOToParent( profileDTO.getParent() ) );
        profile.child( childDTOToChild( profileDTO.getChild() ) );
        profile.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer( profileDTO.getAuthorizingOfficer() ) );
        profile.tutor( tutorDTOToTutor( profileDTO.getTutor() ) );
        profile.birthPlace( cityDTOToCity( profileDTO.getBirthPlace() ) );
        profile.placeOfResidence( cityDTOToCity( profileDTO.getPlaceOfResidence() ) );

        return profile;
    }

    protected HealthStatusCategory healthStatusCategoryDTOToHealthStatusCategory(HealthStatusCategoryDTO healthStatusCategoryDTO) {
        if ( healthStatusCategoryDTO == null ) {
            return null;
        }

        HealthStatusCategory healthStatusCategory = new HealthStatusCategory();

        healthStatusCategory.setId( healthStatusCategoryDTO.getId() );
        healthStatusCategory.setName( healthStatusCategoryDTO.getName() );
        healthStatusCategory.setArchivated( healthStatusCategoryDTO.getArchivated() );

        return healthStatusCategory;
    }

    protected void profileDTOToAuthorizingOfficer1(ProfileDTO profileDTO, AuthorizingOfficer mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
    }

    protected void profileDTOToTutor1(ProfileDTO profileDTO, Tutor mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
    }

    protected void familyDTOToFamily1(FamilyDTO familyDTO, Family mappingTarget) {
        if ( familyDTO == null ) {
            return;
        }

        if ( familyDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( familyDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( familyDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor1( familyDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( familyDTO.getId() != null ) {
            mappingTarget.setId( familyDTO.getId() );
        }
        if ( familyDTO.getFamilyName() != null ) {
            mappingTarget.setFamilyName( familyDTO.getFamilyName() );
        }
        if ( familyDTO.getDwelling() != null ) {
            mappingTarget.setDwelling( familyDTO.getDwelling() );
        }
        if ( familyDTO.getArea() != null ) {
            mappingTarget.setArea( familyDTO.getArea() );
        }
        if ( familyDTO.getNotebookOfPoverty() != null ) {
            mappingTarget.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        }
        if ( familyDTO.getNotebookOfHandicap() != null ) {
            mappingTarget.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        }
        if ( familyDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( familyDTO.getArchivated() );
        }
    }

    protected void parentDTOToParent1(ParentDTO parentDTO, Parent mappingTarget) {
        if ( parentDTO == null ) {
            return;
        }

        if ( parentDTO.getId() != null ) {
            mappingTarget.setId( parentDTO.getId() );
        }
        if ( parentDTO.getAnnualRevenue() != null ) {
            mappingTarget.setAnnualRevenue( parentDTO.getAnnualRevenue() );
        }
        if ( parentDTO.getCnss() != null ) {
            mappingTarget.setCnss( parentDTO.getCnss() );
        }
        if ( parentDTO.getMaritalStatus() != null ) {
            mappingTarget.setMaritalStatus( parentDTO.getMaritalStatus() );
        }
        if ( parentDTO.getOccupation() != null ) {
            mappingTarget.setOccupation( parentDTO.getOccupation() );
        }
        if ( parentDTO.getDeceased() != null ) {
            mappingTarget.setDeceased( parentDTO.getDeceased() );
        }
        if ( parentDTO.getDateOfDeath() != null ) {
            mappingTarget.setDateOfDeath( parentDTO.getDateOfDeath() );
        }
        if ( parentDTO.getFamilyHead() != null ) {
            if ( mappingTarget.getFamilyHead() == null ) {
                mappingTarget.familyHead( new Family() );
            }
            familyDTOToFamily1( parentDTO.getFamilyHead(), mappingTarget.getFamilyHead() );
        }
        if ( parentDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.family( new Family() );
            }
            familyDTOToFamily1( parentDTO.getFamily(), mappingTarget.getFamily() );
        }
    }

    protected void childDTOToChild1(ChildDTO childDTO, Child mappingTarget) {
        if ( childDTO == null ) {
            return;
        }

        if ( childDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( childDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( childDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor1( childDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( childDTO.getId() != null ) {
            mappingTarget.setId( childDTO.getId() );
        }
        if ( childDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.family( new Family() );
            }
            familyDTOToFamily1( childDTO.getFamily(), mappingTarget.getFamily() );
        }
    }

    protected void authorizingOfficerDTOToAuthorizingOfficer1(AuthorizingOfficerDTO authorizingOfficerDTO, AuthorizingOfficer mappingTarget) {
        if ( authorizingOfficerDTO == null ) {
            return;
        }

        if ( authorizingOfficerDTO.getId() != null ) {
            mappingTarget.setId( authorizingOfficerDTO.getId() );
        }
        if ( authorizingOfficerDTO.getAbbreviation() != null ) {
            mappingTarget.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        }
        if ( authorizingOfficerDTO.getActivity() != null ) {
            mappingTarget.setActivity( authorizingOfficerDTO.getActivity() );
        }
        if ( authorizingOfficerDTO.getManager() != null ) {
            mappingTarget.setManager( authorizingOfficerDTO.getManager() );
        }
        if ( authorizingOfficerDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( authorizingOfficerDTO.getManagerCin() );
        }
    }

    protected void tutorDTOToTutor1(TutorDTO tutorDTO, Tutor mappingTarget) {
        if ( tutorDTO == null ) {
            return;
        }

        if ( tutorDTO.getId() != null ) {
            mappingTarget.setId( tutorDTO.getId() );
        }
        if ( tutorDTO.getActivity() != null ) {
            mappingTarget.setActivity( tutorDTO.getActivity() );
        }
        if ( tutorDTO.getManager() != null ) {
            mappingTarget.setManager( tutorDTO.getManager() );
        }
        if ( tutorDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( tutorDTO.getManagerCin() );
        }
    }

    protected void cityDTOToCity1(CityDTO cityDTO, City mappingTarget) {
        if ( cityDTO == null ) {
            return;
        }

        if ( cityDTO.getId() != null ) {
            mappingTarget.setId( cityDTO.getId() );
        }
        if ( cityDTO.getName() != null ) {
            mappingTarget.setName( cityDTO.getName() );
        }
        if ( cityDTO.getGovernorate() != null ) {
            mappingTarget.setGovernorate( cityDTO.getGovernorate() );
        }
        if ( cityDTO.getIsGovernorate() != null ) {
            mappingTarget.setIsGovernorate( cityDTO.getIsGovernorate() );
        }
        if ( cityDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( cityDTO.getArchivated() );
        }
    }

    protected void profileDTOToProfile1(ProfileDTO profileDTO, Profile mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getFirstName() != null ) {
            mappingTarget.setFirstName( profileDTO.getFirstName() );
        }
        if ( profileDTO.getLastName() != null ) {
            mappingTarget.setLastName( profileDTO.getLastName() );
        }
        if ( profileDTO.getFirstNameArabic() != null ) {
            mappingTarget.setFirstNameArabic( profileDTO.getFirstNameArabic() );
        }
        if ( profileDTO.getLastNameArabic() != null ) {
            mappingTarget.setLastNameArabic( profileDTO.getLastNameArabic() );
        }
        if ( profileDTO.getGender() != null ) {
            mappingTarget.setGender( profileDTO.getGender() );
        }
        if ( profileDTO.getDateOfBirth() != null ) {
            mappingTarget.setDateOfBirth( profileDTO.getDateOfBirth() );
        }
        if ( profileDTO.getCin() != null ) {
            mappingTarget.setCin( profileDTO.getCin() );
        }
        byte[] urlPhoto = profileDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            mappingTarget.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( profileDTO.getUrlPhotoContentType() != null ) {
            mappingTarget.setUrlPhotoContentType( profileDTO.getUrlPhotoContentType() );
        }
        if ( profileDTO.getAddress() != null ) {
            mappingTarget.setAddress( profileDTO.getAddress() );
        }
        if ( profileDTO.getPhone() != null ) {
            mappingTarget.setPhone( profileDTO.getPhone() );
        }
        if ( profileDTO.getEmail() != null ) {
            mappingTarget.setEmail( profileDTO.getEmail() );
        }
        byte[] urlCinAttached = profileDTO.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            mappingTarget.setUrlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        if ( profileDTO.getUrlCinAttachedContentType() != null ) {
            mappingTarget.setUrlCinAttachedContentType( profileDTO.getUrlCinAttachedContentType() );
        }
        if ( profileDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( profileDTO.getArchivated() );
        }
        if ( profileDTO.getParent() != null ) {
            if ( mappingTarget.getParent() == null ) {
                mappingTarget.parent( new Parent() );
            }
            parentDTOToParent1( profileDTO.getParent(), mappingTarget.getParent() );
        }
        if ( profileDTO.getChild() != null ) {
            if ( mappingTarget.getChild() == null ) {
                mappingTarget.child( new Child() );
            }
            childDTOToChild1( profileDTO.getChild(), mappingTarget.getChild() );
        }
        if ( profileDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.authorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer1( profileDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( profileDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.tutor( new Tutor() );
            }
            tutorDTOToTutor1( profileDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( profileDTO.getBirthPlace() != null ) {
            if ( mappingTarget.getBirthPlace() == null ) {
                mappingTarget.birthPlace( new City() );
            }
            cityDTOToCity1( profileDTO.getBirthPlace(), mappingTarget.getBirthPlace() );
        }
        if ( profileDTO.getPlaceOfResidence() != null ) {
            if ( mappingTarget.getPlaceOfResidence() == null ) {
                mappingTarget.placeOfResidence( new City() );
            }
            cityDTOToCity1( profileDTO.getPlaceOfResidence(), mappingTarget.getPlaceOfResidence() );
        }
    }

    protected void healthStatusCategoryDTOToHealthStatusCategory1(HealthStatusCategoryDTO healthStatusCategoryDTO, HealthStatusCategory mappingTarget) {
        if ( healthStatusCategoryDTO == null ) {
            return;
        }

        if ( healthStatusCategoryDTO.getId() != null ) {
            mappingTarget.setId( healthStatusCategoryDTO.getId() );
        }
        if ( healthStatusCategoryDTO.getName() != null ) {
            mappingTarget.setName( healthStatusCategoryDTO.getName() );
        }
        if ( healthStatusCategoryDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( healthStatusCategoryDTO.getArchivated() );
        }
    }
}

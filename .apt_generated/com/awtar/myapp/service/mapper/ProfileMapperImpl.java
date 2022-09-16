package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-16T07:55:47+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public Profile toEntity(ProfileDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setId( dto.getId() );
        profile.setFirstName( dto.getFirstName() );
        profile.setLastName( dto.getLastName() );
        profile.setFirstNameArabic( dto.getFirstNameArabic() );
        profile.setLastNameArabic( dto.getLastNameArabic() );
        profile.setGender( dto.getGender() );
        profile.setDateOfBirth( dto.getDateOfBirth() );
        profile.setCin( dto.getCin() );
        byte[] urlPhoto = dto.getUrlPhoto();
        if ( urlPhoto != null ) {
            profile.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        profile.setUrlPhotoContentType( dto.getUrlPhotoContentType() );
        profile.setAddress( dto.getAddress() );
        profile.setPhone( dto.getPhone() );
        profile.setEmail( dto.getEmail() );
        byte[] urlCinAttached = dto.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            profile.setUrlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        profile.setUrlCinAttachedContentType( dto.getUrlCinAttachedContentType() );
        profile.setArchivated( dto.getArchivated() );
        profile.parent( parentDTOToParent( dto.getParent() ) );
        profile.child( childDTOToChild( dto.getChild() ) );
        profile.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer( dto.getAuthorizingOfficer() ) );
        profile.tutor( tutorDTOToTutor( dto.getTutor() ) );
        profile.birthPlace( cityDTOToCity( dto.getBirthPlace() ) );
        profile.placeOfResidence( cityDTOToCity( dto.getPlaceOfResidence() ) );

        return profile;
    }

    @Override
    public List<Profile> toEntity(List<ProfileDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Profile> list = new ArrayList<Profile>( dtoList.size() );
        for ( ProfileDTO profileDTO : dtoList ) {
            list.add( toEntity( profileDTO ) );
        }

        return list;
    }

    @Override
    public List<ProfileDTO> toDto(List<Profile> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProfileDTO> list = new ArrayList<ProfileDTO>( entityList.size() );
        for ( Profile profile : entityList ) {
            list.add( toDto( profile ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Profile entity, ProfileDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getFirstName() != null ) {
            entity.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getFirstNameArabic() != null ) {
            entity.setFirstNameArabic( dto.getFirstNameArabic() );
        }
        if ( dto.getLastNameArabic() != null ) {
            entity.setLastNameArabic( dto.getLastNameArabic() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getDateOfBirth() != null ) {
            entity.setDateOfBirth( dto.getDateOfBirth() );
        }
        if ( dto.getCin() != null ) {
            entity.setCin( dto.getCin() );
        }
        byte[] urlPhoto = dto.getUrlPhoto();
        if ( urlPhoto != null ) {
            entity.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( dto.getUrlPhotoContentType() != null ) {
            entity.setUrlPhotoContentType( dto.getUrlPhotoContentType() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        byte[] urlCinAttached = dto.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            entity.setUrlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        if ( dto.getUrlCinAttachedContentType() != null ) {
            entity.setUrlCinAttachedContentType( dto.getUrlCinAttachedContentType() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getParent() != null ) {
            if ( entity.getParent() == null ) {
                entity.parent( new Parent() );
            }
            parentDTOToParent1( dto.getParent(), entity.getParent() );
        }
        if ( dto.getChild() != null ) {
            if ( entity.getChild() == null ) {
                entity.child( new Child() );
            }
            childDTOToChild1( dto.getChild(), entity.getChild() );
        }
        if ( dto.getAuthorizingOfficer() != null ) {
            if ( entity.getAuthorizingOfficer() == null ) {
                entity.authorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer1( dto.getAuthorizingOfficer(), entity.getAuthorizingOfficer() );
        }
        if ( dto.getTutor() != null ) {
            if ( entity.getTutor() == null ) {
                entity.tutor( new Tutor() );
            }
            tutorDTOToTutor1( dto.getTutor(), entity.getTutor() );
        }
        if ( dto.getBirthPlace() != null ) {
            if ( entity.getBirthPlace() == null ) {
                entity.birthPlace( new City() );
            }
            cityDTOToCity1( dto.getBirthPlace(), entity.getBirthPlace() );
        }
        if ( dto.getPlaceOfResidence() != null ) {
            if ( entity.getPlaceOfResidence() == null ) {
                entity.placeOfResidence( new City() );
            }
            cityDTOToCity1( dto.getPlaceOfResidence(), entity.getPlaceOfResidence() );
        }
    }

    @Override
    public ProfileDTO toDto(Profile s) {
        if ( s == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setParent( toDtoParentId( s.getParent() ) );
        profileDTO.setChild( toDtoChildId( s.getChild() ) );
        profileDTO.setAuthorizingOfficer( toDtoAuthorizingOfficerId( s.getAuthorizingOfficer() ) );
        profileDTO.setTutor( toDtoTutorId( s.getTutor() ) );
        profileDTO.setBirthPlace( toDtoCityName( s.getBirthPlace() ) );
        profileDTO.setPlaceOfResidence( toDtoCityName( s.getPlaceOfResidence() ) );
        profileDTO.setId( s.getId() );
        profileDTO.setFirstName( s.getFirstName() );
        profileDTO.setLastName( s.getLastName() );
        profileDTO.setFirstNameArabic( s.getFirstNameArabic() );
        profileDTO.setLastNameArabic( s.getLastNameArabic() );
        profileDTO.setGender( s.getGender() );
        profileDTO.setDateOfBirth( s.getDateOfBirth() );
        profileDTO.setCin( s.getCin() );
        byte[] urlPhoto = s.getUrlPhoto();
        if ( urlPhoto != null ) {
            profileDTO.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        profileDTO.setUrlPhotoContentType( s.getUrlPhotoContentType() );
        profileDTO.setAddress( s.getAddress() );
        profileDTO.setPhone( s.getPhone() );
        profileDTO.setEmail( s.getEmail() );
        byte[] urlCinAttached = s.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            profileDTO.setUrlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        profileDTO.setUrlCinAttachedContentType( s.getUrlCinAttachedContentType() );
        profileDTO.setArchivated( s.getArchivated() );

        return profileDTO;
    }

    @Override
    public ParentDTO toDtoParentId(Parent parent) {
        if ( parent == null ) {
            return null;
        }

        ParentDTO parentDTO = new ParentDTO();

        parentDTO.setId( parent.getId() );

        return parentDTO;
    }

    @Override
    public ChildDTO toDtoChildId(Child child) {
        if ( child == null ) {
            return null;
        }

        ChildDTO childDTO = new ChildDTO();

        childDTO.setId( child.getId() );

        return childDTO;
    }

    @Override
    public AuthorizingOfficerDTO toDtoAuthorizingOfficerId(AuthorizingOfficer authorizingOfficer) {
        if ( authorizingOfficer == null ) {
            return null;
        }

        AuthorizingOfficerDTO authorizingOfficerDTO = new AuthorizingOfficerDTO();

        authorizingOfficerDTO.setId( authorizingOfficer.getId() );

        return authorizingOfficerDTO;
    }

    @Override
    public TutorDTO toDtoTutorId(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }

        TutorDTO tutorDTO = new TutorDTO();

        tutorDTO.setId( tutor.getId() );

        return tutorDTO;
    }

    @Override
    public CityDTO toDtoCityName(City city) {
        if ( city == null ) {
            return null;
        }

        CityDTO cityDTO = new CityDTO();

        cityDTO.setId( city.getId() );
        cityDTO.setName( city.getName() );

        return cityDTO;
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
}

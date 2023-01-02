package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-06T08:12:10+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class TutorMapperImpl implements TutorMapper {

    @Override
    public Tutor toEntity(TutorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setId( dto.getId() );
        tutor.setActivity( dto.getActivity() );
        tutor.setManager( dto.getManager() );
        tutor.setManagerCin( dto.getManagerCin() );

        return tutor;
    }

    @Override
    public TutorDTO toDto(Tutor entity) {
        if ( entity == null ) {
            return null;
        }

        TutorDTO tutorDTO = new TutorDTO();

        tutorDTO.setId( entity.getId() );
        tutorDTO.setActivity( entity.getActivity() );
        tutorDTO.setManager( entity.getManager() );
        tutorDTO.setManagerCin( entity.getManagerCin() );

        return tutorDTO;
    }

    @Override
    public List<Tutor> toEntity(List<TutorDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Tutor> list = new ArrayList<Tutor>( dtoList.size() );
        for ( TutorDTO tutorDTO : dtoList ) {
            list.add( toEntity( tutorDTO ) );
        }

        return list;
    }

    @Override
    public List<TutorDTO> toDto(List<Tutor> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TutorDTO> list = new ArrayList<TutorDTO>( entityList.size() );
        for ( Tutor tutor : entityList ) {
            list.add( toDto( tutor ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Tutor entity, TutorDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getActivity() != null ) {
            entity.setActivity( dto.getActivity() );
        }
        if ( dto.getManager() != null ) {
            entity.setManager( dto.getManager() );
        }
        if ( dto.getManagerCin() != null ) {
            entity.setManagerCin( dto.getManagerCin() );
        }
    }
}

package org.xujin.halo.convertor;

import org.xujin.halo.domain.Entity;
import org.xujin.halo.dto.DTO;
import org.xujin.halo.repository.DataObject;

/**
 * Convertor  are used to convert Objects among Client Object, Domain Object and Data Object.
 *
 * @author xujin
 */
public interface ConvertorI {

    default public<T extends DTO> T entityToClient(Object... entityObject){return null;}

    default public<T extends DTO> T dataToClient(Object... dataObject){return null;}

    default public<T extends Entity> T clientToEntity(Object... clientObject){return null;}

    default public<T extends Entity> T dataToEntity(Object... dataObject){return null;}

    default public<T extends DataObject> T entityToData(Object... entityObject){return null;}

}


package org.xujin.halo.extension;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * ExtensionRepository 
 * @author xujin
 */
@Component
public class ExtensionRepository {

    @Getter
    private Map<ExtensionCoordinate, ExtensionPointI> extensionRepo = new HashMap<>();

}

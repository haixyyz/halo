package org.xujin.halo.extension;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Extension Coordinate(扩展点坐标) used to uniquely position a Extension
 * @author xujin
 */
@Data
public class ExtensionCoordinate {
    
    private String extensionPoint;
    private String bizCode;
    private String tenantId;
    
    /**
     * @param extensionPoint
     * @param bizCode
     * @param tenantId
     */
    public ExtensionCoordinate(String extensionPoint, String bizCode, String tenantId){
        super();
        this.extensionPoint = extensionPoint;
        this.bizCode = bizCode;
        this.tenantId = tenantId;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bizCode == null) ? 0 : bizCode.hashCode());
        result = prime * result + ((extensionPoint == null) ? 0 : extensionPoint.hashCode());
        result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ExtensionCoordinate other = (ExtensionCoordinate) obj;
        if (bizCode == null) {
            if (other.bizCode != null) return false;
        } else if (!bizCode.equals(other.bizCode)) return false;
        if (extensionPoint == null) {
            if (other.extensionPoint != null) return false;
        } else if (!extensionPoint.equals(other.extensionPoint)) return false;
        if (tenantId == null) {
            if (other.tenantId != null) return false;
        } else if (!tenantId.equals(other.tenantId)) return false;
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

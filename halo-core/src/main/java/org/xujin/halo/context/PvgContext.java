package org.xujin.halo.context;

import org.xujin.halo.exception.Preconditions;

/**
 * Pvg Context
 * <p/>
 * Created by xujin on 2018/4/9.
 */
public class PvgContext {

    private static ThreadLocal<Pvg> pvgContext = new ThreadLocal<>();

    private static class Pvg {
        private String crmUserId;
        private String roleName;
        private String orgId;
        private String corpId;

        public Pvg(String crmUserId, String roleName, String orgId, String corpId) {
            this.crmUserId = crmUserId;
            this.roleName = roleName;
            this.orgId = orgId;
            this.corpId = corpId;
        }
    }

    public static void set(String crmUserId, String roleName, String orgId, String corpId) {
        Pvg pvg = new Pvg(crmUserId, roleName, orgId, corpId);
        pvgContext.set(pvg);
    }

    public static void remove() {
        pvgContext.remove();
    }

    public static boolean exist() {
        return null != pvgContext.get();
    }

    public static String getCrmUserId() {
        Preconditions.checkArgument(null != pvgContext.get() && null != pvgContext.get().crmUserId,
            "No User in Context");
        return pvgContext.get().crmUserId;
    }

    public static String getRoleName() {
        Preconditions.checkArgument(null != pvgContext.get() && null != pvgContext.get().roleName,
            "No roleName in Context");
        return pvgContext.get().roleName;
    }

    public static String getOrgId() {
        Preconditions.checkArgument(null != pvgContext.get() && null != pvgContext.get().orgId,
            "No orgId in Context");
        return pvgContext.get().orgId;
    }

    public static String getCorpId() {
        Preconditions.checkArgument(null != pvgContext.get() && null != pvgContext.get().corpId,
            "No corpId in Context");
        return pvgContext.get().corpId;
    }

}

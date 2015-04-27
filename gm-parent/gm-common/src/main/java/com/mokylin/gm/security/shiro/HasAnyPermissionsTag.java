package com.mokylin.gm.security.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

/**
 * shiro hasAnyPermissionTag
 * Created by yongbo.chen
 * Time:2014/6/15 15:40
 */
public class HasAnyPermissionsTag extends PermissionTag{

    private static final long serialVersionUID = 1L;
    private static final String PERMISSION_NAMES_DELIMETER = ",";

    @Override
    protected boolean showTagBody(String permissionNames) {
        boolean hasAnyPermission = false;
        Subject subject = getSubject();

        if(subject !=  null){
            for(String permission : permissionNames.split(PERMISSION_NAMES_DELIMETER)){
                if(subject.isPermitted(permission.trim())){
                    hasAnyPermission = true;
                    break;
                }
            }
        }
        return hasAnyPermission;
    }
}

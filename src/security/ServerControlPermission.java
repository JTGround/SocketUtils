
package security;

import java.security.Permission;

public class ServerControlPermission extends Permission {

	private static final long serialVersionUID = -810362113250409059L;
	private String actions;
    
    public ServerControlPermission(String name, String actions) {
        super(name);
        this.actions = actions;
    }
    
    public String getActions() {
        return actions;
    }
    
    public int hashCode() {
        return this.hashCode();
    }
    
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;

	if (! (obj instanceof ServerControlPermission))
	    return false;

	ServerControlPermission that = (ServerControlPermission) obj;

	return (this.actions == that.actions);
    }
    
    public boolean implies(java.security.Permission permission) 
    {
	if (! (permission instanceof ServerControlPermission))
   		return false;

	ServerControlPermission fp = (ServerControlPermission) permission;

	/*int desired = fp.getMask();
	int effective = 0;
	int needed = desired;

	synchronized (this) {
	    int len = perms.size();
	    for (int i = 0; i < len; i++) {
		ServerControlPermission x = (ServerControlPermission) perms.get(i);
		if (((needed & x.getMask()) != 0) && x.impliesIgnoreMask(fp)) {
		    effective |=  x.getMask();
		    if ((effective & desired) == desired)
			return true;
		    needed = (desired ^ effective);
		}
	    }
	}*/
	return false;
    }
    
}

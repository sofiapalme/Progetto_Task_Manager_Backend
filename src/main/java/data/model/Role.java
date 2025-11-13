package data.model;

public enum Role {

    OWNER("OWNER"),
    EDITOR("EDITOR"),
    VIEWER("VIEWER")
    ;

    public final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public static final String ROLE_OWNER = "OWNER";
    public static final String ROLE_EDITOR = "EDITOR";
    public static final String ROLE_VIEWER = "VIEWER";
}

package ro.uaic.info.parcelexampleapp.security;

public class EndpointsConfiguration {
    public static final String[] GET_ADMIN_ONLY = new String[]{"/admin/awb", "/admin/routes"};

    public static final String[] GET_DELIVERY_ONLY = new String[]{"/delivery"};
    public static final String[] PUT_DELIVERY_ONLY = new String[]{"/delivery/awb/*"};

    public static final String[] GET_AUTHENTICATED_ONLY = new String[]{"/awb", "/user/profile", "/user/logout", "/awb/add"};
    public static final String[] POST_AUTHENTICATED_ONLY = new String[]{"/awb", "/payment/*", "/awb/add"};

    public static final String[] GET_UNAUTHENTICATED_ONLY = new String[]{"/user/login", "/user/register"};
    public static final String[] POST_UNAUTHENTICATED_ONLY = new String[]{"/user/login", "/user/register"};

    public static final String[] GET_EVERYONE = new String[]{"/", "/awb/*", "/css/**"};

}

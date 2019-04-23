package ir.redmind.paasho.service.dto.mock;

/**
 * A DTO representing a user's credentials
 */
public class LoginDTO {

    private String username;

    private String password;
    private String deviceToken;

    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
            "password='" + password + '\'' +
            ", user='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}

package ir.redmind.paasho.service.dto.mock;

/**
 * A DTO representing a user's credentials
 */
public class ConfirmOTPDTO {

    private String mobile;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
            "mobile='" + mobile + '\'' +
            '}';
    }
}

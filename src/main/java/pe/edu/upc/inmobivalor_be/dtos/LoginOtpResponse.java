package pe.edu.upc.inmobivalor_be.dtos;

import java.io.Serializable;

public class LoginOtpResponse implements Serializable {

    private boolean requiresOtp;

    private String message;


    public LoginOtpResponse(boolean requiresOtp, String message) {
        this.requiresOtp = requiresOtp;
        this.message = message;
    }

    public boolean isRequiresOtp() {
        return requiresOtp;
    }

    public String getMessage() {
        return message;
    }
}

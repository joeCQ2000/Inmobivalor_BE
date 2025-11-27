package pe.edu.upc.inmobivalor_be.dtos;

import java.io.Serializable;

public class VerifyOtpRequest implements Serializable {

    private String username;
    private String otp;

    public String getUsername() {
        return username;
    }

    public String getOtp() {
        return otp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}

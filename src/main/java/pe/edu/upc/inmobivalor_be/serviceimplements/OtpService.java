package pe.edu.upc.inmobivalor_be.serviceimplements;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.repositories.IUsuarioRepository;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpService {
    private final IUsuarioRepository usuarioRepository;
    private final JavaMailSender mailSender;

    public OtpService(IUsuarioRepository usuarioRepository, JavaMailSender mailSender) {
        this.usuarioRepository = usuarioRepository;
        this.mailSender = mailSender;
    }

    public void generateAndSendOtp(Usuario usuario) {
        //Generamos el codigo de 6 digitos
        String otp = generarOtp();

        //Guardar en el usuario con expiracion se pondra 5 min
        usuario.setOtpCode(otp);
        usuario.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        usuarioRepository.save(usuario);

        //Enviar al correo
        sendOtpEmail(usuario.getCorreo(), otp);


    }

    private String generarOtp() {
        int numero = ThreadLocalRandom.current().nextInt(100000, 999999);
        return String.valueOf(numero);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Codigo de verificacion");
        message.setText("Tu código de verificación es: " + otp + "\n\nVence en 5 minutos.");
        mailSender.send(message);
    }

    public void validateOtp(Usuario usuario, String otpIngresado) {
        if (usuario.getOtpCode() == null || usuario.getOtpExpiry() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay OTP generado para este usuario");
        }

        if (usuario.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El codigo expirado");
        }

        if (!usuario.getOtpCode().equals(otpIngresado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Código incorrecto");
        }

        // Si quieres que el código solo sirva una vez:
        usuario.setOtpCode(null);
        usuario.setOtpExpiry(null);
        usuarioRepository.save(usuario);
    }
}

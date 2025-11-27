package pe.edu.upc.inmobivalor_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.inmobivalor_be.dtos.LoginOtpResponse;
import pe.edu.upc.inmobivalor_be.dtos.VerifyOtpRequest;
import pe.edu.upc.inmobivalor_be.entities.Usuario;
import pe.edu.upc.inmobivalor_be.repositories.IUsuarioRepository;
import pe.edu.upc.inmobivalor_be.securities.JwtRequest;
import pe.edu.upc.inmobivalor_be.securities.JwtResponse;
import pe.edu.upc.inmobivalor_be.securities.JwtTokenUtil;
import pe.edu.upc.inmobivalor_be.serviceimplements.JwtUserDetailsService;
import pe.edu.upc.inmobivalor_be.serviceimplements.OtpService;

@RestController
@CrossOrigin
public class JWTAuthenticactionController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest req) throws Exception {
        authenticate(req.getUsername(), req.getPassword());

        //Cargar usuario de BD
        Usuario usuario = usuarioRepository.findOneByUsername(req.getUsername());
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Si el usuario requiere OTP (2FA)
        if(Boolean.TRUE.equals(usuario.getOtpRequired())){
            //Generar y enviar OTP por correo
            otpService.generateAndSendOtp(usuario);

            //No generas JWT todavia
            return ResponseEntity.ok(
                    new LoginOtpResponse(true, "Se ha enviado un código de verificación a tu correo.")
            );
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login/verify-otp")
    public ResponseEntity<JwtResponse> verifyOtp(@RequestBody VerifyOtpRequest request){

        //Buscar usuario por username
        Usuario usuario = usuarioRepository.findOneByUsername(request.getUsername());

        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        //Validar el OTP( lanzamos la excepcion si algo esta mal)
        otpService.validateOtp(usuario, request.getOtp());

        //Si es valido, ahora si generamos JWT

        final UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}

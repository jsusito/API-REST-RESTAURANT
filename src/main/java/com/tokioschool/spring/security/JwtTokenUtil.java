package com.tokioschool.spring.security;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.tokioschool.spring.security.config.JwtConfigurationProperties;
import com.tokioschool.spring.security.config.KeyGenerator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
	
	private final JwtConfigurationProperties jwtConfiguration;
	
	public String generateToken(String userName) throws JOSEException {

		//Tiempo de validez del token en segundos en minutos
		final long JWT_TOKEN_VALIDITY = jwtConfiguration.expiredTime() * 60;
				
		// Definimos los claims del token
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .expirationTime(Date.from(Instant.now().plusSeconds(JWT_TOKEN_VALIDITY)))
                .build();

        // Creamos el objeto JWS firmado con los claims y la clave secreta
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(claimsSet.toJSONObject());
        // JWSSigner signer = new MACSigner(jwtConfiguration.secret());
        JWSSigner signer = new MACSigner(KeyGenerator.getKeyJwtGenerator());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(signer);

        // Devolvemos el token
        return jwsObject.serialize();
    }

    // Accedemos a la informacion del token

    // Fecha de expiracion del token
    public Date getExpirationDate(String token) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        return claimsSet.getExpirationTime();
    }

    // Obtenemos el usuario del token
    public String getUsername(String token) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        return claimsSet.getSubject();
    }

    // Verificamos la validez del token
    public boolean isTokenExpired(String token) throws ParseException, JOSEException {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    /*
     * Comprueba si el token corresponde al usuario correcto y si la fecha no ha
     * expirado. Sino falso en caso contrario.
     */
    public boolean validateToken(String token, UserDetails userDetails) throws ParseException, JOSEException {
        final String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && (!isTokenExpired(token));
    }
		
}

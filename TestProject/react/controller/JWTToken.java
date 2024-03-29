
package react.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import react.exception.NotAuthorizedException;

import java.security.Key;
import java.util.Date;

public class JWTToken {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Crée un nouveau token avec un rôle spécifié
     * @param subject
     * @param role
     * @return
     */
    public static String create(Integer subject, String role) {
        return create(String.valueOf(subject), role);
    }

    /**
     * Crée un nouveau token avec un rôle spécifié
     * @param subject
     * @param role
     * @return
     */
    public static String create(String subject, String role) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    
    /**
     * Crée un nouveau token avec un rôle spécifié
     * @param subject
     * @param role
     * @return
     */
    public static String create(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Crée un nouveau token avec un rôle spécifié
     * @param subject
     * @param role
     * @return
     */
    public static String create(Integer subject) {
        return create(String.valueOf(subject));
    }

    /**
     * Retourne le sujet si le token est valide
     * @param jwt
     * @return
     * @throws NotAuthorizedException
     */
    public static int check(String jwt) throws NotAuthorizedException {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            return extractSubject(jwt);
        } catch (Exception e) {
            throw new NotAuthorizedException(e.getMessage());
        }
    }

    /**
     * Retourne le sujet et le rôle si le token est valide
     * @param jwt
     * @return
     * @throws NotAuthorizedException
     */
    public static int checkWithRole(HttpServletRequest request, String role) throws NotAuthorizedException {
        try {
            String jwt = request.getHeader("Authorization");
            if (jwt != null && jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            } else {
                throw new NotAuthorizedException("NOT AUTHORIZED");
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            int subject = Integer.parseInt(claims.getBody().getSubject());
            if (!role.equals(claims.getBody().get("role", String.class))) {
                throw new NotAuthorizedException("NOT AUTHORIZED");
            }
            return subject;
        } catch (Exception e) {
            throw new NotAuthorizedException(e.getMessage());
        }
    }

    public static int checkBearer(HttpServletRequest request) throws NotAuthorizedException {
        return checkBearer(request.getHeader("Authorization"));
    }

    /**
     * Vérifie si le token est au format Bearer et retourne le sujet si le token est valide
     * @param jwt
     * @return
     * @throws NotAuthorizedException
     */
    public static int checkBearer(String jwt) throws NotAuthorizedException {
        try {
            if (jwt != null && jwt.startsWith("Bearer ")) {
                String token = jwt.substring(7);
                return check(token);
            }
        } catch (Exception e) {
            throw new NotAuthorizedException(e.getMessage());
        }
        throw new NotAuthorizedException("NOT AUTHORIZED");
    }

    /**
     * Récupère le sujet dans le token
     * @param jwt
     * @return
     * @throws NotAuthorizedException
     */
    private static int extractSubject(String jwt) throws NotAuthorizedException {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new NotAuthorizedException(e.getMessage());
        }
    }

}
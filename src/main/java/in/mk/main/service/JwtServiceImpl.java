package in.mk.main.service;

import java.security.Key;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.hibernate.id.insert.GetGeneratedKeysDelegate;
import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import in.mk.main.entity.Role;
import in.mk.main.entity.User;
import in.mk.main.exception.JwtTokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.DecryptionKeyRequest;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey;

    public JwtServiceImpl() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            SecretKey key = keyGenerator.generateKey();
            this.secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("roles",
        	    user.getRoles()
        	        .stream()
        	        .map(Role::getName)
        	        .toList()
        	);

        claims.put("status", user.getStatus().getIsActive());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +   60 * 60*60 * 1)) // 10 hours
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String role(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    private Claims extractAllClaims(String token) {
        try {
    	return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        }catch (ExpiredJwtException e) {
			throw new JwtTokenExpiredException("Token is expired");
		}catch (JwtException e) {
			throw new JwtTokenExpiredException("invalid Jwt token");
		}
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equalsIgnoreCase(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}

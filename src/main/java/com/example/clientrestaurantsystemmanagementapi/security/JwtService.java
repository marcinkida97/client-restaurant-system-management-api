package com.example.clientrestaurantsystemmanagementapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "O4EfqTBpu9cSs0mjUdjo7ourY1udWwT8kftPOivFz4ohQZGD+hRk6Pe+y5UZKyVVHio+KsXpSUm7dYkpjy4nBIfnyc5QMv21G49Iw1Nhvid3jPwjpOMFrd0af6qwICMp+bjZWjLQ6MeTdv3niW/LA83UTKlQK12Ivev+t1z6tkBb443ceIxpM/METYQ4PnV5M6a/S/BVuKcPTcgE00HLF9UwrsiKYn/y/Gym6b0UIxqfnTqFvbm4597L/Jy8Km2ALnk1kacbcRwvZYapnlM/OR295Yr3W+yDnVnfnvO9+Vu1dRGa4U6Hjv6kTKocMmAGHMbqtw3VdZyjxyDfl6GQwblOwx90z7tCQNXoZAg/Ho+ZPhOjOEPAXgLw7ahYuAbJkzn5oMBM1yoLP/yJwiFw1PilSbCEeyg67v5FM+8yoSzv/CH4nyJG9L92TJHciYh3tTYRxi6qqfgMP0fZcwQTEPjM2wJkXWW8Dp4f3Q/5bD7BwwBETlxt45JS5pip2vkuGVdbKGyzo8BIJIymiUtiZZLWEJrb8uFWrHtw6PwBQ+7uKjDVnCWb28YWW45B4SZyJdixOmEctGlh6QBuqfW0MD/hWcbcfOLU6gupRfPt8Bl1uL89WsETVjq8Q7seNGbm1rn1nEpXB08e8KuaV4RwB3XAKKZtuYSIESXkbGOy6IJnduoH1nrpgPQr9TzvU+x/";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}

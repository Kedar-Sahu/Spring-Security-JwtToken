package in.main.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String secreteKey="";
	
	public JwtService() {
		try {
			KeyGenerator key = KeyGenerator.getInstance("HmacShA256");
			SecretKey sk=key.generateKey();
			secreteKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

	public String generatetoken(String username) {
		
		Map<String,Object> map=new HashMap<>();
		map.put("username", username);
		
		String token=Jwts.builder()
		.claims()
		.add(map)
		.subject(username)
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis()+60*60*10))
		.and()
		.signWith(getKey())
		.compact();
		
		return token;
	}

	private Key getKey() {
		
		byte[] keyByte=Decoders.BASE64.decode(secreteKey);
		
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	private Claims extractAllClaims(String token) {
		Claims claims=Jwts.parser().verifyWith(decryptKey(secreteKey)).build()
		.parseSignedClaims(token)
		.getPayload();
		
		return claims;
	}
	
	private <T> T extractClaim(String token,Function<Claims,T> claimResolver){
		Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private SecretKey decryptKey(String secreteKey2) {
		byte[] keyBytes=Decoders.BASE64.decode(secreteKey2);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	private Date extractExpairation(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username=extractUserName(token);
		Boolean isExpired=isTokenExpired(token);
		if(username.equals(userDetails.getUsername()) && !isExpired) {
			return true;
		}
		return false;
	}

	private Boolean isTokenExpired(String token) {
		Date expiredDate=extractExpairation(token);
		return expiredDate.before(new Date());
	}
}

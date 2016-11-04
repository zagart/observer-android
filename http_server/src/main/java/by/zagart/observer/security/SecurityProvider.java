package by.zagart.observer.security;

import by.zagart.observer.database.entities.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class provides methods related to security of application.
 *
 * @author zagart
 */
public class SecurityProvider {
    public static final String KEY = "my_key";
    public static final String TOKEN_CREATE_DATE = "token_create_date";
    public static final String TOKEN_EXPIRATION_DATE = "token_expiration_date";
    public static final int TOKEN_EXPIRATION_YEAR = 100;

    private SecurityProvider() {
    }

    public static String getToken(User pUser, String pPassword) {
        Map<String, Object> tokenData = new HashMap<>();
        if (pPassword.equals(pUser.getPassword())) {
            tokenData.put(User.Fields.ID, pUser.getId());
            tokenData.put(User.Fields.LOGIN, pUser.getLogin());
            tokenData.put(User.Fields.ROLE, pUser.getRole());
            tokenData.put(TOKEN_CREATE_DATE, new Date().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, TOKEN_EXPIRATION_YEAR);
            tokenData.put(TOKEN_EXPIRATION_DATE, calendar.getTime());
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setExpiration(calendar.getTime());
            jwtBuilder.setClaims(tokenData);
            String key = KEY;
            String token = jwtBuilder.signWith(SignatureAlgorithm.HS256, key).compact();
            return token;
        } else {
            return null;
        }
    }
}

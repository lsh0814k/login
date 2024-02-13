package fem.login.oauth2.userinfo;

import fem.login.domain.model.vo.SocialType;
import fem.login.oauth2.OAuth2UserInfo;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getId() {
        return String.valueOf(getValue("id"));
    }

    @Override
    public String getNickname() {
        return String.valueOf(getValue("nickname"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(getValue("email"));
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
    }

    private Object getValue(String key) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response != null) {
            return String.valueOf(response.get(key));
        }

        return "";
    }
}

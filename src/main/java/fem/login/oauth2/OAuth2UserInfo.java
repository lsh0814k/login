package fem.login.oauth2;

import fem.login.domain.model.vo.SocialType;

public interface OAuth2UserInfo {
    String getId();
    String getNickname();
    String getEmail();
    SocialType getSocialType();
}

package fem.login.oauth2;

import fem.login.domain.model.Users;
import fem.login.oauth2.userinfo.NaverOAuth2UserInfo;
import fem.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser 실행 - 로그인 요청");

        OAuth2UserService service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttribute = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Users user = saveUser(registrationId, attributes);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleUser().toString())),
                attributes,
                userNameAttribute
        );
    }

    private Users saveUser(String registrationId, Map<String, Object> attributes) {
        OAuth2UserInfo oAuthUserInfo = findOAuthUserInfo(registrationId, attributes);
        Optional<Users> optional = userRepository.findByEmailAndSocialType(oAuthUserInfo.getEmail(), oAuthUserInfo.getSocialType());
        if (optional.isPresent()) {
            return optional.get();
        }

        Users user = Users.create(oAuthUserInfo.getNickname(), oAuthUserInfo.getEmail(), oAuthUserInfo.getSocialType());
        return userRepository.save(user);
    }

    private OAuth2UserInfo findOAuthUserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.toUpperCase().equals("NAVER")) {
            return new NaverOAuth2UserInfo(attributes);
        }

        return null;
    }
}

package fem.login.domain.model;

import fem.login.domain.model.vo.SocialType;
import fem.login.domain.model.vo.RoleUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static fem.login.domain.model.vo.RoleUser.*;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE)
public class Users {
    @Id @GeneratedValue
    private Long id;
    private String nickName;
    private String email;
    @Enumerated(STRING)
    private SocialType socialType;
    @Enumerated(STRING)
    private RoleUser roleUser;

    public static Users create(String nickName, String email, SocialType socialType) {
        return Users.builder()
                .nickName(nickName)
                .email(email)
                .socialType(socialType)
                .roleUser(GUEST)
                .build();
    }
}

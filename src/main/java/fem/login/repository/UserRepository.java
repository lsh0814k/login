package fem.login.repository;

import fem.login.domain.model.Users;
import fem.login.domain.model.vo.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailAndSocialType(String email, SocialType socialType);
}

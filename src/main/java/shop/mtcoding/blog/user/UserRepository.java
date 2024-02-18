package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save(UserRequest.JoinDTO requestDTO) {
        Query query = em.createNativeQuery("insert into user_tb(username, password, email)values (?,?,?)");
        query.setParameter(1, requestDTO.getUsername());
        query.setParameter(2, requestDTO.getPassword());
        query.setParameter(3, requestDTO.getEmail());

        query.executeUpdate();
    }

    public User findByUsername(String username) {
        Query query = em.createNativeQuery("select * from user_tb where username = ?", User.class);
        query.setParameter(1, username);

        try {
            User user = (User) query.getResultList();
            return user;
        } catch (Exception e) {
            return null;
        }
    }


    public User findByUsernameAndPassword(UserRequest.LoginDTO requsetDTO) {
        String a = """
                select * from user_tb where username = ? And password = ?
                """;
        Query query = em.createNativeQuery(a, User.class);
        query.setParameter(1, requsetDTO.getUsername());
        query.setParameter(2, requsetDTO.getPassword());
        User user = (User) query.getSingleResult();
        return user;
    }
}

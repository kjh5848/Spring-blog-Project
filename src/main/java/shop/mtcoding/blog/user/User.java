package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity//엔터티를 만든다.즉 테이블을 만든다.
@Data// get,set,tostring이 된다.
@Table(name = "user_tb")
public class User {
    @Id//프라이머리 키 지정 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가 어노테이션
    private int id;

    @Column(unique = true)
    private String username;

    @Column(length = 20, nullable = false)
    private String password;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

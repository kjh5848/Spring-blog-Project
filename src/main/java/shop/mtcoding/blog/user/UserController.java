package shop.mtcoding.blog.user;


import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final HttpSession session;

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requsetDTO,HttpServletRequest request) {
        // 유효성 검사
        if (requsetDTO.getUsername().length() < 3) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "username의 길이는 3자 이상입니다.");
            return "error/40x";
        }

        User user = userRepository.findByUsernameAndPassword(requsetDTO);

        if (user == null) {
            request.setAttribute("status", 401);
            request.setAttribute("msg", "로그인 권한이 없습니다.");
            return "error/40x";
        } else {
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        }

    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO, HttpServletRequest request) {

        //1. 유효성 검사 바디데이터가 없으면 필요없음.
        if (requestDTO.getUsername().length() < 3) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "username의 길이는 3자 이상입니다.");
            return "error/40x";
        }

        if (requestDTO.getPassword().length() < 3) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "password의 길이는 3자 이상입니다.");
            return "error/40x";
        }

        //2. 모델 위임
        User user = userRepository.findByUsername(requestDTO.getUsername());
        if (user == null) {
            userRepository.save(requestDTO);
        } else {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "이미 존재하는 username입니다.");
            return "error/40x";
        }

        return "redirect:/loginForm";
    }

    public String join(UserRequest.JoinDTO requestDTO) {
        //1. 유효성 검사 바디데이터가 없으면 필요없음.

        //2. 모델 위임
        userRepository.save(requestDTO);

        return "user/loginForm";

    }

    @GetMapping("/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}

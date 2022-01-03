package life.majiang.community.controller;
import life.majiang.community.dto.EmailUserDto;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MailLoginController {
    @Autowired
    private MailService mailService;

    // 跳转注册页面
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // 跳转登录页面
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // 发送邮件
    @PostMapping("/sendCode")
    @ResponseBody
    public ResultDTO sendEmail(
            @RequestBody EmailUserDto emailUserDto
            , HttpSession httpSession) {
        return mailService.sendMimeMail(emailUserDto.getEmail(), httpSession);
    }

    // 新用户注册
    @PostMapping("/register")
    @ResponseBody
    public ResultDTO register(@RequestBody EmailUserDto emailUserDto,
                              HttpSession session,
                              HttpServletResponse response) {
        return mailService.register(emailUserDto, session, response);
    }

    // 点击登录
    @ResponseBody
    @PostMapping("/login")
    public ResultDTO login(
            @RequestBody EmailUserDto emailUserDto,
            HttpServletResponse response) {
        log.info("要验证的邮箱：" + emailUserDto.getEmail());
        if (mailService.loginInByPass(emailUserDto.getEmail(), emailUserDto.getPassword(), response)) {
            return ResultDTO.okOf();
        }
        return ResultDTO.errorOf(CustomizeErrorCode.WRONG_PASSWORD);
    }
}

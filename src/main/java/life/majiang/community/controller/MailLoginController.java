package life.majiang.community.controller;

import life.majiang.community.dto.EmailUserDto;
import life.majiang.community.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MailLoginController {
    @Autowired
    private MailService mailService;

    // 登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    // 发送邮件
    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession){
        mailService.sendMimeMail(email, httpSession);
        return "success";
    }

    // 点击校验验证码
    @PostMapping("/register")
    @ResponseBody
    public String register(EmailUserDto emailUserDto, HttpSession session, HttpServletResponse response){
        mailService.loginByCode(emailUserDto,session,response);
        return "success";
    }

    // 点击登录
    @PostMapping("/loginCheck")
    @ResponseBody
    public String login(
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true)  String password,
            HttpServletResponse response,
            Model model){
        System.out.println("邮箱：" + email);
        if (mailService.loginInByPass(email,password,response)){
            return "redirect:/";
        }
        model.addAttribute("error", "用户不存在或者密码错误！");
        return "";
    }
}

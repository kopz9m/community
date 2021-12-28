package life.majiang.community.service;

import life.majiang.community.dto.EmailUserDto;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import life.majiang.community.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.sun.activation.registries.LogSupport.log;

@Slf4j
@Service
public class MailService {

    @Value("${myHost}")
    private String host;
    @Value("${server.port}")
    private String port;
    @Autowired
    private UserMapper userMapper;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    // 给前端输入的邮箱，发送验证码
    public ResultDTO sendMimeMail(String email, HttpSession session) {

        // 用户是否已经存在
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email.trim());
        if (userMapper.countByExample(userExample) > 0) {
            return ResultDTO.errorOf(CustomizeErrorCode.EMAIL_ALREADY_EXISTS);
        }

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证您的邮箱");//主题
            //生成验证码并发送
            String code = FileUtils.randomCode();
            session.setAttribute("email", email);
            session.setAttribute("codeSent", code);
            mailMessage.setText("您的验证码是：" + code);
            mailMessage.setTo(email);
            mailMessage.setFrom(from);
            mailSender.send(mailMessage);
            return ResultDTO.okOf();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.errorOf(CustomizeErrorCode.SEND_MAIL_FAIL);
        }
    }

    // 用户注册
    public ResultDTO register(EmailUserDto emailUserDto, HttpSession session, HttpServletResponse response) {
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String codeSent = (String) session.getAttribute("codeSent");
        String codeToCheck = emailUserDto.getVerificationCode();
        log.info("输入邮箱" + emailUserDto.getEmail());
        // 检查输入
        if (email == null || !email.equals(emailUserDto.getEmail())) {
            return ResultDTO.errorOf(CustomizeErrorCode.EMAIL_DIFFER);
        } else if (!codeSent.equals(codeToCheck)) {
            return ResultDTO.errorOf(CustomizeErrorCode.WRONG_VERIFICATION_CODE);
        }
        log.info("验证码验证成功");

        //保存数据
        User user = new User();
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setEmail(email);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setName(emailUserDto.getName());
        user.setAvatarUrl("http://" + host + ":" + port + "/file/get/?type=avatars&filename="
                + FileUtils.getDefaultAvatar());
        userMapper.insert(user);
        log.info("数据库添加用户成功" + email);
        response.addCookie(new Cookie("token", token));
        return ResultDTO.okOf();
    }

    // 密码校验，成功则加入 cookie
    public boolean loginInByPass(String email, String password, HttpServletResponse response) {
        // 根据邮箱查找用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email.trim());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0) {
            log.error("用户不存在！");
            return false;
        }

        if (users.size() > 1) {
            log.error("用户重复！");
            return false;
        }

        // 比较密码
        if (password.trim().equals(users.get(0).getPassword().trim())) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            // 更新 cookie
            user.setToken(token);
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateByExampleSelective(user, userExample);
            response.addCookie(new Cookie("token", token));
            return true;
        }
        log.error("密码错误!");
        return false;
    }
}

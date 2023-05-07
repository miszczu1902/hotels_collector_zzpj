package pl.lodz.p.it.zzpj.hotelscollector.user.activation.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.zzpj.hotelscollector.utils.CustomMailSenderListener;

@Component
public class RegistrationListener extends CustomMailSenderListener<RegistrationCompleteEvent> {
    private static final String SUBJECT = "Registration Confirmation in Hotel Collectors";
    private static final String MESSAGE = """
      Hello %s,
      You registered an account on Hotel Collectors, before being able to use your account you need to verify that this is your email address by clicking here:
      %s
      Kind Regards, Liki
      """;
    private final String appUrl;

    public RegistrationListener(
            JavaMailSender mailSender, @Value("${spring.mail.username}") String sendFrom, @Value("${app.url}") String appUrl
    ) {
        super(mailSender, sendFrom);
        this.appUrl = appUrl;
    }

    @Override
    public String getSendTo(RegistrationCompleteEvent event) {
        return event.getUser().email();
    }

    @Override
    public String getMailSubject() {
        return SUBJECT;
    }

    @Override
    public String getMailMessage(RegistrationCompleteEvent event) {
        return MESSAGE.formatted(event.getUser().username(),
                appUrl + "/users/activation" + "?token=" + event.getUser().token());
    }
}


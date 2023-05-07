package pl.lodz.p.it.zzpj.hotelscollector.user.activation.token;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private final TokenUserDTO user;

    public RegistrationCompleteEvent(TokenUserDTO user) {
        super(user);
        this.user = user;
    }

}


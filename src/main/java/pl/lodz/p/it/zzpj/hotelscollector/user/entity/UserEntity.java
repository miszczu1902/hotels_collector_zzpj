package pl.lodz.p.it.zzpj.hotelscollector.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.zzpj.hotelscollector.utils.UserRole;

import java.io.Serializable;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Size(max = 254)
    @Email
    @Column
    private String email;

    @Size(min = 6, max = 32)
    @NotNull
    @Column
    private String username;

    @Size(max = 60)
    @NotNull
    @Column
    private String password;

    @Setter
    @NotNull
    @Column
    private Boolean isActive;

    @NotNull
    @Setter
    @Column
    private Boolean isEnable;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    @Setter
    private UserRole role;

    public UserEntity(String email, String username, String password, Boolean isActive, Boolean isEnable, UserRole role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.isEnable = isEnable;
        this.role = role;
    }
}


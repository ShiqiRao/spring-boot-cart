package com.example.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 5752474342578373213L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "*请提供有效的电子邮件")
    @NotEmpty(message = "*请提供电子邮件")
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "*您的密码必须至少包含5个字符")
    @NotEmpty(message = "*请提供您的密码")
    @JsonIgnore
    private String password;

    @Column(name = "username", nullable = false, unique = true)
        @Length(min = 5, message = "*您的用户名必须至少包含5个字符")
    @NotEmpty(message = "*请提供您的名字")
    private String username;

    @Column(name = "name")
    @NotEmpty(message = "*请提供您的名字")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "*请提供您的姓氏")
    private String lastName;

    @Column(name = "active", nullable = false)
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Order> orderList;

}

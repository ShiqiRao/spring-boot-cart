package com.example.cart.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 7798851301444068917L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "payment")
    private BigDecimal payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /*一对多，级联保存*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order",cascade = CascadeType.PERSIST)
    private List<Sold> soldList;

}

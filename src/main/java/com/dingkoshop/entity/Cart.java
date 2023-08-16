package com.dingkoshop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Table(name="cart")
@Getter @Setter @ToString
public class Cart extends BaseEntity{

    @Id @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //장바구니 엔티티가 회원 엔티티를 참조하는 단방향 매핑
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}

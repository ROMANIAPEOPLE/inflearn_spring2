package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;


    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent /100;
        }else  {
            return 0;
        }

    }
}
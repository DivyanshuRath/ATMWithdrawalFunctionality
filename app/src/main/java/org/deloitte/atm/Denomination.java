package org.deloitte.atm;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Builder
public class Denomination {
    

    private Integer value;

    @Setter
    private Currency currency;


    public void setValue(Integer value){
        if(value == null || value <=0 || value %10 != 0 || value > Integer.MAX_VALUE){
            throw new RuntimeException("Invalid value");
        }
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Denomination)) return false;
        Denomination that = (Denomination) o;
        return value.equals(that.value) && currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Denomination{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }
}

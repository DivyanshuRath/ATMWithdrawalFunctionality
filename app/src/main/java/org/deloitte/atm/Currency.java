package org.deloitte.atm;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Currency {
    String currency;
    String symbol;
}

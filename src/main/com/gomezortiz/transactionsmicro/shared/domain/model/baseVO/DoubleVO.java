package com.gomezortiz.transactionsmicro.shared.domain.model.baseVO;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@EqualsAndHashCode
public class DoubleVO {

    private Double value;
}

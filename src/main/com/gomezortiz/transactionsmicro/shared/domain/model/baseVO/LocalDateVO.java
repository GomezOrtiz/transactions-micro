package com.gomezortiz.transactionsmicro.shared.domain.model.baseVO;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@EqualsAndHashCode
public class LocalDateVO {

    private LocalDate value;
}

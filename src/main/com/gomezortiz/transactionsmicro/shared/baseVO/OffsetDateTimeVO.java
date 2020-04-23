package com.gomezortiz.transactionsmicro.shared.baseVO;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@EqualsAndHashCode
public class OffsetDateTimeVO {

    private OffsetDateTime value;
}

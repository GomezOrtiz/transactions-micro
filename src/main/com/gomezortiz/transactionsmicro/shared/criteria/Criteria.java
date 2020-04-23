package com.gomezortiz.transactionsmicro.shared.criteria;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Criteria {

    private final OrderType orderType;
    private final OrderBy orderBy;
}

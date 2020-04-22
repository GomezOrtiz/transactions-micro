package com.gomezortiz.transactionsmicro.shared.domain.model.criteria;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Criteria {

    private final OrderType orderType;
    private final OrderBy orderBy;
}

package su;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public final class TestObj {
    private final Long id;
    private final String name;
}

package com.com.munroes.data.query;

import com.com.munroes.model.Designation;
import com.com.munroes.model.Munro;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Data
public class MunroQuerySpecification {

    private final @Positive Integer limit;
    private final @Positive Double minHeight;
    private final @Positive Double maxHeight;
    private final @Nullable Set<Designation> types;
    private final @Nullable Comparator<Munro> sorter;

    public MunroQuerySpecification(
            final @Positive Integer limit,
            final @Positive Double minHeight,
            final @Positive Double maxHeight,
            final Set<Designation>types,
            final List<Comparator<Munro>> sorters) {

        if (minHeight != null && maxHeight != null
            && maxHeight < minHeight) {
            throw new IllegalArgumentException("Invalid min/max heights");
        }
        if ((limit != null && limit <= 0)
                || (minHeight != null && minHeight <= 0)
                || (maxHeight != null && maxHeight <= 0)) {
            throw new IllegalArgumentException("Parameters must be positive");
        }

        this.limit = limit;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.types = types == null ? null : types;
        this.sorter = sorters == null ? null : new CompositeComparator<>(sorters);
    }
}

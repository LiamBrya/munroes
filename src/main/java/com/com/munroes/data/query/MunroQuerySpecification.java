package com.com.munroes.data.query;

import com.com.munroes.model.Designation;
import com.com.munroes.model.Munro;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

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
            final Set<Designation> types,
            final List<Comparator<Munro>> sorters) {

        Assert.isTrue(limit == null || limit > 0, "'limit' must be positive if present");
        Assert.isTrue(minHeight == null || minHeight > 0, "'minHeight' must be positive if present");
        Assert.isTrue(maxHeight == null || maxHeight > 0, "'limit' must be positive if present");
        Assert.isTrue((minHeight == null || maxHeight == null) || minHeight < maxHeight,
                "Invalid min/max heights: " + minHeight + "/" + maxHeight);

        this.limit = limit;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.types = types;
        this.sorter = sorters == null ? null : new CompositeComparator<>(sorters);
    }
}

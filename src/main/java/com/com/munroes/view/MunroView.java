package com.com.munroes.view;

import com.com.munroes.model.Munro;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public final class MunroView {

    private final String name;
    private final double height;
    private final String category;
    private final String gridReference;

    public MunroView(final Munro model) {
        Assert.notNull(model, "'model' must not be 'null'");

        this.name = model.getName();
        this.height = model.getHeight();
        this.category = model.getDesignation().toString();
        this.gridReference = model.getGridReference().getReference();
    }
}

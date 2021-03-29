package com.com.munroes.data.load;

import com.com.munroes.model.Munro;

import java.util.Set;

public interface MunroDataLoader {

    void loadInto(Set<Munro> munroes);
}

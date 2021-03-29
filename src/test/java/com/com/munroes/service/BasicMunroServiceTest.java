package com.com.munroes.service;

import com.com.munroes.data.MunroStore;
import com.com.munroes.data.query.MunroQuerySpecification;
import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BasicMunroServiceTest {

    private MunroStore munroStore = mock(MunroStore.class);
    private BasicMunroService service = new BasicMunroService(this.munroStore);

    @Test
    void testQueryForwardedToStoreUntouched() {
        final MunroQuerySpecification expected = new MunroQuerySpecification(null, null, null, null, null);

        this.service.query(expected);

        verify(this.munroStore).query(same(expected));
    }
}

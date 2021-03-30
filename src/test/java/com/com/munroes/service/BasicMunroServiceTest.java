package com.com.munroes.service;

import com.com.munroes.data.MunroStore;
import com.com.munroes.data.query.MunroQuerySpecification;
import com.com.munroes.model.Designation;
import com.com.munroes.model.GridReference;
import com.com.munroes.model.Munro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BasicMunroServiceTest {

    private final MunroStore munroStore = mock(MunroStore.class);
    private final BasicMunroService service = new BasicMunroService(this.munroStore);

    @Test
    void testQueryForwardedToStoreUntouched() {
        final MunroQuerySpecification expected =
                new MunroQuerySpecification(null, null, null, null, null);

        this.service.query(expected);

        verify(this.munroStore).query(same(expected));
    }

    @Test
    void testResultReturnedFromStoreUntouched() {
        final List<Munro> expected =
                Collections.singletonList(
                        new Munro("Dave", GridReference.of("NN112233"), 200.0D, Designation.MUN));

        doReturn(expected).when(this.munroStore).query(any());

        final List<Munro> received = this.service.query(
                new MunroQuerySpecification(null, null, null, null, null));

        Assertions.assertSame(expected, received);
    }
}

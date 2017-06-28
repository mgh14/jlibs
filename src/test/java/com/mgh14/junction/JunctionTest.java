package com.mgh14.junction;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JunctionTest {
    
    private static final String NONNULL_VALUE = "Nonnull Value";
    private static final Exception TEST_EXCEPTION =
            new RuntimeException("Junction Runtime Exception");

    @Test
    public void testOf_expectedNonNullValue() {
        assertJunctionValue(Junction.of(NONNULL_VALUE), NONNULL_VALUE);
    }

    @Test
    public void testOf_expectedNullValue() {
        assertJunctionValue(Junction.of(), null);
    }

    @Test
    public void testEmpty_expectedNullValue() {
        assertJunctionValue(Junction.empty(), null);
    }

    @Test
    public void testOf_junctionOfJunction() {
        assertJunctionValue(Junction.of(Junction.empty()), Junction.empty());
    }

    @Test
    public void testGetValue_nonNullOperand() {
        assertThat(Junction.of(NONNULL_VALUE).getValue(), equalTo(NONNULL_VALUE));
    }

    @Test
    public void testGetValue_nullOperand() {
        assertThat(Junction.of().getValue(), is(nullValue()));
    }

    @Test
    public void testIfSucceeds_nonEmptyOperand_nullPredicate() {
        Junction<String> result = Junction.of(NONNULL_VALUE).ifSucceeds(null);
        assertJunctionValue(result, null);
    }
    
    @Test
    public void testIfSucceeds_emptyOperand_nullPredicate() {
        Junction<String> result = Junction.of((String) null).ifSucceeds(null);
        assertJunctionValue(result, null);
    }

    @Test
    public void testIfSucceeds_nonEmptyOperand_conditionSucceeds() {
        Junction<String> result = Junction.of(NONNULL_VALUE).ifSucceeds(s -> true);
        assertJunctionValue(result, NONNULL_VALUE);
    }

    @Test
    public void testIfSucceeds_emptyOperand_conditionSucceeds() {
        Junction<String> result = Junction.of((String) null).ifSucceeds(s -> true);
        assertJunctionValue(result, null);
    }

    @Test
    public void testIfSucceeds_nonEmptyOperand_conditionFails() {
        Junction<String> result = Junction.of(NONNULL_VALUE).ifSucceeds(s -> false);
        assertJunctionValue(result, null);
    }
    
    @Test
    public void testIfSucceeds_emptyOperand_conditionFails() {
        Junction<String> result = Junction.of((String) null).ifSucceeds(s -> false);
        assertJunctionValue(result, null);
    }

    @Test
    public void testIfFails_nonEmptyOperand_nullPredicate() {
        Junction<String> result = Junction.of(NONNULL_VALUE).ifFails(null);
        assertJunctionValue(result, NONNULL_VALUE);
    }

    @Test
    public void testIfFails_emptyOperand_nullPredicate() {
        Junction<String> result = Junction.of((String) null).ifFails(null);
        assertJunctionValue(result, null);
    }

    @Test
    public void testIfFails_nonEmptyOperand_conditionSucceeds() {
        Junction<String> result = Junction.of(NONNULL_VALUE).ifFails(s -> true);
        assertJunctionValue(result, null);
    }

    @Test
    public void testIfFails_emptyOperand_conditionSucceeds() {
        Junction<String> result = Junction.of((String) null).ifFails(s -> true);
        assertJunctionValue(result, null);
    }

    @Test
    public void testIfFails_nonEmptyOperand_conditionFails() {
        Junction<String> result = Junction.of(NONNULL_VALUE).ifFails(s -> false);
        assertJunctionValue(result, NONNULL_VALUE);
    }

    @Test
    public void testIfFails_emptyOperand_conditionFails() {
        Junction<String> result = Junction.of((String) null).ifFails(s -> false);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_nonNullOperand_nonNullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE).doOperation(String::toLowerCase);
        assertJunctionValue(result, NONNULL_VALUE.toLowerCase());
    }

    @Test
    void testDoOperation_nullOperand_nonNullFunction() {
        Junction<String> result = Junction.of((String) null).doOperation(String::toUpperCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_nonNullOperand_nullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE).doOperation(null);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_nullOperand_nullFunction() {
        Junction<String> result = Junction.of((String) null).doOperation(null);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_checkForNull_nonNullOperand_nonNullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doOperation(true, String::toLowerCase);
        assertJunctionValue(result, NONNULL_VALUE.toLowerCase());
    }

    @Test
    void testDoOperation_checkForNull_nullOperand_nonNullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doOperation(true, String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_checkForNull_nonNullOperand_nullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doOperation(true, null);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_checkForNull_nullOperand_nullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doOperation(true, null);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_noCheckForNull_nonNullOperand_nonNullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doOperation(false, String::toLowerCase);
        assertJunctionValue(result, NONNULL_VALUE.toLowerCase());
    }

    @Test
    void testDoOperation_noCheckForNull_nullOperand_nonNullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doOperation(false, String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_noCheckForNull_nonNullOperand_nullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doOperation(false, null);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_noCheckForNull_nullOperand_nullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doOperation(false, String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoOperation_sameType_checkForNull_nonNullOperand_nonNullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doSameTypeOperation(true, String::toLowerCase);
        assertJunctionValue(result, NONNULL_VALUE.toLowerCase());
    }

    @Test
    void testDoSameTypeOperation_checkForNull_nullOperand_nonNullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doSameTypeOperation(true, String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoSameTypeOperation_checkForNull_nonNullOperand_nullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doSameTypeOperation(true, null);
        assertJunctionValue(result, NONNULL_VALUE);
    }

    @Test
    void testDoSameTypeOperation_checkForNull_nullOperand_nullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doSameTypeOperation(true, null);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoSameTypeOperation_noCheckForNull_nonNullOperand_nonNullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doSameTypeOperation(false, String::toLowerCase);
        assertJunctionValue(result, NONNULL_VALUE.toLowerCase());
    }

    @Test
    void testDoSameTypeOperation_noCheckForNull_nullOperand_nonNullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doSameTypeOperation(false, String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testDoSameTypeOperation_noCheckForNull_nonNullOperand_nullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE)
                .doSameTypeOperation(false, null);
        assertJunctionValue(result, NONNULL_VALUE);
    }

    @Test
    void testDoSameTypeOperation_noCheckForNull_nullOperand_nullFunction() {
        Junction<String> result = Junction.of((String) null)
                .doSameTypeOperation(false, String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testThenDo_nonNullOperand_nonNullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE).thenDo(String::toLowerCase);
        assertJunctionValue(result, NONNULL_VALUE.toLowerCase());
    }

    @Test
    void testThenDo_nullOperand_nonNullFunction() {
        Junction<String> result = Junction.of((String) null).thenDo(String::toLowerCase);
        assertJunctionValue(result, null);
    }

    @Test
    void testThenDo_nonNullOperand_nullFunction() {
        Junction<String> result = Junction.of(NONNULL_VALUE).thenDo(null);
        assertJunctionValue(result, null);
    }

    @Test
    void testThenDo_nullOperand_nullFunction() {
        Junction<String> result = Junction.of((String) null).thenDo(null);
        assertJunctionValue(result, null);
    }

    @Test
    public void testOrElseThrow_returnJunction_nonNullExceptionParam() {
        Exception result = assertThrows(TEST_EXCEPTION.getClass(),
                () -> Junction.of().orElseThrow_returnJunction(TEST_EXCEPTION));
        assertThat(result, sameInstance(TEST_EXCEPTION));
    }

    @Test
    public void tetOrElseThrow_returnJunction_nullExceptionParam() {
        assertThrows(NullPointerException.class,
                () -> Junction.of().orElseThrow_returnJunction(null));
    }

    @Test
    public void testOrElseThrow_returnValue_nonNullExceptionParam() {
        Exception result = assertThrows(TEST_EXCEPTION.getClass(),
                () -> Junction.of().orElseThrow_returnValue(TEST_EXCEPTION));
        assertThat(result, sameInstance(TEST_EXCEPTION));
    }

    @Test
    public void testOrElseThrow_returnValue_nullExceptionParam() {
        assertThrows(NullPointerException.class,
                () -> Junction.of().orElseThrow_returnValue(null));
    }

    @Test
    public void testStream_nonNullOperand() {
        Stream<String> stream = Junction.of(NONNULL_VALUE).stream();
        Optional<String> streamResult = stream.findAny();
        assertThat(streamResult.isPresent(), is(equalTo(true)));
    }

    @Test
    public void testStream_nullOperand() {
        assertThrows(NullPointerException.class, () -> {
            Stream<String> streamResult = Junction.of((String) null).stream();
            streamResult.findAny();
        });
    }

    @Test
    public void testEquals_nullOther() {
        boolean result = Junction.of().equals(null);
        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void testEquals_otherNotInstanceOfJunction() {
        boolean result = Junction.of().equals(new Object());
        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void testEquals_otherIsThis() {
        Junction<String> junction = Junction.of((String) null);
        boolean result = junction.equals(junction);
        assertThat(result, is(equalTo(true)));
    }

    @Test
    public void testEquals_bothOperandsNull() {
        Junction<String> junctionOne = Junction.of((String) null);
        Junction<String> junctionTwo = Junction.of((String) null);

        boolean result = junctionOne.equals(junctionTwo);
        assertThat(result, is(equalTo(true)));
    }

    @Test
    public void testEquals_thisOperandNull_otherNonNull() {
        Junction<String> junctionOne = Junction.of((String) null);
        Junction<String> junctionTwo = Junction.of(NONNULL_VALUE);

        boolean result = junctionOne.equals(junctionTwo);
        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void testEquals_thisOperandNotNull_otherNull() {
        Junction<String> junctionOne = Junction.of(NONNULL_VALUE);
        Junction<String> junctionTwo = Junction.of((String) null);

        boolean result = junctionOne.equals(junctionTwo);
        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void testEquals_bothOperandsNotNull() {
        // Note: we assume with confidence that the java.lang.String#equals()
        // method is implemented correctly
        Junction<String> junctionOne = Junction.of(NONNULL_VALUE);
        Junction<String> junctionTwo = Junction.of(NONNULL_VALUE);

        boolean result = junctionOne.equals(junctionTwo);
        assertThat(result, is(equalTo(true)));    }

    @Test
    public void testHashCode_nonEmptyOperand() {
        assertThat(Junction.of(NONNULL_VALUE).hashCode(),
                equalTo(NONNULL_VALUE.hashCode()));
    }

    @Test
    public void testHashCode_emptyOperand() {
        assertThat(Junction.of().hashCode(), equalTo(112358));
    }
    
    private static <T> void assertJunctionValue(Junction<T> actual, T expected) {
        assertThat((T) actual.getValue(), equalTo(expected));
    }
}

/*
 * 版权所有 2024 Matrix。
 * 保留所有权利。
 */
package net.matrix.java.lang;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ObjectMxTest {
    @Test
    public void testIfNull() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        Runnable action = () -> runned.setTrue();

        ObjectMx.ifNull(null, action);
        assertThat(runned.booleanValue()).isTrue();
        runned.setFalse();

        ObjectMx.ifNull(object, action);
        assertThat(runned.booleanValue()).isFalse();
    }

    @Test
    public void testIfNotNull() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        Consumer action = o -> runned.setTrue();

        ObjectMx.ifNotNull(null, action);
        assertThat(runned.booleanValue()).isFalse();

        ObjectMx.ifNotNull(object, action);
        assertThat(runned.booleanValue()).isTrue();
    }

    @Test
    public void testIfNullOrElse() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        MutableBoolean elseRunned = new MutableBoolean();
        Runnable action = () -> runned.setTrue();
        Consumer elseAction = o -> elseRunned.setTrue();

        ObjectMx.ifNullOrElse(null, action, elseAction);
        assertThat(runned.booleanValue()).isTrue();
        assertThat(elseRunned.booleanValue()).isFalse();
        runned.setFalse();

        ObjectMx.ifNullOrElse(object, action, elseAction);
        assertThat(runned.booleanValue()).isFalse();
        assertThat(elseRunned.booleanValue()).isTrue();
    }

    @Test
    public void testIfNullOrTest() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        Predicate predicate = o -> {
            runned.setTrue();
            return false;
        };

        assertThat(ObjectMx.ifNullOrTest(null, predicate)).isTrue();
        assertThat(runned.booleanValue()).isFalse();

        assertThat(ObjectMx.ifNullOrTest(object, predicate)).isFalse();
        assertThat(runned.booleanValue()).isTrue();
    }

    @Test
    public void testIfNotNullAndTest() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        Predicate predicate = o -> {
            runned.setTrue();
            return true;
        };

        assertThat(ObjectMx.ifNotNullAndTest(null, predicate)).isFalse();
        assertThat(runned.booleanValue()).isFalse();

        assertThat(ObjectMx.ifNotNullAndTest(object, predicate)).isTrue();
        assertThat(runned.booleanValue()).isTrue();
    }

    @Test
    public void testIfNotNullMap() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        Function function = o -> {
            runned.setTrue();
            return 1;
        };

        assertThat(ObjectMx.ifNotNullMap(null, function)).isNull();
        assertThat(runned.booleanValue()).isFalse();

        assertThat(ObjectMx.ifNotNullMap(object, function)).isEqualTo(1);
        assertThat(runned.booleanValue()).isTrue();
    }

    @Test
    public void testIfNullThrow() {
        Object object = new Object();
        MutableBoolean runned = new MutableBoolean();
        Supplier<RuntimeException> supplier = () -> {
            runned.setTrue();
            return new RuntimeException();
        };

        assertThat(ObjectMx.ifNullThrow(object, supplier)).isSameAs(object);
        assertThat(runned.booleanValue()).isFalse();

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> ObjectMx.ifNullThrow(null, supplier));
        assertThat(runned.booleanValue()).isTrue();
    }

    @Test
    public void testIfElse() {
        Object object1 = new Object();
        Object object2 = new Object();

        assertThat(ObjectMx.ifElse(true, object1, object2)).isSameAs(object1);
        assertThat(ObjectMx.ifElse(false, object1, object2)).isSameAs(object2);
    }

    @Test
    public void testIfElseGet() {
        Object object1 = new Object();
        Object object2 = new Object();

        assertThat(ObjectMx.ifElseGet(true, () -> object1, () -> object2)).isSameAs(object1);
        assertThat(ObjectMx.ifElseGet(false, () -> object1, () -> object2)).isSameAs(object2);
    }
}

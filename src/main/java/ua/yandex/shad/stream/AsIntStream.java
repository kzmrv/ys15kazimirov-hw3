package ua.yandex.shad.stream;

import ua.yandex.shad.function.IntUnaryOperator;
import ua.yandex.shad.function.IntToIntStreamFunction;
import ua.yandex.shad.function.IntPredicate;
import ua.yandex.shad.function.IntConsumer;
import ua.yandex.shad.function.IntBinaryOperator;
import ua.yandex.shad.arrays.DynamicArray;

public class AsIntStream implements IntStream {

    private DynamicArray<Integer> storage;

    private AsIntStream() {
        storage = new DynamicArray<>();
    }

    public boolean isEmpty() {
        return (storage.isEmpty());
    }

    private AsIntStream addBoxed(Integer... values) {
        storage.add(values);
        return this;
    }

    private AsIntStream addNative(int... values) {
        Integer[] boxed = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            boxed[i] = values[i];
        }
        return addBoxed(boxed);
    }

    private void connect(IntStream stream) {
        int[] source = stream.toArray();
        addNative(source);
    }

    public static IntStream ofBoxed(Integer... values) {
        return new AsIntStream().addBoxed(values);
    }

    public static IntStream of(int... values) {
        return new AsIntStream().addNative(values);
    }

    @Override
    public Double average() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        return (double) sum() / count();
    }

    @Override
    public Integer max() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        return reduce(storage.at(0), (x, y) -> Math.max(x, y));
    }

    @Override
    public Integer min() {
        if(isEmpty()) {
            throw new IllegalArgumentException();
        }
        return reduce(storage.at(0), (x, y) -> Math.min(x, y));
    }

    @Override
    public long count() {
        return storage.getLength();
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        AsIntStream res = new AsIntStream();
        for (int i = 0; i < count(); i++) {
            if (predicate.test(storage.at(i))) {
                res.addNative(storage.at(i));
            }
        }
        return res;
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i = 0; i < this.count(); i++) {
            action.accept(storage.at(i));
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        int[] localStorage = this.toArray();
        Integer[] res = new Integer[localStorage.length];
        for (int i = 0; i < localStorage.length; i++) {
            res[i] = mapper.apply(localStorage[i]);
        }
        return ofBoxed(res);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int res = identity;
        int len = storage.getLength();
        for (int i = 0; i < len; i++) {
            res = op.apply(res, storage.at(i));
        }
        return res;
    }

    @Override
    public Integer sum() {
        if(isEmpty()) {
            throw new IllegalArgumentException();
        }
        return this.reduce(0, (res, x) -> res += x);
    }

    @Override
    public int[] toArray() {
        int[] res = new int[storage.getLength()];
        for (int i = 0; i < storage.getLength(); i++) {
            res[i] = storage.at(i);
        }
        return res;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        AsIntStream res = new AsIntStream();
        for (int i = 0; i < count(); i++) {
            res.connect(func.applyAsIntStream(storage.at(i)));
        }
        return res;
    }
}

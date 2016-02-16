package ua.yandex.shad.arrays;

public class DynamicArray<Typename> {

    private Typename[] values;
    private int length;

    public DynamicArray(int length) {
        values = (Typename[]) new Object[length];
    }

    public DynamicArray() {
        this(0);
    }

    public void add(Typename... elements) {
        if (values.length == 0) {
            values = (Typename[]) new Object[elements.length];
            this.length = elements.length;
            System.arraycopy(elements, 0, values, 0, elements.length);
        } else {
            int finalLength = values.length;
            while (elements.length + this.length > finalLength) {
                finalLength *= 2;
            }
            Typename[] newArr = (Typename[]) new Object[finalLength];

            System.arraycopy(this.values, 0, newArr, 0, this.length);
            System.arraycopy(elements, 0, newArr, this.length,
                    elements.length);
            values = newArr;
            this.length += elements.length;
        }
    }

    public Typename at(int index) {
        return values[index];
    }

    public int getLength() {
        return this.length;
    }

    public boolean isEmpty() {
        return (length == 0);
    }

}

package com.mgh14.computedconstruct;

import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.util.function.Supplier;

public class RegularMutableVsImmutableDerivedDataConstructors
{
    @ToString
    public static class MutableData
    {
        @Getter
        private final String a;
        private String b;

        MutableData(String a)
        {
            this.a = a;
        }

        public String getB() {
            if (b == null) {
                b = a + ";second string: calculated later";
            }

            return b;
        }
    }

    @Value
    private static class ImmutableDerivedData<T>
    {
        String a;
        T derived;
    }

    public static class ImmutableDerivedDataBuilder<T>
    {
        private String a;
        private Supplier<T> derivedPropertyComputer;

        static <U> ImmutableDerivedDataBuilder<U> builder()
        {
            return new ImmutableDerivedDataBuilder<>();
        }

        ImmutableDerivedDataBuilder<T> setA(String a)
        {
            this.a = a;
            return this;
        }

        ImmutableDerivedDataBuilder<T> setDerivedPropertyComputer(Supplier<T> derivedPropertyComputer)
        {
            this.derivedPropertyComputer = derivedPropertyComputer;
            return this;
        }

        ImmutableDerivedData build()
        {
            T derivedProperty = derivedPropertyComputer.get();
            return new ImmutableDerivedData<>(a, derivedProperty);
        }
    }

    public static void main(String[] args)
    {
        String regularArg = "first string: 1";
        MutableData mutableData = new MutableData(regularArg);
        ImmutableDerivedData immutableDerivedData = ImmutableDerivedDataBuilder.builder().setA(regularArg)
                .setDerivedPropertyComputer(() -> regularArg + ";second string: calculated later, but immutable :)").build();
        System.out.println("Mutable object: " + mutableData);
        System.out.println("Immutable object: " + immutableDerivedData);
    }
}

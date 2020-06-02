package me.b1vth420.survivalTools.interfaces;

public interface TimerInterface<E> {

    void success(E p);
    void error(E p);
}

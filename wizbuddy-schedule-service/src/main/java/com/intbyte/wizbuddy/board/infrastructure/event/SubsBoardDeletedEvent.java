package com.intbyte.wizbuddy.board.infrastructure.event;

public class SubsBoardDeletedEvent {
    private final int subsCode;

    public SubsBoardDeletedEvent(int subsCode) {
        this.subsCode = subsCode;
    }

    public int getSubsCode() {
        return subsCode;
    }
}

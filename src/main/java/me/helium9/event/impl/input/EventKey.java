package me.helium9.event.impl.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.helium9.event.Event;

@Getter
@AllArgsConstructor
public final class EventKey extends Event {
    private final int key;
}

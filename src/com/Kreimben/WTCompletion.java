package com.Kreimben;

import java.util.HashMap;

@FunctionalInterface
public interface WTCompletion {
    HashMap<String, String> completion();
}
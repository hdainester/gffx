package gffx.game.rule.condition;

import gffx.game.world.Field2D;

public interface FieldCondition {
    public boolean check(Field2D f);
}
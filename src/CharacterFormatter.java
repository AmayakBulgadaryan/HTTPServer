//package ru.sbt.jschool.session5.problem2;

public class CharacterFormatter implements JSONTypeFormatter<Character>{
    @Override
    public String format(Character character, JSONFormatter formatter, int ctx, StringBuilder lineForm) {
        String line = character.toString();
        return line;
    }
}

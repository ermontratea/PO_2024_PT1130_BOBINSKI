//package agh.ics.oop.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TextMap implements WorldMap<String, Integer> {
//    private final List<String> textList = new ArrayList<>();
//
//    @Override
//    public boolean canMoveTo(Integer position) {
//        return position >= 0 && position <= textList.size();
//    }
//    @Override
//    public boolean place(String object) {
//        textList.add(object);
//        return true;
//    }
//
//    @Override
//    public void move(String object, MoveDirection direction) {
//        int index = textList.indexOf(object);
//        if (index == -1) return;
//
//        switch (direction) {
//            case FORWARD:
//            case RIGHT:
//                if (index + 1 < textList.size()) {
//
//                    String temp = textList.get(index);
//                    textList.set(index, textList.get(index + 1));
//                    textList.set(index + 1, temp);
//                }
//                break;
//            case BACKWARD:
//            case LEFT:
//                if (index - 1 >= 0) {
//
//                    String temp = textList.get(index);
//                    textList.set(index, textList.get(index - 1));
//                    textList.set(index - 1, temp);
//                }
//                break;
//        }
//    }
//
//    @Override
//    public boolean isOccupied(Integer position) {
//        return position >= 0 && position < textList.size();
//    }
//
//    @Override
//    public String objectAt(Integer position) {
//        if (position >= 0 && position < textList.size()) {
//            return textList.get(position);
//        }
//        return null;
//    }
//
//    public String toString() {
//        return textList.toString();
//    }
//}

//package src;
//
//import javalib.funworld.WorldScene;
//import javalib.worldcanvas.WorldCanvas;
//import javalib.worldimages.*;
//
//import java.awt.*;
//import java.util.HashSet;
//
//
//public class ExamplesGame2048 {
//
//    EmptyTile tEmpty;
//    Tile t2, t4, t8, t16, t128, t512, t1024, t2048, t16384, t131072;
//    Square[] rowEmpty, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14,
//            rowSNP, rowSNPRight, rowSNPLeft, rowSNP2, rowSNPRight2, rowSNPLeft2,
//            rowSNPRight4A, rowSNPRight4B;
//    Grid g0, g1, g2, g3, g4, g5, g6, g7, g8,
//        gridSNP, gridSNPUp, gridSNPDown, gridSNPRight, gridSNPLeft,
//            gridSNP2, gridSNPRight2, gridSNPLeft2, gridSNP3, gridSNPUp3, gridSNPDown3,
//            gridSNPRight4A, gridSNPRight4B;
//
//    GamePlay2048 gameSNP, game2, game3, game4;
//
//    void initData () {
//
//        tEmpty = new EmptyTile();
//        t2 = new Tile(2);
//        t4 = new Tile(4);
//        t8 = new Tile(8);
//        t16 = new Tile(16);
//        t128 = new Tile(128);
//        t512 = new Tile(512);
//        t1024 = new Tile(1024);
//        t2048 = new Tile(2048);
//        t16384 = new Tile(16384);
//        t131072 = new Tile(131072);
//
//
//        rowEmpty = new Square[] {tEmpty, tEmpty, tEmpty, tEmpty};
//        row1 = new Square[] {t2, tEmpty, tEmpty, tEmpty};
//        row2 = new Square[] {t2, t4, tEmpty, t16};
//        row3 = new Square[] {tEmpty, tEmpty, t8, t2};
//        row4 = new Square[] {t4, tEmpty, tEmpty, t16};
//        row5 = new Square[] {t8, tEmpty, tEmpty, tEmpty};
//        row6 = new Square[] {t16, tEmpty, tEmpty, tEmpty};
//        row7 = new Square[] {t2, t4, t2, t4};
//        row8 = new Square[] {t4, t2, t4, t2};
//        row9 = new Square[] {t2, t2, tEmpty, tEmpty};
//        row10 = new Square[] {t4, tEmpty, tEmpty, tEmpty};
//        row11 = new Square[] {t2, tEmpty, t128, tEmpty};
//        row12 = new Square[] {t4, tEmpty, t512, tEmpty};
//        row13 = new Square[] {t16, tEmpty, t2048, tEmpty};
//        row14 = new Square[] {t131072, tEmpty, t16384, tEmpty};
//
//
//        rowSNP = new Square[] {tEmpty, t2, tEmpty, tEmpty};
//        rowSNPRight = new Square[] {tEmpty, tEmpty, tEmpty, t2};
//        rowSNPLeft = new Square[] {t2, tEmpty, tEmpty, tEmpty};
//        rowSNP2 = new Square[] {tEmpty, t2, t4, tEmpty};
//        rowSNPRight2 = new Square[] {tEmpty, tEmpty, t2, t4};
//        rowSNPLeft2 = new Square[] {t2, t4, tEmpty, tEmpty};
//        rowSNPRight4A = new Square[] {tEmpty, t2, tEmpty, t2};
//        rowSNPRight4B = new Square[] {tEmpty, tEmpty, tEmpty, t4};
//
//        g0 = new Grid();
//        g1 = new Grid(new Square[][] {rowEmpty, rowEmpty, rowEmpty, rowEmpty});
//        g2 = new Grid(new Square[][] {row1, row2, row3, row4});
//        g3 = new Grid(new Square[][] {row1, rowEmpty, rowEmpty, rowEmpty});
//        g4 = new Grid(new Square[][] {row7, row8, row7, rowEmpty});
//        g5 = new Grid(new Square[][] {row7, row8, row7, row8});
//        g6 = new Grid(new Square[][] {row9, row9, rowEmpty, rowEmpty});
//        g7 = new Grid(new Square[][] {row10, row10, rowEmpty, rowEmpty});
//        g8 = new Grid(new Square[][] {row11, row12, row13, row14});
//
//
//        gridSNP = new Grid(new Square[][] {rowEmpty, rowSNP, rowEmpty, rowEmpty});
//        gridSNPUp = new Grid(new Square [][] {rowSNP, rowEmpty, rowEmpty, rowEmpty});
//        gridSNPDown = new Grid(new Square[][] {rowEmpty, rowEmpty, rowEmpty, rowSNP});
//        gridSNPRight = new Grid(new Square[][] {rowEmpty, rowSNPRight, rowEmpty, rowEmpty});
//        gridSNPLeft = new Grid(new Square[][] {rowEmpty, rowSNPLeft, rowEmpty, rowEmpty});
//        gridSNP2 = new Grid(new Square[][] {rowEmpty, rowSNP2, rowEmpty, rowEmpty});
//        gridSNPRight2 = new Grid(new Square[][] {rowEmpty, rowSNPRight2, rowEmpty, rowEmpty});
//        gridSNPLeft2 = new Grid(new Square[][] {rowEmpty, rowSNPLeft2, rowEmpty, rowEmpty});
//        gridSNP3 = new Grid(new Square[][] {rowEmpty, row5, row6, rowEmpty});
//        gridSNPUp3 = new Grid(new Square[][] {row5, row6, rowEmpty, rowEmpty});
//        gridSNPDown3 = new Grid(new Square[][] {rowEmpty, rowEmpty, row5, row6});
//        gridSNPRight4A = new Grid(new Square[][] {rowEmpty, rowSNPRight4A, rowEmpty, rowEmpty});
//        gridSNPRight4B = new Grid(new Square[][] {rowEmpty, rowSNPRight4B, rowEmpty, rowEmpty});
//
//
//        gameSNP = new GamePlay2048(gridSNP, new Scoreboard(0));
//        game2 = new GamePlay2048(g6, new Scoreboard(0));
//        game3 = new GamePlay2048(g7, new Scoreboard(8));
//        game4 = new GamePlay2048(g5, new Scoreboard(0));
//
//
//
//    }
//
//
//    void testScoreBoardIncreasing (Tester t) {
//        this.initData();
////        t.checkExpect(game2.onKeyEvent("left"), game3);
//        t.checkExpect(game4.onKeyEvent("left"), 0);
//    }
//
//    void testSameThings (Tester t) {
//        this.initData();
//        t.checkExpect(t2.isSameSquare(t2), true);
//        t.checkExpect(tEmpty.isSameSquare(tEmpty), true);
//        t.checkExpect(t2.isSameSquare(t16), false);
//        t.checkExpect(t2.isSameSquare(tEmpty), false);
//        t.checkExpect(g0.isSameGrid(g0), true);
//        t.checkExpect(g0.isSameGrid(g2), false);
//
//    }
//
////    void testSquareNewPosnAndValueAfterKeyEvent(Tester t) {
////        this.initData();
////        t.checkExpect(gridSNPRight4A.squareNewPosnAndValueAfterKeyEvent("right",new Posn(1,1), new Posn(1,1)),
////                new PosnAndIsSameValue(new Posn(1,3),true));
////        t.checkExpect(gridSNP.squareNewPosnAndValueAfterKeyEvent("up", new Posn(1,1), new Posn(1,1)),
////                new PosnAndIsSameValue(new Posn(0,1),false));
////        t.checkExpect(gridSNP.squareNewPosnAndValueAfterKeyEvent("down", new Posn(1,1), new Posn(1,1)),
////                new PosnAndIsSameValue(new Posn(3,1),false));
////        t.checkExpect(gridSNP.squareNewPosnAndValueAfterKeyEvent("right", new Posn(1,1), new Posn(1,1)),
////                new PosnAndIsSameValue(new Posn(1,3),false));
////        t.checkExpect(gridSNP.squareNewPosnAndValueAfterKeyEvent("left", new Posn(1,1), new Posn(1,1)),
////                new PosnAndIsSameValue(new Posn(1,0),false));
////
////
////
////    }
//
////    void testSquareNewPosition (Tester t) {
////        this.initData();
////        t.checkExpect(gridSNPUp.squareNewPositionAfterKeyEvent("up",new Posn(0,1)), new Posn(0,1));
////        t.checkExpect(gridSNPDown.squareNewPositionAfterKeyEvent("down",new Posn(3,1)), new Posn(3,1));
////        t.checkExpect(gridSNPRight.squareNewPositionAfterKeyEvent("right",new Posn(1,3)), new Posn(1,3));
////        t.checkExpect(gridSNPLeft.squareNewPositionAfterKeyEvent("left",new Posn(1,0)), new Posn(1,0));
////        t.checkExpect(gridSNP.squareNewPositionAfterKeyEvent("up",new Posn(1,1)), new Posn(0,1));
////        t.checkExpect(gridSNP.squareNewPositionAfterKeyEvent("down",new Posn(1,1)), new Posn(3,1));
////        t.checkExpect(gridSNP.squareNewPositionAfterKeyEvent("right",new Posn(1,1)), new Posn(1,3));
////        t.checkExpect(gridSNP.squareNewPositionAfterKeyEvent("left",new Posn(1,1)), new Posn(1,0));
////
////    }
//
//
//
//
//// void testConstructorExceptions (Tester t) {
////     this.initData();
////     t.checkConstructorException(new IllegalArgumentException("Invalid value: 3"),
////             "Tile",
////             3);
////
////
//// }
//
////    boolean testDrawGrid(Tester t) {
////        this.initData();
////        WorldCanvas c = new WorldCanvas(600, 600);
////        WorldScene s = new WorldScene(600, 600);
////        return c.drawScene(s.placeImageXY(gridSNPRight2.drawGrid(), 300, 300))
////                && c.show();
////    }
//
//    boolean testDrawS(Tester t) {
//
//        this.initData();
//        WorldCanvas c = new WorldCanvas(600, 600);
//        WorldScene s = new WorldScene(600, 600);
//        return c.drawScene
//                (s.placeImageXY(g8.drawGrid(),
//                300,
//                300))
//
//                && c.show();
//    }
//
//
//    boolean testGamePlay2048(Tester t) {
//
//        GamePlay2048 g = new GamePlay2048();
//        return g.bigBang(Square.SIDE_LENGTH * 6,Square.SIDE_LENGTH * 6,1);
//
//    }
//
//}







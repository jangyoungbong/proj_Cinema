package menu;

import java.util.Scanner;

/**
 * 공통 메뉴 기능을 제공하는 추상 클래스
 */
public abstract class AbstractMenu implements Menu {
    protected String menuText;      // 각 메뉴가 표시할 텍스트
    protected Menu prevMenu;        // 이전 메뉴(뒤로가기용)
    protected static final Scanner scanner = new Scanner(System.in); // 공용 Scanner

    // 생성자: 메뉴 텍스트와 이전 메뉴를 전달받아 초기화
    public AbstractMenu(String menuText, Menu prevMenu) {
        this.menuText = menuText;
        this.prevMenu = prevMenu;
    }

    // 메뉴 출력: 기본적으로 menuText를 출력 (하위 클래스에서 오버라이드 가능)
    @Override
    public void print() {
        System.out.print(menuText);
    }

    // 이전 메뉴를 설정하는 유틸리티(필요시 사용)
    public void setPrevMenu(Menu prevMenu) {
        this.prevMenu = prevMenu;
    }
}
package main;

import menu.Menu;
import menu.MainMenu;

/**
 * 프로그램 진입점(Main)
 */
public class MainApp {
    public static void main(String[] args) {
        // 프로그램 시작 메시지 출력
        System.out.println("프로그램을 시작합니다!");

        // 메인 메뉴 인스턴스를 받아옴
        Menu menu = MainMenu.getInstance();

        // 메뉴가 null이 될 때까지 반복 (q 입력 시 prevMenu가 null이면 종료)
        while (menu != null) {
            // 현재 메뉴 화면 출력
            menu.print();
            // 사용자 입력 후 다음 메뉴로 이동 (next()는 다음 메뉴 객체를 반환)
            menu = menu.next();
        }

        // 프로그램 종료 메시지
        System.out.println("프로그램이 종료됩니다.");
    }
}


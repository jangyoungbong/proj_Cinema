package menu;

/**
 * 메뉴 인터페이스
 * - print(): 메뉴를 출력
 * - next(): 사용자 입력을 받아 다음 메뉴 객체 반환
 */
public interface Menu {
    void print();
    Menu next();
}
package movie;

import java.util.ArrayList;
import reserv.Reservation;

/**
 * 좌석 관리 클래스
 * - 5행(A~E), 9열(1~9)
 * - reservations 리스트를 받아 예매된 좌석을 "x"로 표기
 */
public class Seats {
    public static final int MAX_ROW = 5; // A~E
    public static final int MAX_COL = 9; // 1~9

    private String[][] map = new String[MAX_ROW][MAX_COL]; // 좌석 상태 배열 ("o" 빈, "x" 예매)

    // 생성자: 기존 예매 리스트를 받아 좌석 맵 초기화 및 표기
    public Seats(ArrayList<Reservation> reservations) throws Exception {
        // 기본값: 빈좌석 "o"
        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_COL; j++) {
                map[i][j] = "o";
            }
        }
        // 예매된 좌석들을 "x"로 표기
        for (int i = 0; i < reservations.size(); i++) {
            String seat = reservations.get(i).getSeatName();
            if (seat == null || seat.length() < 3) continue;
            char rowChar = seat.charAt(0); // 예: 'A'
            // 포맷을 'A-1'로 가정 (index 1가 하이픈)
            if (seat.length() >= 3 && seat.charAt(1) == '-') {
                char colChar = seat.charAt(2);
                int row = rowChar - 'A';
                int col = colChar - '1';
                if (row >= 0 && row < MAX_ROW && col >= 0 && col < MAX_COL) {
                    map[row][col] = "x";
                }
            }
        }
    }

    // 좌석 현황 출력
    public void show() {
        System.out.println("--------------------");
        System.out.println(" S C R E E N");
        System.out.println("--------------------");
        // 행 레이블 (A~E)과 좌석 상태 출력
        for (int i = 0; i < MAX_ROW; i++) {
            System.out.print((char) ('A' + i) + "  ");
            for (int j = 0; j < MAX_COL; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("   1 2 3 4 5 6 7 8 9");
    }

    // 좌석 예매 표기: 이미 예매되어 있으면 Exception 발생
    public void mark(String seatName) throws Exception {
        // seatName 포맷: A-1 (허용: 대소문자)
        if (seatName == null || seatName.length() < 3 || seatName.charAt(1) != '-') {
            throw new Exception("좌석명 형식이 올바르지 않습니다. 예: A-1");
        }
        char rowChar = seatName.charAt(0);
        char colChar = seatName.charAt(2);

        int row = rowChar - 'A';
        int col = colChar - '1';

        if (row < 0 || row >= MAX_ROW || col < 0 || col >= MAX_COL) {
            throw new Exception("좌석 선택이 범위를 벗어났습니다.");
        }

        if ("x".equals(map[row][col])) {
            throw new Exception("이미 예매된 좌석입니다..");
        }
        map[row][col] = "x"; // 예매 표기
    }
}